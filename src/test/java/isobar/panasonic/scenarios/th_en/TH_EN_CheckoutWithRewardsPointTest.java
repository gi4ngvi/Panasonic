package isobar.panasonic.scenarios.th_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.checkout.PaymentPage;
import isobar.panasonic.page.fo.method.checkout.SuccessPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TH_EN_CheckoutWithRewardsPointTest extends SeleniumTest {
    CustomerInformation cus;
    Address shippingAddress, billingAddress;
    SingleProduct singleProduct;
    BundleProduct bundleProduct;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    CheckoutPage checkoutPage;
    SuccessPage successPage;
    PaymentPage paymentPage;
    OrderDetailsPage orderDetailsPage;
    ShippingMethod shippingMethod;
    int subTotal, grandTotal;
    String orderId;
    String MSG_ORDER_SUCCESS;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        bundleProduct = DataTest.getBundleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        billingAddress = DataTest.getDefaultShippingAddress();
        cus.setShippingAddress(shippingAddress);
        cus.setBillingAddress(billingAddress);
        shippingMethod = ShippingMethod.SelfCollection;
        MSG_ORDER_SUCCESS = "Thank you for your redemption!";
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        // Menu on site TL has changed now. Workaround until stable
//        productListPage = headerPage.gotoCategory(singleProduct.getCategory());
//        productListPage.addToCart(singleProduct);
//        productListPage = headerPage.gotoCategory(bundleProduct.getCategory());

        productListPage = headerPage.search(bundleProduct.getName());
        productDetailsPage = productListPage.viewPDP(bundleProduct);
        bundleProduct.qty(3);
        productDetailsPage.setQuantity(3);
        productDetailsPage.addToCart();
        headerPage.search(singleProduct.getName()).addToCart(singleProduct);
    }

    @Test(dependsOnMethods = "addCart")
    public void checkout() {
        headerPage.showMiniCart();
        checkoutPage = headerPage.proceedToCheckout();
        checkoutPage.selectAddress(cus.getShippingAddress());
        checkoutPage.setShippingMethod(shippingMethod);
        Assert.assertTrue(checkoutPage.checkProduct(singleProduct));
        Assert.assertTrue(checkoutPage.checkProduct(bundleProduct));
    }

    @Test(dependsOnMethods = "checkout")
    public void payment() {
        calculate();
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setBillingAddressSameAsShipping(true);
        Assert.assertTrue(paymentPage.checkSubtotal(subTotal));
        Assert.assertEquals(paymentPage.getShippingFeeWithoutTax(), PriceUtility.convertPriceToString(shippingMethod.getFee()));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertTrue(paymentPage.checkProduct(singleProduct));
        Assert.assertTrue(paymentPage.checkProduct(bundleProduct));
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
        Assert.assertTrue(orderDetailsPage.checkProduct(bundleProduct));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(orderDetailsPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal));
        Assert.assertTrue(orderDetailsPage.checkShippingAddress(cus.getShippingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
    }

    private void calculate() {
        subTotal = singleProduct.getPrice() * singleProduct.getQty() + bundleProduct.getPrice() * bundleProduct.getQty();
        grandTotal = subTotal + shippingMethod.getFee();
    }
}
