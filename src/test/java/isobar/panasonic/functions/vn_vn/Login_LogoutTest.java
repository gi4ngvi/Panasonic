package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Login_LogoutTest extends SeleniumTest {
    CustomerInformation cusEmpty, cusCorrect, cus;
    AccountInformationPage accountInformationPage;
    LoginPopupPage loginPage;
    String ERROR_REQUIRED_FILED, ERROR_INCORRECT_LOGIN, ERROR_INVALID_EMAIL_FORMAT;

    @Override
    protected void preCondition() {
        cusEmpty = new CustomerInformation.Builder()
                .name("")
                .email("")
                .password("")
                .passwordConfirm("")
                .build();
        cusCorrect = new CustomerInformation.Builder()
                .name("hon trang" + RUN_ID)
                .email("email" + RUN_ID + "@isobar.com")
                .password("Abc@12345")
                .passwordConfirm("Abc@12345")
                .build();
        cus = DataTest.getDefaultCustomerTest();
        ERROR_REQUIRED_FILED = "Đây là trường bắt buộc.";
        ERROR_INCORRECT_LOGIN = "Bạn đã không đăng nhập chính xác hoặc tài khoản của bạn tạm thời bị vô hiệu.";
        ERROR_INVALID_EMAIL_FORMAT = "Vui lòng nhập một địa chỉ email hợp lệ (ví dụ: johndoe@domain.com).";
    }

    @Test
    public void loginWithEmptyInfo() {
        headerPage.logout();
        loginPage = headerPage.expandLoginPopup();
        loginPage.submitLogin();
        Assert.assertTrue(headerPage.isGuest());
        Panasonic.assertEquals(loginPage.getEmailError(), ERROR_REQUIRED_FILED);
        Assert.assertEquals(loginPage.getPasswordError(), ERROR_REQUIRED_FILED);
    }

    @Test(dependsOnMethods = "loginWithEmptyInfo", alwaysRun = true)
    public void loginWithIncorrectAccount() {
        cusEmpty.setEmail("abc");
        loginPage.fillInLoginAndSubmit(cusEmpty);
        Assert.assertTrue(headerPage.isGuest());
        Assert.assertEquals(loginPage.getEmailError(), ERROR_INVALID_EMAIL_FORMAT);
        Assert.assertEquals(loginPage.getPasswordError(), ERROR_REQUIRED_FILED);
        loginPage.fillInLoginAndSubmit(cusCorrect);
        Assert.assertTrue(headerPage.isGuest());
        Panasonic.assertEquals(loginPage.getErrorMsg(), ERROR_INCORRECT_LOGIN);
    }

    @Test(dependsOnMethods = "loginWithIncorrectAccount", alwaysRun = true)
    public void loginWithCorrectAccount() {
        accountInformationPage = headerPage.login(cus);
        accountInformationPage.checkContactInfo(cus);
        Assert.assertFalse(headerPage.isGuest());
    }

    @Test(dependsOnMethods = "loginWithCorrectAccount", alwaysRun = true)
    public void logout() {
        headerPage.logout();
        Assert.assertTrue(headerPage.isGuest());
    }
}
