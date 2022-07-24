package isobar.panasonic.scenarios.vn_vn;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.OrderStatus;
import isobar.panasonic.entity.data.PaymentMethod;
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

public class VN_VN_CheckoutWithCreateNewBillingAddressTest extends SeleniumTest {
    private CustomerInformation cus;
    private SingleProduct singleProduct;
    private Address shippingAddress, billingAddress, billingAddressEmpty;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private CheckoutPage checkoutPage;
    private OrderDetailsPage orderDetailsPage;
    private PaymentPage paymentPage;
    private SuccessPage successPage;
    private StringUtility stringUtility;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private int subtotal, grandTotal, shippingFee, shippingFeeWithoutTax, tax;
    private static final String MSG_REQUIRED = "Đây là trường bắt buộc.";
    private static final String MSG_ORDER_SUCCESS = "Cảm ơn bạn đã mua hàng!";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        billingAddress = DataTest.getDefaultBillingAddress();
        billingAddress.setName(stringUtility.getRandomCharacter(5));
        billingAddress.setCompany(stringUtility.getRandomCharacter(5));
        billingAddress.setStreet(stringUtility.getRandomCharacter(5));
        billingAddressEmpty = new Address.Builder().name("").company("").telephone("").city("Vui lòng chọn khu vực, Tỉnh hay thành phố.").district("").ward("").street("").build();
        cus.setShippingAddress(shippingAddress);
        cus.setBillingAddress(billingAddress);
        shippingMethod = ShippingMethod.FreeShipping;
        paymentMethod = PaymentMethod.COD;
    }

    @Test
    public void addCart(){
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(singleProduct.getSku());
        productDetailsPage = productListPage.viewPDP(singleProduct);
        singleProduct.qty(4);
        productDetailsPage.setQuantity(4);
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
        paymentPage.selectPaymentMethod(paymentMethod);
        paymentPage.addNewBillingAddress(billingAddressEmpty);
        Assert.assertEquals(paymentPage.getBillingFullNameError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingTelephoneError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingStateError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingCityError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingDistrictError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingWardError(), MSG_REQUIRED);
        Assert.assertEquals(paymentPage.getBillingStreetError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "addBillingAddressEmpty")
    public void payment() {
        calculate();
        paymentPage.addNewBillingAddress(cus.getBillingAddress());
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingFee));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingMethod.getFee()));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertTrue(paymentPage.checkProduct(singleProduct));
        Assert.assertEquals(paymentPage.getShippingMethod(), shippingMethod.title());
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
        Panasonic.assertEquals(successPage.getSuccessMesg(), MSG_ORDER_SUCCESS);
    }

    @Test(dependsOnMethods = "payment")
    public void orderDetail() {
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertTrue(orderDetailsPage.checkProduct(singleProduct));
        Assert.assertTrue(orderDetailsPage.checkSubtotal(subtotal));
        Assert.assertEquals(orderDetailsPage.getShippingFee(),PriceUtility.convertPriceToString(shippingFee));
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertEquals(orderDetailsPage.getShippingAddress(), DataTest.formatAddress(cus.getShippingAddress()));
        Assert.assertEquals(orderDetailsPage.getBillingAddress(), DataTest.formatAddress(cus.getBillingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), PaymentMethod.COD.toString());
    }

    private void calculate() {
        subtotal = singleProduct.getPrice() * singleProduct.getQty();
        tax = DataTest.calculateTax(subtotal);
        shippingFee = 0;
        shippingFeeWithoutTax = shippingFee + DataTest.calculateTax(shippingFee);
        grandTotal = subtotal + tax;
    }
}
