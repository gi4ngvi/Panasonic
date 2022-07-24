package isobar.panasonic.scenarios.vn_en;

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

public class VN_EN_CheckoutWithCreateNewShippingAddressTest extends SeleniumTest {
       private CustomerInformation cus;
    private SingleProduct singleProduct;
    private Address shippingAddress, shippingAddressEmpty;
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
    private static final String MSG_REQUIRED = "This is a required field.";
    private static final String MSG_ORDER_SUCCESS = "Thank you for your purchase!";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        shippingAddress.setName(stringUtility.getRandomCharacter(5));
        shippingAddress.setCompany(stringUtility.getRandomCharacter(5));
        shippingAddress.setStreet(stringUtility.getRandomCharacter(5));
        shippingAddressEmpty = new Address.Builder().name("").company("").telephone("").city("Please select a region, state or province.").district("").ward("").street("").build();
        cus.setShippingAddress(shippingAddress);
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
    public void checkoutWithShippingAddressEmpty() {
        headerPage.showMiniCart();
        checkoutPage = headerPage.proceedToCheckout();
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
    public void checkoutWithAddNewShippingAddressValid(){
        checkoutPage.newAddress(cus.getShippingAddress());
        checkoutPage.setShippingMethod(shippingMethod);
        Assert.assertTrue(checkoutPage.checkProduct(singleProduct));
    }

    @Test(dependsOnMethods = "checkoutWithAddNewShippingAddressValid")
    public void payment() {
        calculate();
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.selectPaymentMethod(paymentMethod);
        paymentPage.setBillingAddressSameAsShipping(true);
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingFeeWithoutTax));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingFee));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertTrue(paymentPage.checkShippingAddress(cus.getShippingAddress()));
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
        Assert.assertEquals(orderDetailsPage.getBillingAddress(), DataTest.formatAddress(cus.getShippingAddress()));
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
