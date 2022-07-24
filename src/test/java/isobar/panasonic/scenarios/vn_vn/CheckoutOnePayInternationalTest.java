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

public class CheckoutOnePayInternationalTest extends SeleniumTest {
    SingleProduct sp;
    CustomerInformation cus;
    Address addr, billingAddr;
    CreditCard card,cardInvalid;
    ProductListPage productListPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    VisaPage visaPage;
    SuccessPage successPage;
    OrderDetailsPage orderDetailsPage;
    int subtotal, grandtotal, shippingfee, shippingfeeWithoutTax, tax;
    String orderID;
    String PAYMENT_SUCCESS_MSG,CARD_NUMBER_INVALID_MSG;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        sp = DataTest.getSingleProductTest();
        addr = DataTest.getRandomAddress();
        billingAddr = DataTest.getDefaultBillingAddress();
        card = DataTest.getVisaTest2();
        cardInvalid = new CreditCard.Builder().creditCardNumber("4000000000000011").expMonth("05").expYear("2021").securityCode("123").build();
        PAYMENT_SUCCESS_MSG = "Đã thanh toán thành công bằng OnePay quốc tế.";
        CARD_NUMBER_INVALID_MSG = "Số thẻ sai, Hãy kiểm tra lại.";
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
        paymentPage.setPaymentOnePAYInternational();
        paymentPage.setBillingAddressSameAsShipping(false);
        paymentPage.setBillingAddress(billingAddr);
        paymentPage.agreeTermsAndConditions();
        visaPage = paymentPage.placeOrderOnePayInternational();
        visaPage.fillInVisa(cardInvalid);
        Assert.assertEquals(visaPage.getErrorMsg(),CARD_NUMBER_INVALID_MSG);
    }

    @Test(dependsOnMethods = "paymentWithCardInvalid")
    public void paymentWithCardValid(){
        visaPage.fillInVisa(card);
        successPage = visaPage.submitPassword(card.getPassword3D());
        Assert.assertEquals(headerPage.getSuccessMessage(),PAYMENT_SUCCESS_MSG);
    }

    @Test(dependsOnMethods = "paymentWithCardValid")
    public void orderDetail(){
        orderID = successPage.getOrderID();
        orderDetailsPage = successPage.gotoOrderDetail();
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderID));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), OrderStatus.PENDING.getStatus());
        Assert.assertEquals(orderDetailsPage.getOrderDate(), DateUtility.getCurrentDate());
        Assert.assertTrue(orderDetailsPage.checkProduct(sp));
        Assert.assertTrue(orderDetailsPage.checkSubtotal(subtotal));
        //Assert.assertEquals(orderDetailsPage.getShippingFee(), PriceUtility.convertPriceToString(shippingfeeWithoutTax));
        //work around shipping fee difference format currency
        Assert.assertTrue(orderDetailsPage.checkShippingFee(shippingfeeWithoutTax));
        Assert.assertEquals(orderDetailsPage.getTax(), PriceUtility.convertPriceToString(tax));
        Assert.assertEquals(orderDetailsPage.getTotal(), PriceUtility.convertPriceToString(grandtotal));
        Assert.assertEquals(orderDetailsPage.getShippingAddress(), DataTest.formatAddress(addr));
        Assert.assertEquals(orderDetailsPage.getBillingAddress(), DataTest.formatAddress(billingAddr));
        Assert.assertEquals(orderDetailsPage.getShippingMethod(), ShippingMethod.FreeShipping.title());
        Assert.assertEquals(orderDetailsPage.getPaymentMethod(), PaymentMethod.ONEPAY_INTERNATIONAL.toString());
    }

}
