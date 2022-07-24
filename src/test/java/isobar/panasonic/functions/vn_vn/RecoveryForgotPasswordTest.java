package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.CreatePasswordPage;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.internet.MimeMessage;

public class RecoveryForgotPasswordTest extends SeleniumTest {
    GmailUtility gmail;
    LoginPopupPage loginPage;
    AccountInformationPage accountInformationPage;
    CreatePasswordPage createPasswordPage;
    CustomerInformation cus, cusForgot, cusTemp;
    ActionUtility actionUtility;
    String recoveryPasswordLink;
    String PASSWORD_CHANGED, ERROR_EMAIL, ERROR_REQUIRED_FILED, SENDER_MAIL_FORGOT_PASS, SUBJECT_MAIL_FORGOT_PASS, MSG_NOT_SENDMAIL;
    MimeMessage emailForgotPassword;

    @Override
    protected void preCondition() {
        actionUtility = new ActionUtility(getDriver());
        gmail = new GmailUtility(DataTest.getForgotPasswordCredential());
        cus = DataTest.getCustomerForgotPassword();
        cusTemp = cus.clone();
        cusForgot = cus.clone().setPassword("123456789@Abc").setPasswordConfirm("123456789@Abc");
        PASSWORD_CHANGED = "Bạn đã cập nhật mật khẩu của bạn.";
        ERROR_EMAIL = "Vui lòng nhập một địa chỉ email hợp lệ (ví dụ: johndoe@domain.com).";
        ERROR_REQUIRED_FILED = "Đây là trường bắt buộc.";
        SENDER_MAIL_FORGOT_PASS = "Panasonic Customer";
        SUBJECT_MAIL_FORGOT_PASS = "Đặt lại mật khẩu Panasonic Online Store";
        MSG_NOT_SENDMAIL = "Not send mail reset password";
    }

    @Test
    public void checkOldPasswordValid() {
        accountInformationPage = headerPage.login(cus);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus));
    }

    @Test(dependsOnMethods = "checkOldPasswordValid")
    public void forgotPassword() {
        headerPage.logout();
        loginPage = headerPage.expandLoginPopup();
        loginPage.clickForgotPassword();

    }

    @Test(dependsOnMethods = "forgotPassword")
    public void invalidEmail() {
        loginPage.forgotPassword("");
        Assert.assertEquals(loginPage.getForgotEmailError(), ERROR_REQUIRED_FILED);
        loginPage.forgotPassword("abc");
        Assert.assertEquals(loginPage.getForgotEmailError(), ERROR_EMAIL);
    }

    @Test(dependsOnMethods = "invalidEmail")
    public void validEmail() {
        long time = gmail.getSendDate(gmail.getLatestEmail(SENDER_MAIL_FORGOT_PASS, null)).getTime();
        loginPage.forgotPassword(cusForgot.getEmail());
        Assert.assertTrue(loginPage.checkSuccessMsg(cus.getEmail()));
        gmail.waitForEmailSentBySenderName(SENDER_MAIL_FORGOT_PASS, time);
        emailForgotPassword = gmail.getLatestEmail(SENDER_MAIL_FORGOT_PASS, SUBJECT_MAIL_FORGOT_PASS);
        Assert.assertNotNull(emailForgotPassword, MSG_NOT_SENDMAIL);
        ReportUtility.getInstance().logInfo("<b>Email reset password</b>" + gmail.getEmailContent(emailForgotPassword));
        recoveryPasswordLink = gmail.getResetPasswordLink(emailForgotPassword);
        createPasswordPage = gotoCreatePasswordPage(recoveryPasswordLink);
        loginPage = createPasswordPage.createNewPassword(cusForgot);
        Panasonic.assertEquals(loginPage.getSuccessMsg(), PASSWORD_CHANGED);
        cusTemp.setPassword(cusForgot.getPassword()).setPasswordConfirm(cusForgot.getPasswordConfirm());
    }

    @Test(dependsOnMethods = "validEmail")
    public void validatePasswordChanged() {
        headerPage.logout();
        accountInformationPage = headerPage.login(cusForgot);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cusForgot));
    }

    @Test(dependsOnMethods = "validatePasswordChanged", alwaysRun = true)
    public void returnConfig() {
        accountInformationPage = headerPage.login(cusTemp);
        accountInformationPage.openAccountInfomationTab();
        accountInformationPage.editAccountInfo(cusTemp, cus);
    }
}
