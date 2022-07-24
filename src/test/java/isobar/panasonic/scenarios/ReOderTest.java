package isobar.panasonic.scenarios;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyOrderPage;
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
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReOderTest extends SeleniumTest {
    CustomerInformation cus;
    Address shippingAddress, billingAddress;
    SingleProduct product;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    CheckoutPage checkoutPage;
    SuccessPage successPage;
    PaymentPage paymentPage;
    OrderDetailsPage orderDetailsPage;
    AccountInformationPage accountInformationPage;
    MyOrderPage myOrderPage;
    ShoppingCartPage shoppingCartPage;
    ShippingMethod shippingMethod;
    int subTotal, grandTotal;
    String orderID;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        product = DataTest.getSingleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        billingAddress = DataTest.getDefaultShippingAddress();
        cus.setShippingAddress(shippingAddress);
        cus.setBillingAddress(billingAddress);
        shippingMethod = ShippingMethod.SelfCollection;
    }

    @Test
    public void addCart(){
       headerPage.login(cus);
       headerPage.clearCart();
       // Menu on site TL has changed now. Workaround until stable
//       productListPage = headerPage.gotoCategory(product.getCategory());
        productListPage = headerPage.search(product.getName());
       productDetailsPage = productListPage.viewPDP(product);
       productDetailsPage.addToCart();
    }

    @Test(dependsOnMethods = "addCart")
    public void checkout(){
        headerPage.showMiniCart();
        checkoutPage = headerPage.proceedToCheckout();
        checkoutPage.selectAddress(cus.getShippingAddress());
        checkoutPage.setShippingMethod(shippingMethod);
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setBillingAddressSameAsShipping(true);
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
        orderID = successPage.getOrderID();
        orderDetailsPage = successPage.gotoOrderDetail();
    }

    @Test(dependsOnMethods = "checkout")
    public void reOrder() {
        accountInformationPage = headerPage.gotoAccountInfo();
        myOrderPage = accountInformationPage.openMyOrderTab();
        shoppingCartPage = myOrderPage.reOrder(orderID);
        product.qty(5);
        shoppingCartPage.setQuantity(product, 5);
        shoppingCartPage.updateShoppingCart();
    }

    @Test(dependsOnMethods = "reOrder")
    public void reCheckout(){
        calculate();
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(cus.getShippingAddress());
        checkoutPage.setShippingMethod(shippingMethod);
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setBillingAddressSameAsShipping(true);
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
    }

    @Test(dependsOnMethods = "reCheckout")
    public void reCheckInformation(){
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertTrue(orderDetailsPage.checkProduct(product));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(orderDetailsPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal));
        Assert.assertTrue(orderDetailsPage.checkShippingAddress(cus.getShippingAddress()));
        Assert.assertTrue(orderDetailsPage.checkBillingAddress(cus.getBillingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
    }

    private void calculate() {
        subTotal = product.getPrice() * product.getQty();
        grandTotal = subTotal + shippingMethod.getFee();
    }
}
