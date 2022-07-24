package isobar.panasonic.scenarios.vn_vn;
import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.*;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.checkout.*;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutOnePayDomesticTest extends SeleniumTest {
    SingleProduct sp;
    CustomerInformation cus;
    Address addr, billingAddr;
    CreditCard card,cardInvalid;
    ProductListPage productListPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    OnePayPaymentPage onePayPaymentPage;
    int subtotal, grandtotal, shippingfee, shippingfeeWithoutTax, tax;
    String orderID;
    String PAYMENT_ONEPAYDOMESTIC_SUCCESS_MSG,CARD_INVALID_MSG, ABB_PAY_URL;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        sp = DataTest.getSingleProductTest();
        addr = DataTest.getRandomAddress();
        billingAddr = DataTest.getDefaultBillingAddress();
        card = DataTest.getABBCardTest();
        cardInvalid = new CreditCard.Builder().creditCardName("AAAAAAAAA").creditCardNumber("00000000000").expMonth("1").expYear("08").password3D("123").build();
        PAYMENT_ONEPAYDOMESTIC_SUCCESS_MSG = "Đã thanh toán thành công bằng Thẻ ATM nội địa.";
        CARD_INVALID_MSG = "Số thẻ không đúng, Xin vui lòng nhập lại";
        ABB_PAY_URL = "https://mtf.onepay.vn/onecomm-pay/abbauth.op";

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
    public void paymentWithCardInvalid(){
        paymentPage = checkoutPage.gotoPayment();
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertTrue(paymentPage.checkProduct(sp));
        Assert.assertTrue(paymentPage.checkShippingAddress(addr));
        Assert.assertEquals(paymentPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        paymentPage.setPaymentOnePAYDomestic();
        paymentPage.setBillingAddressSameAsShipping(false);
        paymentPage.setBillingAddress(billingAddr);
        paymentPage.agreeTermsAndConditions();
        onePayPaymentPage = paymentPage.placeOrderOnePayDomestic();
        onePayPaymentPage.selectABBank();
        onePayPaymentPage.fillInOnePayDomestic(cardInvalid);
        onePayPaymentPage.submit();
        Assert.assertEquals(onePayPaymentPage.getVerifyCardMsg(),CARD_INVALID_MSG);
    }

    @Test(dependsOnMethods = "paymentWithCardInvalid")
    public void paymentWithCardValid(){
        onePayPaymentPage.fillInOnePayDomestic(card);
        onePayPaymentPage.submit();
        Assert.assertTrue(onePayPaymentPage.checkUrlRedirect(ABB_PAY_URL));
        onePayPaymentPage.cancel();
    }
}
