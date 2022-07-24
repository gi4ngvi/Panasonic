package isobar.panasonic.scenarios.my_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.PaymentMethod;
import isobar.panasonic.entity.data.ShippingMethod;
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

public class MY_EN_CheckoutWithCreateNewBillingAddressTest extends SeleniumTest {
    private SingleProduct singleProduct;
    private CustomerInformation cus;
    private Address shippingAddress, billingAddress, billingAddressEmpty;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private ShoppingCartPage shoppingCartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private SuccessPage successPage;
    private OrderDetailsPage orderDetailsPage;
    private int subTotal, grandTotal, tax;
    StringUtility stringUtility;
    private static final String MSG_REQUIRED = "This is a required field.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        singleProduct = DataTest.getSingleProductTest();
        cus = DataTest.getDefaultCustomerTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        billingAddress = DataTest.getDefaultBillingAddress();
        billingAddress.setName(stringUtility.getRandomCharacter(5));
        billingAddress.setCompany(stringUtility.getRandomCharacter(5));
        billingAddress.setStreet(stringUtility.getRandomCharacter(5));
        billingAddress.setAddressName(stringUtility.getRandomCharacter(10));
        billingAddressEmpty = new Address.Builder().firstName("").lastName("").company("").telephone("").state("Please select a region, state or province.").city("").
                zip("").addressName("").mobileNumber("").street("").build();
        cus.setShippingAddress(shippingAddress);
        cus.setBillingAddress(billingAddress);
        shippingMethod = ShippingMethod.FreeShipping;
        paymentMethod = PaymentMethod.CASH_CHEQUE;
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(singleProduct.getName());
        productDetailsPage = productListPage.viewPDP(singleProduct);
        productDetailsPage.setQuantity(3);
        singleProduct.qty(3);
        productDetailsPage.addToCart();
    }

    @Test(dependsOnMethods = "addCart")
    public void checkout() {
        shoppingCartPage = headerPage.viewCart();
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(shippingAddress);
        checkoutPage.setShippingMethod(shippingMethod);
        Assert.assertTrue(checkoutPage.checkProduct(singleProduct));
    }

    @Test(dependsOnMethods = "checkout")
    public void addBillingAddressEmpty() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.selectPaymentMethod(paymentMethod);
        paymentPage.addNewBillingAddress(billingAddressEmpty);
        Assert.assertEquals(paymentPage.getBillingFirstNameError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingLastNameError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingStateError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingCityError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingStreetError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingMobileNumberError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "addBillingAddressEmpty")
    public void payment() {
        calculate();
        paymentPage.addNewBillingAddress(cus.getBillingAddress());
        Assert.assertTrue(paymentPage.checkSubtotal(subTotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingMethod.getFee()));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(paymentPage.getShippingMethod(), shippingMethod.title());
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
    }

    @Test(dependsOnMethods = "payment")
    public void orderDetail() {
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(orderDetailsPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), paymentMethod.toString());
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(orderDetailsPage.checkShippingAddress(cus.getShippingAddress()));
        Assert.assertTrue(orderDetailsPage.checkBillingAddress(cus.getBillingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
    }

    private void calculate() {
        subTotal = singleProduct.getQty() * singleProduct.getPrice();
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + shippingMethod.getFee() + tax;
    }
}
