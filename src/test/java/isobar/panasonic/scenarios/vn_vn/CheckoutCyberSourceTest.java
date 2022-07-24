package isobar.panasonic.scenarios.vn_vn;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.*;
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

public class CheckoutCyberSourceTest extends SeleniumTest {
    SingleProduct sp, sp1;
    CustomerInformation cus;
    Address addr, billingAddr;
    CreditCard validCard, invalidCard, emptyCard;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    SuccessPage successPage;
    OrderDetailsPage orderDetailsPage;
    int subtotal, grandtotal, shippingfee, shippingfeeWithoutTax, tax;
    String orderID;
    String INVALID_CARD_NUMBER, INVALID_CARD_EXPIRATION, INVALID_CARD_CVV;
    String ERROR_CARD_NUMBER_EMPTY, ERROR_CARD_MONTH_EMPTY, ERROR_CARD_YEAR_EMPTY, ERROR_CVV_EMPTY;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        sp = DataTest.getSingleProductTest();
        sp1 = DataTest.getSingleProductTest1();
        addr = DataTest.getDefaultShippingAddress();
        billingAddr = DataTest.getDefaultBillingAddress();
        validCard = new CardAccount().creditCard(CardType.CYBERSOURCE);
        invalidCard = new CreditCard.Builder().creditCardNumber("1231231231231231")
                .expMonth("01 - tháng một")
                .expYear("2018")
                .securityCode("11111")
                .build();
        emptyCard = new CreditCard.Builder().build();
        INVALID_CARD_NUMBER = "Vui lòng nhập số thẻ tín dụng hợp lệ.";
        INVALID_CARD_EXPIRATION = "Ngày hết hạn thẻ tín dụng không hợp lệ.";
        INVALID_CARD_CVV = "Vui lòng nhập một số xác minh thẻ tín dụng hợp lệ.";
        ERROR_CARD_NUMBER_EMPTY = "Vui lòng nhập một số hợp lệ trong lĩnh vực này.";
        ERROR_CARD_MONTH_EMPTY = "Đây là trường bắt buộc.";
        ERROR_CARD_YEAR_EMPTY = "Đây là trường bắt buộc.";
        ERROR_CVV_EMPTY = "Vui lòng nhập một số hợp lệ trong lĩnh vực này.";
    }

    public void calculate() {
        subtotal = sp.getPrice() * sp.getQty() + sp1.getPrice() * sp1.getQty();
        shippingfeeWithoutTax = ShippingMethod.FreeShipping.getFee();
        shippingfee = shippingfeeWithoutTax + DataTest.calculateTax(shippingfeeWithoutTax);
        tax = DataTest.calculateTax(subtotal);
        grandtotal = subtotal + tax;
    }

    public void calculate1() {
        shippingfeeWithoutTax = ShippingMethod.Fixed.getFee();
        shippingfee = shippingfeeWithoutTax + DataTest.calculateTax(shippingfeeWithoutTax);
        tax = DataTest.calculateTax(subtotal) + DataTest.calculateTax(shippingfeeWithoutTax);
        grandtotal = subtotal + tax + shippingfeeWithoutTax;
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
        productDetailsPage = productListPage.viewPDP(sp1);
        productDetailsPage.setQuantity(3);
        productDetailsPage.addToCart();
        sp1.qty(3);
        shoppingCartPage = headerPage.viewCart();
        shoppingCartPage.setFreeShipping();
        calculate();
        Assert.assertTrue(shoppingCartPage.checkProduct(sp));
        Assert.assertTrue(shoppingCartPage.checkProduct(sp1));
        Assert.assertTrue(shoppingCartPage.checkSubtotal(subtotal));
        Assert.assertEquals(shoppingCartPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
    }

    @Test(dependsOnMethods = "cart")
    public void checkout() {
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(addr);
        checkoutPage.setShippingMethod(ShippingMethod.Fixed);
        calculate1();
        Assert.assertTrue(checkoutPage.checkProduct(sp));
        Assert.assertTrue(checkoutPage.checkProduct(sp1));
    }

    @Test(dependsOnMethods = "checkout")
    public void paymentWithEmptyCard() {
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setPaymentCyberSource();
        paymentPage.setBillingAddressSameAsShipping(false);
        paymentPage.setBillingAddress(billingAddr);
        paymentPage.fillInCyberSource(emptyCard);
        paymentPage.agreeTermsAndConditions();
        paymentPage.placeOrderError();
        Assert.assertEquals(paymentPage.getCardNumberError(), ERROR_CARD_NUMBER_EMPTY);
        Assert.assertEquals(paymentPage.getCardExpirationError(), ERROR_CARD_MONTH_EMPTY);
        Assert.assertEquals(paymentPage.getCardExpiratioYearError(), ERROR_CARD_YEAR_EMPTY);
        Assert.assertEquals(paymentPage.getCardCvvError(), ERROR_CVV_EMPTY);
    }

    @Test(dependsOnMethods = "paymentWithEmptyCard")
    public void paymentWithInvalidCard() {
        paymentPage.fillInCyberSource(invalidCard);
        paymentPage.placeOrderError();
        Assert.assertEquals(paymentPage.getCardNumberError(), INVALID_CARD_NUMBER);
        Assert.assertEquals(paymentPage.getCardExpirationError(), INVALID_CARD_EXPIRATION);
        Assert.assertEquals(paymentPage.getCardCvvError(), INVALID_CARD_CVV);
    }

    @Test(dependsOnMethods = "paymentWithInvalidCard")
    public void paymentValidCard() {
        Assert.assertTrue(paymentPage.checkSubtotal(subtotal));
        Assert.assertEquals(paymentPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfee));
        Assert.assertEquals(paymentPage.getShippingFeeWithoutTax(), PriceUtility.convertPriceToString(shippingfeeWithoutTax));
        Assert.assertEquals(paymentPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(paymentPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertTrue(paymentPage.checkProduct(sp));
        Assert.assertTrue(paymentPage.checkProduct(sp1));
        Assert.assertTrue(paymentPage.checkShippingAddress(addr));
        Assert.assertEquals(paymentPage.getShippingMethod(), ShippingMethod.Fixed.title());
        paymentPage.fillInCyberSource(validCard);
        successPage = paymentPage.placeOrder();
    }

    @Test(dependsOnMethods = "paymentValidCard")
    public void orderDetail() {
        orderID = successPage.getOrderID();
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderID));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertEquals(orderDetailsPage.getOrderDate(), DateUtility.getCurrentDate());
        Assert.assertTrue(orderDetailsPage.checkProduct(sp));
        Assert.assertTrue(orderDetailsPage.checkProduct(sp1));
        Assert.assertTrue(orderDetailsPage.checkSubtotal(subtotal));
        Assert.assertEquals(orderDetailsPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfeeWithoutTax));
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertEquals(orderDetailsPage.getShippingAddress(), DataTest.formatAddress(addr));
        Assert.assertEquals(orderDetailsPage.getBillingAddress(), DataTest.formatAddress(billingAddr));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), ShippingMethod.Fixed.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), PaymentMethod.CREDIT_CARD.toString());
    }
}
