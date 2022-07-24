package isobar.panasonic.scenarios.my_en;

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
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_ReorderTest extends SeleniumTest {
    private CustomerInformation cus;
    private Address shippingAddress, billingAddress;
    private SingleProduct product;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private CheckoutPage checkoutPage;
    private SuccessPage successPage;
    private PaymentPage paymentPage;
    private OrderDetailsPage orderDetailsPage;
    private AccountInformationPage accountInformationPage;
    private MyOrderPage myOrderPage;
    private ShoppingCartPage shoppingCartPage;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private int subTotal, grandTotal, tax;
    private String orderID;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        product = DataTest.getSingleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        billingAddress = DataTest.getDefaultShippingAddress();
        cus.setShippingAddress(shippingAddress);
        cus.setBillingAddress(billingAddress);
        shippingMethod = ShippingMethod.FreeShipping;
        paymentMethod = PaymentMethod.CASH_CHEQUE;
    }

    @Test
    public void addCart(){
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(product.getName());
        productDetailsPage = productListPage.viewPDP(product);
        productDetailsPage.addToCart();
    }

    @Test(dependsOnMethods = "addCart")
    public void checkout(){
        headerPage.showMiniCart();
        shoppingCartPage = headerPage.proceedToCheckoutSiteML();
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(cus.getShippingAddress());
        checkoutPage.setShippingMethod(shippingMethod);
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.selectPaymentMethod(paymentMethod);
        paymentPage.setBillingAddressSameAsShipping(true);
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
        orderID = successPage.getOrderID();
    }

    @Test(dependsOnMethods = "checkout")
    public void reOrder() {
        // New GUI does not have header now.
        orderDetailsPage = successPage.gotoOrderDetail();
        shoppingCartPage = orderDetailsPage.reOrder();
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
        paymentPage.selectPaymentMethod(paymentMethod);
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
        Assert.assertEquals(orderDetailsPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(orderDetailsPage.checkShippingAddress(cus.getShippingAddress()));
        Assert.assertTrue(orderDetailsPage.checkBillingAddress(cus.getBillingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), paymentMethod.toString());
    }

    private void calculate() {
        subTotal = product.getPrice() * product.getQty();
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + shippingMethod.getFee() + tax;
    }
}

