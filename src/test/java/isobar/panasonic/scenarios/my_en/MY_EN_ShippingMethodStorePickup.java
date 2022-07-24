package isobar.panasonic.scenarios.my_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.customer.StorePickup;
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
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_ShippingMethodStorePickup extends SeleniumTest {
    SingleProduct singleProduct;
    ConfigurationProduct configurationProduct;
    CustomerInformation cus;
    Address billingAddress;
    ShippingMethod shippingMethod;
    PaymentMethod paymentMethod;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    SuccessPage successPage;
    OrderDetailsPage orderDetailsPage;
    int subTotal, grandTotal, tax;
    String orderId;
    StorePickup storePickup;

    @Override
    protected void preCondition() {
        singleProduct = DataTest.getSingleProductTest();
        configurationProduct = DataTest.getConfigurationProduct();
        cus = DataTest.getDefaultCustomerTest();
        billingAddress = DataTest.getDefaultBillingAddress();
        storePickup = DataTest.getDefaultStorePickup();
        shippingMethod = ShippingMethod.StorePickup;
        paymentMethod = PaymentMethod.CASH_CHEQUE;
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(singleProduct.getName());
        productDetailsPage = productListPage.viewPDP(singleProduct);
        productDetailsPage.setQuantity(singleProduct.getQty());
        productDetailsPage.addToCart();
        productListPage = headerPage.search(configurationProduct.getName());
        productDetailsPage = productListPage.viewPDP(configurationProduct);
        productDetailsPage.selectColor(configurationProduct.getColor());
        productDetailsPage.setQuantity(configurationProduct.getQty());
        productDetailsPage.addToCart();
    }

    @Test(dependsOnMethods = "addCart")
    public void checkout() {
        shoppingCartPage = headerPage.viewCart();
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(billingAddress);
        checkoutPage.setShippingMethodStorePickup(storePickup);
        Assert.assertTrue(checkoutPage.checkProduct(singleProduct));
        Assert.assertTrue(checkoutPage.checkProduct(configurationProduct));
    }

    @Test(dependsOnMethods = "checkout")
    public void payment() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.selectPaymentMethod(paymentMethod);
        paymentPage.agreeTermsAndConditions();
        calculate();
        Assert.assertTrue(paymentPage.checkShippingAddressStorePickup(storePickup, cus));
        Assert.assertTrue(paymentPage.checkBillingAddress(billingAddress));
        Assert.assertTrue(paymentPage.checkSubtotal(subTotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingMethod.getFee()));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(paymentPage.checkProduct(singleProduct));
        Assert.assertTrue(paymentPage.checkProduct(configurationProduct));
        Assert.assertEquals(paymentPage.getShippingMethod(), shippingMethod.title());
        successPage = paymentPage.placeOrder();
        orderId = successPage.getOrderID();
    }

    @Test(dependsOnMethods = "payment")
    public void orderDetail() {
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderId));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertTrue(orderDetailsPage.checkProduct(singleProduct));
        Assert.assertTrue(orderDetailsPage.checkProduct(configurationProduct));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(orderDetailsPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), paymentMethod.toString());
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(orderDetailsPage.checkShippingAddressStorePickup(billingAddress, storePickup));
        Assert.assertTrue(orderDetailsPage.checkBillingAddress(billingAddress));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
    }

    private void calculate() {
        subTotal = singleProduct.getQty() * singleProduct.getPrice() + configurationProduct.getQty() * configurationProduct.getPrice();
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + shippingMethod.getFee() + tax;
    }
}

