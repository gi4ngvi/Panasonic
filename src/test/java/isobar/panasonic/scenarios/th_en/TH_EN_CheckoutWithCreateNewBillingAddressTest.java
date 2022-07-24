package isobar.panasonic.scenarios.th_en;

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

public class TH_EN_CheckoutWithCreateNewBillingAddressTest extends SeleniumTest {
    CustomerInformation cus;
    Address shippingAddress, billingAddress, billingAddressEmpty;
    SingleProduct singleProduct;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    CheckoutPage checkoutPage;
    SuccessPage successPage;
    PaymentPage paymentPage;
    OrderDetailsPage orderDetailsPage;
    ShippingMethod shippingMethod;
    int subTotal, grandTotal;
    String orderId;
    String MSG_ORDER_SUCCESS, MSG_REQUIED;
    StringUtility stringUtility;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        billingAddress = DataTest.getDefaultBillingAddress();
        billingAddress.setFirstName(stringUtility.getRandomCharacter(5));
        billingAddress.setLastName(stringUtility.getRandomCharacter(5));
        billingAddress.setCompany(stringUtility.getRandomCharacter(5));
        billingAddress.setStreet(stringUtility.getRandomCharacter(5));
        billingAddressEmpty = new Address.Builder().firstName("").lastName("").company("").telephone("").zip("").street("").state("Please select a region, state or province.").build();
        cus.setShippingAddress(shippingAddress);
        cus.setBillingAddress(billingAddress);
        shippingMethod = ShippingMethod.SelfCollection;
        MSG_ORDER_SUCCESS = "Thank you for your redemption!";
        MSG_REQUIED = "This is a required field.";
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        // Menu on site TL has changed now. Workaround until stable
//        productListPage = headerPage.gotoCategory(singleProduct.getCategory());
        productListPage = headerPage.search(singleProduct.getName());
        singleProduct.qty(3);
        productDetailsPage = productListPage.viewPDP(singleProduct);
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
        Assert.assertEquals(paymentPage.getBillingFirstNameError(), MSG_REQUIED);
        Assert.assertEquals(paymentPage.getBillingLastNameError(), MSG_REQUIED);
        Assert.assertEquals(paymentPage.getBillingTelephoneError(), MSG_REQUIED);
        Assert.assertEquals(paymentPage.getBillingStateError(), MSG_REQUIED);
        Assert.assertEquals(paymentPage.getBillingZipError(), MSG_REQUIED);
        Assert.assertEquals(paymentPage.getBillingStreetError(), MSG_REQUIED);
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
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
    }

    private void calculate() {
        subTotal = singleProduct.getPrice() * singleProduct.getQty();
        grandTotal = subTotal + shippingMethod.getFee();
    }
}
