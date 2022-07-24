package isobar.panasonic.scenarios.vn_vn;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.PaymentMethod;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.checkout.PaymentPage;
import isobar.panasonic.page.fo.method.checkout.SuccessPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckOutCODTest extends SeleniumTest {
    CustomerInformation cus;
    SingleProduct singleProduct;
    BundleProduct bundleProduct;
    Address addr;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    OrderDetailsPage orderDetailsPage;
    PaymentPage paymentPage;
    SuccessPage successPage;
    String ORDER_SUCCESS_MESSAGE, orderID;
    int subtotal, grandtotal, shippingfee, shippingfeeWithoutTax, tax;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        bundleProduct = DataTest.getBundleProductTest();
        addr = DataTest.getDefaultShippingAddress();
        ORDER_SUCCESS_MESSAGE = "Cảm ơn bạn đã mua hàng!";
    }

    public void calculate() {
        subtotal = singleProduct.getPrice() * singleProduct.getQty() + bundleProduct.getPrice() * bundleProduct.getQty();
        shippingfeeWithoutTax = ShippingMethod.FreeShipping.getFee();
        shippingfee = ShippingMethod.FreeShipping.getFee() + DataTest.calculateTax(shippingfeeWithoutTax);
        tax = DataTest.calculateTax(subtotal) + DataTest.calculateTax(shippingfeeWithoutTax);
        grandtotal = subtotal + tax;
    }

    @Test
    public void cart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(singleProduct.getName());
        productListPage.addToCart(singleProduct);
        productListPage.addToCart(singleProduct);
        singleProduct.qty(2);
        productListPage = headerPage.search(bundleProduct.getName());
        productDetailsPage = productListPage.viewPDP(bundleProduct);
        productDetailsPage.addToCart();
        shoppingCartPage = headerPage.viewCart();
        shoppingCartPage.setFreeShipping();
        calculate();
        Assert.assertTrue(shoppingCartPage.checkProduct(singleProduct));
        Assert.assertTrue(shoppingCartPage.checkProduct(bundleProduct));
        Assert.assertTrue(shoppingCartPage.checkSubtotal(subtotal));
        Assert.assertEquals(shoppingCartPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
    }

    @Test(dependsOnMethods = "cart")
    public void checkout() {
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(addr);
        checkoutPage.setShippingMethod(ShippingMethod.FreeShipping);
        Assert.assertTrue(checkoutPage.checkProduct(singleProduct));
        Assert.assertTrue(checkoutPage.checkProduct(bundleProduct));
    }

    @Test(dependsOnMethods = "checkout")
    public void payment() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setPaymentCOD();
        paymentPage.agreeTermsAndConditions();
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertTrue(paymentPage.checkProduct(singleProduct));
        Assert.assertTrue(paymentPage.checkProduct(bundleProduct));
        Assert.assertTrue(paymentPage.checkShippingAddress(addr));
        Assert.assertEquals(paymentPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        successPage = paymentPage.placeOrder();
        Panasonic.assertEquals(successPage.getSuccessMesg(), ORDER_SUCCESS_MESSAGE);
    }

    @Test(dependsOnMethods = "payment")
    public void orderDetail() {
        orderID = successPage.getOrderID();
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderID));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertEquals(orderDetailsPage.getOrderDate(), DateUtility.getCurrentDate());
        Assert.assertTrue(orderDetailsPage.checkProduct(singleProduct));
        Assert.assertTrue(orderDetailsPage.checkProduct(bundleProduct));
        Assert.assertTrue(orderDetailsPage.checkSubtotal(subtotal));
      //  Assert.assertEquals(orderDetailsPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
        //work around  format currency 0
        Assert.assertEquals(orderDetailsPage.getShippingFee(),PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertEquals(orderDetailsPage.getShippingAddress(), DataTest.formatAddress(addr));
        Assert.assertEquals(orderDetailsPage.getBillingAddress(), DataTest.formatAddress(addr));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), PaymentMethod.COD.toString());
    }
}
