package isobar.panasonic.scenarios.my_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.PaymentMethod;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.ConfigurationProduct;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.checkout.PaymentPage;
import isobar.panasonic.page.fo.method.checkout.SuccessPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_CheckoutWithStaffInstallmentTest extends SeleniumTest {
    SingleProduct product;
    ConfigurationProduct configurationProduct;
    CustomerInformation cus;
    Address address, addressEmpty;
    ShippingMethod shippingMethod;
    PaymentMethod paymentMethod;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    SuccessPage successPage;
    OrderDetailsPage orderDetailsPage;
    int subTotal, grandTotal, tax, interestRate, interest;
    String installmentPackage;
    StringUtility stringUtility;
    String MSG_ORDER_SUCCESS, MSG_REQUIRE;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        product = DataTest.getSingleProductTest();
        configurationProduct = DataTest.getConfigurationProduct();
        address = DataTest.getDefaultShippingAddress();
        address.setFirstName(stringUtility.getRandomCharacter(10));
        address.setLastName(stringUtility.getRandomCharacter(10));
        address.setStreet(stringUtility.getRandomCharacter(5) + "_street");
        address.setCompany(stringUtility.getRandomCharacter(5));
        addressEmpty = new Address.Builder().firstName("").lastName("").addressName("").state("Please select a region, state or province.").company("").mobileNumber("").city("").zip("").street("").telephone("").build();
        shippingMethod = ShippingMethod.FreeShipping;
        paymentMethod = PaymentMethod.STAFF_INSTALLMENT;
        installmentPackage = "3 months";
        MSG_ORDER_SUCCESS = "Thank you for your purchase!";
        MSG_REQUIRE = "This is a required field.";
        interestRate = 5;
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(product.getName());
        productDetailsPage = productListPage.viewPDP(product);
        productDetailsPage.setQuantity(4);
        productDetailsPage.addToCart();
        product.qty(4);
        productListPage = headerPage.search(configurationProduct.getName());
        productDetailsPage = productListPage.viewPDP(configurationProduct);
        productDetailsPage.selectColor(configurationProduct.getColor());
        productDetailsPage.setQuantity(2);
        productDetailsPage.addToCart();
        configurationProduct.qty(2);
    }

    @Test(dependsOnMethods = "addCart")
    public void checkoutWithAddressEmpty() {
        shoppingCartPage = headerPage.viewCart();
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.newAddress(addressEmpty);
        Assert.assertEquals(checkoutPage.getFirstNameError(), MSG_REQUIRE);
        Assert.assertEquals(checkoutPage.getLastNameError(), MSG_REQUIRE);
        Assert.assertEquals(checkoutPage.getStateError(), MSG_REQUIRE);
        Assert.assertEquals(checkoutPage.getMobileNumberError(), MSG_REQUIRE);
        Assert.assertEquals(checkoutPage.getCityError(), MSG_REQUIRE);
        Assert.assertEquals(checkoutPage.getStateError(), MSG_REQUIRE);
        Assert.assertEquals(checkoutPage.getZipError(), MSG_REQUIRE);
        Assert.assertEquals(checkoutPage.getStreetError(), MSG_REQUIRE);
        checkoutPage.cancelAddress();
    }

    @Test(dependsOnMethods = "checkoutWithAddressEmpty")
    public void checkoutWithAddressValid(){
        checkoutPage.newAddress(address);
        checkoutPage.setShippingMethod(shippingMethod);
        checkoutPage.selectAddress(address);        // Re-select address to workaround for changing shipping method
        Assert.assertTrue(checkoutPage.checkProduct(product));
        Assert.assertTrue(checkoutPage.checkProduct(configurationProduct));
    }

    @Test(dependsOnMethods = "checkoutWithAddressValid")
    public void payment() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.selectPaymentMethod(paymentMethod);
        paymentPage.setBillingAddressSameAsShipping(true);
        paymentPage.selectInstallmentPackage(installmentPackage);
        calculate();
        Assert.assertTrue(paymentPage.checkSubtotal(subTotal));
        Assert.assertTrue(paymentPage.checkInterest(interest));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingMethod.getFee()));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(paymentPage.checkProduct(product));
        Assert.assertTrue(paymentPage.checkProduct(configurationProduct));
        Assert.assertEquals(paymentPage.getShippingMethod(), shippingMethod.title());
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
    }

    @Test(dependsOnMethods = "payment")
    public void orderDetail() {
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertTrue(orderDetailsPage.checkProduct(product));
        Assert.assertTrue(orderDetailsPage.checkProduct(configurationProduct));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(orderDetailsPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), paymentMethod.toString() + " - " + installmentPackage);
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(orderDetailsPage.checkShippingAddress(address));
        Assert.assertTrue(orderDetailsPage.checkBillingAddress(address));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
    }

    private void calculate() {
        subTotal = product.getQty() * product.getPrice() + configurationProduct.getQty() * configurationProduct.getPrice();
        interest = subTotal * interestRate / 100;
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + tax + interest + shippingMethod.getFee();
    }
}
