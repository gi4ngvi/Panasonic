package isobar.panasonic.functions;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginLogoutTest extends SeleniumTest {
    private CustomerInformation cus, cusInvalid;
    private String MSG_LOGIN_ERROR;
    private StringUtility stringUtility;
    private AccountInformationPage accountInformationPage;
    private LoginPopupPage loginPage;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        cusInvalid = DataTest.getDefaultCustomerTest();
        cusInvalid.setEmail(RUN_ID+stringUtility.getRandomCharacter(5)+"@mailinator.com");
        cusInvalid.setPassword(RUN_ID+stringUtility.getRandomCharacter(5));
        MSG_LOGIN_ERROR = "You did not sign in correctly or your account is temporarily disabled.";
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
