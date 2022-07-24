package isobar.panasonic.functions;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.MyOrderPage;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyOrderTest extends SeleniumTest {
    CustomerInformation cus;
    MyOrderPage myOrderPage;
    OrderDetailsPage orderDetailsPage;
    AccountInformationPage accountInformationPage;
    String orderId, orderStatus, orderTotal;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
    }

    @Test
    public void myOrder(){
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        myOrderPage = accountInformationPage.openMyOrderTab();
        Assert.assertTrue(myOrderPage.hasOrderInPage());
        orderId = myOrderPage.getOrderId(1);
        orderStatus = myOrderPage.getOrderStatus(1);
        orderTotal = myOrderPage.getOrderTotal(1);
        orderDetailsPage = myOrderPage.viewOrder(1);
        Assert.assertTrue(orderDetailsPage.checkOrderID(orderId));
        Assert.assertEquals(orderDetailsPage.getOrderStatus(), orderStatus);
        Assert.assertEquals(orderDetailsPage.getTotal(), orderTotal);
    }
}
