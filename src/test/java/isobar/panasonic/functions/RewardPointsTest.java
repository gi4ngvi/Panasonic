package isobar.panasonic.functions;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.RewardPointsPage;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.checkout.PaymentPage;
import isobar.panasonic.page.fo.method.checkout.SuccessPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RewardPointsTest extends SeleniumTest {
    CustomerInformation cus;
    Address address;
    SingleProduct product;
    ProductListPage productListPage;
    AccountInformationPage accountInformationPage;
    RewardPointsPage rewardPointsPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    SuccessPage successPage;
    ShoppingCartPage shoppingCartPage;
    String currentPointBalanceStr;
    int currentPointBalanceNumber;
    int grandTotal;
    ShippingMethod shippingMethod;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        address = DataTest.getDefaultShippingAddress();
        product = DataTest.getSingleProductTest();
        shippingMethod = ShippingMethod.SelfCollection;
        grandTotal = product.getPrice() * product.getQty() + shippingMethod.getFee();
    }

    @Test
    public void prepare(){
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        rewardPointsPage = accountInformationPage.openRewardPointsPage();
        currentPointBalanceStr = rewardPointsPage.getRewardPointsBalance();
        headerPage.clearCart();
        productListPage = headerPage.gotoCategory(product.getCategory());
        productListPage.addToCart(product);
        shoppingCartPage = headerPage.viewCart();
        checkoutPage = shoppingCartPage.checkout();
        checkoutPage.selectAddress(address);
        paymentPage = checkoutPage.gotoPayment();
        paymentPage.setBillingAddressSameAsShipping(true);
        paymentPage.agreeTermsAndConditions();
        successPage = paymentPage.placeOrder();
    }

    @Test(dependsOnMethods = "prepare")
    public void checkRewardPoints(){
        // Add step go to Order Details page because new GUI does not have header.
        successPage.gotoOrderDetail();
        accountInformationPage = headerPage.gotoAccountInfo();
        rewardPointsPage = accountInformationPage.openRewardPointsPage();
        currentPointBalanceNumber = PriceUtility.convertPriceStringToNumber(currentPointBalanceStr) - grandTotal;
        Assert.assertEquals(rewardPointsPage.getRewardPointsBalance(), PriceUtility.convertPriceToString(currentPointBalanceNumber));
    }
}
