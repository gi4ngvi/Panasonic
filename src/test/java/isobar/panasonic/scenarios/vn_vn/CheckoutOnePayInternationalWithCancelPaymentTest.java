package isobar.panasonic.scenarios.vn_vn;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyOrderPage;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.checkout.*;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutOnePayInternationalWithCancelPaymentTest extends SeleniumTest {
    SingleProduct sp;
    CustomerInformation cus;
    Address addr, billingAddr;
    AccountInformationPage accountInformationPage;
    ProductListPage productListPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    MyOrderPage myOrderPage;
    VisaPage visaPage;
    FailurePage failurePage;
    OrderDetailsPage orderDetailsPage;
    int subtotal, grandtotal, shippingfee, shippingfeeWithoutTax, tax;
    String orderID;
    String PAYMENT_FAILURE_MSG;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        sp = DataTest.getSingleProductTest();
        addr = DataTest.getRandomAddress();
        billingAddr = DataTest.getDefaultBillingAddress();
        PAYMENT_FAILURE_MSG = "Thanh toán OnePay thất bại. Người dùng hủy giao dịch.";
    }

    public void calculate() {
        subtotal = sp.getPrice() * sp.getQty();
        shippingfeeWithoutTax = ShippingMethod.FreeShipping.getFee();
        shippingfee = shippingfeeWithoutTax + DataTest.calculateTax(shippingfeeWithoutTax);
        tax = DataTest.calculateTax(subtotal);
        grandtotal = subtotal + tax;
    }

    public void calculate1() {
        //shippingfeeWithoutTax = ShippingMethod.Fixed.getFee();
        //work around get shipping fee in UI, because shipping fee changes constantly.
        shippingfeeWithoutTax = checkoutPage.getShippingFee(ShippingMethod.FreeShipping.toString());
        shippingfee = shippingfeeWithoutTax + DataTest.calculateTax(shippingfeeWithoutTax);
        tax = DataTest.calculateTax(subtotal) + DataTest.calculateTax(shippingfeeWithoutTax);
        grandtotal = subtotal + tax + shippingfeeWithoutTax;
    }

    @Test
    public void cart(){
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(sp.getName());
        productListPage.addToCart(sp);
        sp.qty(1);
        shoppingCartPage = headerPage.viewCart();
        //shoppingCartPage.setFreeShipping();
        calculate();
        Assert.assertTrue(shoppingCartPage.checkProduct(sp));
        Assert.assertTrue(shoppingCartPage.checkSubtotal(subtotal));
        Assert.assertEquals(shoppingCartPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
    }

    @Test(dependsOnMethods = "cart")
    public void checkout(){
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.newAddress(addr);
        checkoutPage.setShippingMethod(ShippingMethod.FreeShipping);
        calculate1();
        Assert.assertTrue(checkoutPage.checkAddress(addr));
        Assert.assertTrue(checkoutPage.checkProduct(sp));
    }

    @Test(dependsOnMethods = "checkout")
    public void payment(){
        paymentPage = checkoutPage.gotoPayment();
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertTrue(paymentPage.checkProduct(sp));
        Assert.assertTrue(paymentPage.checkShippingAddress(addr));
        Assert.assertEquals(paymentPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        paymentPage.setPaymentOnePAYInternational();
        paymentPage.setBillingAddressSameAsShipping(false);
        paymentPage.setBillingAddress(billingAddr);
        paymentPage.agreeTermsAndConditions();
        visaPage = paymentPage.placeOrderOnePayInternational();
        failurePage = visaPage.cancel();
        Assert.assertEquals(headerPage.getErrorMessage(),PAYMENT_FAILURE_MSG);
    }

    @Test(dependsOnMethods = "payment")
    public void checkOrderStatus(){
        orderID = failurePage.getOrderID();
        accountInformationPage = headerPage.gotoAccountInfo();
        myOrderPage = accountInformationPage.openMyOrderTab();
        orderDetailsPage = myOrderPage.viewOrder(orderID);
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderID));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.CANCEL.getStatus());
    }

}
