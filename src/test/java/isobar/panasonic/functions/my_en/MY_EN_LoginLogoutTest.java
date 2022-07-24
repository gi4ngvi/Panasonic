package isobar.panasonic.functions.my_en;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.home.HomePage;
import isobar.panasonic.page.fo.method.home.HomePageEmployee;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_LoginLogoutTest extends SeleniumTest {
    private CustomerInformation cusEmployee, cusGeneral, cusInvalid;
    private String MSG_LOGIN_ERROR;
    private StringUtility stringUtility;
    private AccountInformationPage accountInformationPage;
    private LoginPopupPage loginPage;
    private HomePage homePage;
    private HomePageEmployee homePageEmployee;
    private static final String PAGETITLE_GENERAL = "Homepage for malaysia";
    private static final String PAGETITLE_EMPLOYEE = "Malaysia Employee Home Page";
    private static final String URL = "https://panasonic.dev.bluecomvn.com/my_en/";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cusEmployee = DataTest.getDefaultCustomerTest();
        cusGeneral = new CustomerInformation.Builder()
                .firstName("QC")
                .lastName("panasonic")
                .email("myen_general01@mailinator.com")
                .password("Abc@123456789")
                .group("General").build();
        cusInvalid = DataTest.getDefaultCustomerTest();
        cusInvalid.setEmail(RUN_ID+stringUtility.getRandomCharacter(5)+"@mailinator.com");
        cusInvalid.setPassword(RUN_ID+stringUtility.getRandomCharacter(5));
        MSG_LOGIN_ERROR = "Please reset your password or email to direct@my.panasonic.com for assistance.";
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
    public void loginWithEmployeeAccount(){
        homePageEmployee = (HomePageEmployee) headerPage.loginSiteML(cusEmployee);
        Assert.assertFalse(headerPage.isGuest());
        // Employee Homepage is now using theme of General Homepage, skip this checkpoint.
//        Assert.assertTrue(homePageEmployee.verifyEmployeeHomePage());
        Panasonic.assertEquals(homePageEmployee.getPageTitle(), PAGETITLE_EMPLOYEE);
        Assert.assertEquals(homePageEmployee.getUrl(), URL);

        accountInformationPage = headerPage.gotoAccountInfo();
        Assert.assertTrue(accountInformationPage.checkContactInfo(cusEmployee));
    }

    @Test(dependsOnMethods = "loginWithEmployeeAccount")
    public void logoutEmployee(){
        headerPage.logout();
        Assert.assertTrue(headerPage.isGuest());
    }

    @Test(dependsOnMethods = "logoutEmployee")
    public void loginWithGeneralAccount(){
        homePage = (HomePage) headerPage.loginSiteML(cusGeneral);
        Assert.assertFalse(headerPage.isGuest());
        Assert.assertTrue(homePage.verifyGeneralHomePage());
        Panasonic.assertEquals(homePage.getPageTitle(), PAGETITLE_GENERAL);
        Assert.assertEquals(homePage.getUrl(), URL);

        accountInformationPage = headerPage.gotoAccountInfo();
        Assert.assertTrue(accountInformationPage.checkContactInfo(cusGeneral));
    }

    @Test(dependsOnMethods = "loginWithGeneralAccount")
    public void logoutGeneral(){
        headerPage.logout();
        Assert.assertTrue(headerPage.isGuest());
    }
}
