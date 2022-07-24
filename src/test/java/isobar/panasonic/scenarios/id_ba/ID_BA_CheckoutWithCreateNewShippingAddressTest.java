package isobar.panasonic.scenarios.id_ba;

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
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ID_BA_CheckoutWithCreateNewShippingAddressTest extends SeleniumTest {
    private CustomerInformation cus;
    private Address shippingAddress, shippingAddressEmpty;
    private SingleProduct singleProduct;
    private BundleProduct bundleProduct;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private CheckoutPage checkoutPage;
    private SuccessPage successPage;
    private PaymentPage paymentPage;
    private OrderDetailsPage orderDetailsPage;
    private ShippingMethod shippingMethod;
    private int subTotal, grandTotal;
    private String orderId;
    private StringUtility stringUtility;
    private static final String MSG_REQUIRED = "Ini adalah kolom yang harus diisi.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        bundleProduct = DataTest.getBundleProductTest();
        shippingAddress = DataTest.getDefaultShippingAddress();
        shippingAddress = DataTest.getDefaultBillingAddress();
        shippingAddress.setFirstName(stringUtility.getRandomCharacter(5));
        shippingAddress.setLastName(stringUtility.getRandomCharacter(5));
        shippingAddress.setCompany(stringUtility.getRandomCharacter(5));
        shippingAddress.setStreet(stringUtility.getRandomCharacter(5));
        shippingAddressEmpty = new Address.Builder().name("").company("").city("").telephone("").zip("").street("").state("Silakan pilih wilayah, negara bagian atau provinsi.").build();
        cus.setShippingAddress(shippingAddress);
        shippingMethod = ShippingMethod.SelfCollection;
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.gotoCategory(singleProduct.getCategory());
        productListPage.addToCart(singleProduct);
        productListPage = headerPage.gotoCategory(bundleProduct.getCategory());
        productDetailsPage = productListPage.viewPDP(bundleProduct);
        bundleProduct.qty(2);
        productDetailsPage.setQuantity(2);
        productDetailsPage.addToCart();
    }

    @Test(dependsOnMethods = "addCart")
    public void checkoutWithShippingAddressEmpty() {
        headerPage.showMiniCart();
        checkoutPage = headerPage.proceedToCheckout();
        checkoutPage.newAddress(shippingAddressEmpty);
        Assert.assertEquals(checkoutPage.getFullNameError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getStateError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getCityError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getStreetError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getZipError(), MSG_REQUIRED);
        Assert.assertEquals(checkoutPage.getTelephoneError(), MSG_REQUIRED);
        checkoutPage.cancelAddress();
    }

    @Test(dependsOnMethods = "checkoutWithShippingAddressEmpty")
    public void checkoutWithAddNewShippingAddressValid(){
        checkoutPage.newAddress(shippingAddress);
        Assert.assertTrue(checkoutPage.checkAddress(cus.getShippingAddress()));
        checkoutPage.setShippingMethod(shippingMethod);
        paymentPage = checkoutPage.gotoPayment();
    }

    @Test(dependsOnMethods = "checkoutWithAddNewShippingAddressValid")
    public void payment() {
        calculate();
        paymentPage.setBillingAddressSameAsShipping(true);
        Assert.assertTrue(paymentPage.checkSubtotal(subTotal));
        Assert.assertEquals(paymentPage.getShippingFeeWithoutTax(), PriceUtility.convertPriceToString(shippingMethod.getFee()));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
        Assert.assertTrue(paymentPage.checkProduct(singleProduct));
        Assert.assertTrue(paymentPage.checkProduct(bundleProduct));
        Assert.assertTrue(paymentPage.checkShippingAddress(cus.getShippingAddress()));
        Assert.assertEquals(paymentPage.getShippingMethod(), shippingMethod.title());
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
        orderId = successPage.getOrderID();
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
        Assert.assertTrue(orderDetailsPage.checkBillingAddress(cus.getShippingAddress()));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), shippingMethod.title());
    }

    private void calculate() {
        subTotal = singleProduct.getPrice() * singleProduct.getQty() + bundleProduct.getPrice() * bundleProduct.getQty();
        grandTotal = subTotal + shippingMethod.getFee();
    }
}
