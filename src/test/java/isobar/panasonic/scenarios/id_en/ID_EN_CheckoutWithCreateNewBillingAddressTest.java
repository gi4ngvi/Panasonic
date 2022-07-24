package isobar.panasonic.scenarios.id_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.checkout.PaymentPage;
import isobar.panasonic.page.fo.method.checkout.SuccessPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ID_EN_CheckoutWithCreateNewBillingAddressTest extends SeleniumTest {
    private CustomerInformation cus;
    private Address shippingAddress, billingAddress, billingAddressEmpty;
    private SingleProduct singleProduct;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private CheckoutPage checkoutPage;
    private SuccessPage successPage;
    private PaymentPage paymentPage;
    private OrderDetailsPage orderDetailsPage;
    private ShippingMethod shippingMethod;
    private int subTotal, grandTotal;
    private String orderId;
    private StringUtility stringUtility;
    private static final String MSG_ORDER_SUCCESS = "Thank you for your purchase!";
    private static final String MSG_NO_PAYMENT_REQUIRED = "No Payment Information Required";
    private static final String MSG_REQUIRED = "This is a required field.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        billingAddress = DataTest.getDefaultShippingAddress();
        billingAddress.setFirstName(stringUtility.getRandomCharacter(5));
        billingAddress.setLastName(stringUtility.getRandomCharacter(5));
        billingAddress.setCompany(stringUtility.getRandomCharacter(5));
        billingAddress.setStreet(stringUtility.getRandomCharacter(5));
        billingAddressEmpty = new Address.Builder().name("").company("").telephone("").zip("").street("").state("Please select a region, state or province.").city("").build();
        cus.setShippingAddress(shippingAddress);
        cus.setBillingAddress(billingAddress);
        shippingMethod = ShippingMethod.SelfCollection;
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.gotoCategory(singleProduct.getCategory());
        productDetailsPage = productListPage.viewPDP(singleProduct);
        singleProduct.qty(3);
        productDetailsPage.setQuantity(3);
        productDetailsPage.addToCart();
    }

    @Test(dependsOnMethods = "addCart")
    public void checkout() {
        headerPage.showMiniCart();
        checkoutPage = headerPage.proceedToCheckout();
        checkoutPage.selectAddress(cus.getShippingAddress());
        checkoutPage.setShippingMethod(shippingMethod);
        Assert.assertTrue(checkoutPage.checkProduct(singleProduct));
    }

    @Test(dependsOnMethods = "checkout")
    public void addBillingAddressEmpty() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setBillingAddressSameAsShipping(false);
        paymentPage.addNewBillingAddress(billingAddressEmpty);
        Assert.assertEquals(paymentPage.getBillingFullNameError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingTelephoneError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingStateError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingCityError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingZipError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingStreetError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "addBillingAddressEmpty")
    public void payment() {
        calculate();
        paymentPage.addNewBillingAddress(billingAddress);
        Assert.assertTrue(paymentPage.checkSubtotal(subTotal));
        Assert.assertEquals(paymentPage.getShippingFeeWithoutTax(), PriceUtility.convertPriceToString(shippingMethod.getFee()));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertTrue(paymentPage.checkProduct(singleProduct));
        Assert.assertEquals(paymentPage.getShippingMethod(), shippingMethod.title());
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
        orderId = successPage.getOrderID();
        Panasonic.assertEquals(successPage.getSuccessMesg(), MSG_ORDER_SUCCESS);
    }

    @Test(dependsOnMethods = "payment")
    public void orderDetail() {
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderId));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertTrue(orderDetailsPage.checkProduct(singleProduct));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(orderDetailsPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal));
        Assert.assertTrue(orderDetailsPage.checkShippingAddress(cus.getShippingAddress()));
        Assert.assertTrue(orderDetailsPage.checkBillingAddress(cus.getBillingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), MSG_NO_PAYMENT_REQUIRED);
    }

    private void calculate() {
        subTotal = singleProduct.getPrice() * singleProduct.getQty();
        grandTotal = subTotal + shippingMethod.getFee();
    }
}
