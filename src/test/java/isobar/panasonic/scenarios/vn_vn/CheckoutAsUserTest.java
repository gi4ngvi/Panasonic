package isobar.panasonic.scenarios.vn_vn;

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
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutAsUserTest extends SeleniumTest {
    private CustomerInformation cus;
    private SingleProduct sp, sp1;
    private ProductListPage productListPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private SuccessPage successPage;
    private OrderDetailsPage orderDetailsPage;
    private ShoppingCartPage shoppingCartPage;
    private Address shippingAddress, shippingAddressEmpty;
    private String orderID;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private int subtotal, grandtotal, shippingfee, shippingfeeWithoutTax, tax;
    private static final String ORDER_SUCCESS_MESSAGE = "Cảm ơn bạn đã mua hàng!";
    private static final String MSG_REQUIRED = "Đây là trường bắt buộc.";

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        sp = DataTest.getSingleProductTest();
        sp1 = DataTest.getSingleProductTest1();
        shippingAddress = new Address.Builder()
                .name("name" + RUN_ID)
                .company("company" + RUN_ID)
                .city("TP. Hồ Chí Minh")
                .district("1")
                .ward("Bến Nghé")
                .street("đường " + RUN_ID)
                .telephone(RUN_ID)
                .country("Việt Nam")
                .build();
        cus.setShippingAddress(shippingAddress);
        shippingAddressEmpty = new Address.Builder().name("").company("").city("Vui lòng chọn khu vực, Tỉnh hay thành phố.").district("").ward("").street("").telephone("").build();
        shippingMethod = ShippingMethod.FreeShipping;
        paymentMethod = PaymentMethod.CMO;
    }

    public void calculate() {
        subtotal = sp.getPrice() * sp.getQty() + sp1.getPrice() * sp1.getQty();
        tax = DataTest.calculateTax(subtotal);
        shippingfee = 0;
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
        calculate();
        Assert.assertTrue(shoppingCartPage.checkProduct(sp));
        Assert.assertTrue(shoppingCartPage.checkProduct(sp1));
        Assert.assertTrue(shoppingCartPage.checkSubtotal(subtotal));
//        Assert.assertEquals(shoppingCartPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
//        Assert.assertEquals(shoppingCartPage.getShippingFeeWithTax(), PriceUtility.convertPriceToString(shippingfeeWithoutTax));
        Assert.assertEquals(shoppingCartPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertTrue(shoppingCartPage.checkTotal(subtotal));
    }

    @Test(dependsOnMethods = "cart")
    public void checkoutWithShippingAddressEmpty() {
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.newAddress(shippingAddressEmpty);
        Assert.assertEquals(checkoutPage.getFullNameError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getStateError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getDistrictError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getWardError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getStreetError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getTelephoneError(), MSG_REQUIRED);
        checkoutPage.cancelAddress();
    }

    @Test(dependsOnMethods = "checkoutWithShippingAddressEmpty")
    public void checkoutWithNewShippingAddress() {
        checkoutPage.newAddress(cus.getShippingAddress());
        checkoutPage.setShippingMethod(shippingMethod);
        Assert.assertTrue(checkoutPage.checkProduct(sp));
        Assert.assertTrue(checkoutPage.checkProduct(sp1));
    }

    @Test(dependsOnMethods = "checkoutWithNewShippingAddress")
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
        Assert.assertTrue(paymentPage.checkShippingAddress(cus.getShippingAddress()));
        Assert.assertEquals(paymentPage.getShippingMethod(), shippingMethod.title());
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
        Assert.assertTrue(orderDetailsPage.checkProduct(sp));
        Assert.assertTrue(orderDetailsPage.checkProduct(sp1));
        Assert.assertTrue(orderDetailsPage.checkSubtotal(subtotal));
       // Assert.assertEquals(orderDetailsPage.getShippingFee(), PriceUtility.convertPriceToString(ShippingMethod.FreeShipping.getFee()));
        //work around  format currency 0
        Assert.assertEquals(orderDetailsPage.getShippingFee(),PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertEquals(orderDetailsPage.getShippingAddress(), DataTest.formatAddress(cus.getShippingAddress()));
        Assert.assertEquals(orderDetailsPage.getBillingAddress(), DataTest.formatAddress(cus.getShippingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), PaymentMethod.CMO.toString());
    }
}
