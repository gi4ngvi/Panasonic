package isobar.panasonic.scenarios.vn_vn;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.PaymentMethod;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyOrderPage;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.checkout.PaymentPage;
import isobar.panasonic.page.fo.method.checkout.SuccessPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReOrderTest extends SeleniumTest {
    CustomerInformation cus;
    SingleProduct sp, sp1;
    Address address1, address2;
    ProductListPage productListPage;
    SuccessPage successPage;
    CheckoutPage checkoutPage;
    ShoppingCartPage shoppingCartPage;
    OrderDetailsPage orderDetailsPage;
    PaymentPage paymentPage;
    MyOrderPage myOrderPage;
    AccountInformationPage accountInformationPage;
    int subtotal, grandtotal, tax, shippingfee, shippingfeeWithoutTax;
    String orderID;
    String ORDER_SUCCESS_MESSAGE;

    @Override
    protected void preCondition() {
        sp = DataTest.getSingleProductTest();
        sp1 = DataTest.getSingleProductTest1();
        cus = DataTest.getDefaultCustomerTest();
        address1 = DataTest.getDefaultShippingAddress();
        address2 = DataTest.getDefaultBillingAddress();
        ORDER_SUCCESS_MESSAGE = "Cảm ơn bạn đã mua hàng!";
    }

    public void calculate() {
        subtotal = sp.getPrice() * sp.getQty() + sp1.getPrice() * sp1.getQty();
        tax = DataTest.calculateTax(subtotal);
        shippingfee = ShippingMethod.FreeShipping.getFee();
        shippingfeeWithoutTax = shippingfee + DataTest.calculateTax(shippingfee);
        grandtotal = subtotal + tax;
    }

    @Test
    public void cart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(sp.getName());
        productListPage.addToCart(sp);
        productListPage.addToCart(sp);
        sp.qty(2);
        productListPage = headerPage.search(sp1.getName());
        productListPage.viewPDP(sp1).addToCart();
        shoppingCartPage = headerPage.viewCart();
        shoppingCartPage.setFreeShipping();
        calculate();
        Assert.assertTrue(shoppingCartPage.checkProduct(sp));
        Assert.assertTrue(shoppingCartPage.checkProduct(sp1));
        Assert.assertTrue(shoppingCartPage.checkSubtotal(subtotal));
        Assert.assertEquals(shoppingCartPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(shoppingCartPage.checkTotal(subtotal));
    }

    @Test(dependsOnMethods = "cart")
    public void checkout() {
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(address1);
        checkoutPage.setShippingMethod(ShippingMethod.FreeShipping);
        Assert.assertTrue(checkoutPage.checkProduct(sp));
        Assert.assertTrue(checkoutPage.checkProduct(sp1));
    }

    @Test(dependsOnMethods = "checkout")
    public void payment() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setPaymentCMO();
        paymentPage.agreeTermsAndConditions();
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(paymentPage.checkTotal(subtotal));
        Assert.assertTrue(paymentPage.checkProduct(sp));
        Assert.assertTrue(paymentPage.checkProduct(sp1));
        Assert.assertTrue(paymentPage.checkShippingAddress(address1));
        Assert.assertEquals(paymentPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        successPage = paymentPage.placeOrder();
        Panasonic.assertEquals(successPage.getSuccessMesg(), ORDER_SUCCESS_MESSAGE);
    }

    @Test(dependsOnMethods = "payment")
    public void reOrder() {
        orderID = successPage.getOrderID();
        orderDetailsPage = successPage.gotoOrderDetail();
        shoppingCartPage = orderDetailsPage.reOrder();
        sp.qty(5);
        sp1.qty(5);
        shoppingCartPage.setQuantity(sp, 5);
        shoppingCartPage.setQuantity(sp1, 5);
        shoppingCartPage.updateShoppingCart();
        calculate();
        Assert.assertTrue(shoppingCartPage.checkProduct(sp));
        Assert.assertTrue(shoppingCartPage.checkProduct(sp1));
        Assert.assertEquals(shoppingCartPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(shoppingCartPage.checkSubtotal(subtotal));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
    }

    @Test(dependsOnMethods = "reOrder")
    public void reCheckout() {
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(address2);
        checkoutPage.setShippingMethod(ShippingMethod.FreeShipping);
        Assert.assertTrue(checkoutPage.checkProduct(sp));
        Assert.assertTrue(checkoutPage.checkProduct(sp1));
    }

    @Test(dependsOnMethods = "reCheckout")
    public void rePayment() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setPaymentCMO();
        paymentPage.agreeTermsAndConditions();
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(paymentPage.checkTotal(subtotal));
        Assert.assertTrue(paymentPage.checkProduct(sp));
        Assert.assertTrue(paymentPage.checkProduct(sp1));
        Assert.assertTrue(paymentPage.checkShippingAddress(address2));
        Assert.assertEquals(paymentPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        successPage = paymentPage.placeOrder();
        Panasonic.assertEquals(successPage.getSuccessMesg(), ORDER_SUCCESS_MESSAGE);
    }

    @Test(dependsOnMethods = "rePayment")
    public void reCheckInfomation() {
        orderID = successPage.getOrderID();
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderID));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertEquals(orderDetailsPage.getOrderDate(), DateUtility.getCurrentDate());
        Assert.assertTrue(orderDetailsPage.checkProduct(sp));
        Assert.assertTrue(orderDetailsPage.checkProduct(sp1));
        Assert.assertTrue(orderDetailsPage.checkSubtotal(subtotal));
       // Assert.assertEquals(orderDetailsPage.getShippingFee(), PriceUtility.convertPriceToString(ShippingMethod.FreeShipping.getFee()));
        //work around  format currency 0
        Assert.assertEquals(orderDetailsPage.getShippingFee(),PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertEquals(orderDetailsPage.getShippingAddress(), DataTest.formatAddress(address2));
        Assert.assertEquals(orderDetailsPage.getBillingAddress(), DataTest.formatAddress(address2));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), PaymentMethod.CMO.toString());
    }
}
