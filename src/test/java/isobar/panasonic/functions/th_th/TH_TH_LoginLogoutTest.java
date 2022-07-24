package isobar.panasonic.functions.th_th;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TH_TH_LoginLogoutTest extends SeleniumTest {
    CustomerInformation cus, cusInvalid;
    String MSG_LOGIN_ERROR;
    StringUtility stringUtility;
    AccountInformationPage accountInformationPage;
    LoginPopupPage loginPage;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        cusInvalid = DataTest.getDefaultCustomerTest();
        cusInvalid.setEmail(RUN_ID+stringUtility.getRandomCharacter(5)+"@mailinator.com");
        cusInvalid.setPassword(RUN_ID+stringUtility.getRandomCharacter(5));
        MSG_LOGIN_ERROR = "คุณไม่ได้ลงชื่อเข้าใช้อย่างถูกต้องหรือบัญชีของคุณถูกปิดการใช้งานชั่วคราว";
    }

    @Test
    public void loginWithAccountInvalid(){
        headerPage.logout();
        loginPage = headerPage.expandLoginPopup();
        loginPage.fillInLoginAndSubmit(cusInvalid);
        Panasonic.assertEquals(headerPage.getErrorMessage(), MSG_LOGIN_ERROR);
        Assert.assertTrue(headerPage.isGuest());
    }

    @Test(dependsOnMethods = "loginWithAccountInvalid")
    public void loginWithAccountValid(){
        accountInformationPage = headerPage.login(cus);
        Assert.assertFalse(headerPage.isGuest());
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus));
    }

    @Test(dependsOnMethods = "loginWithAccountValid")
    public void logout(){
        headerPage.logout();
        Assert.assertTrue(headerPage.isGuest());
    }
}
