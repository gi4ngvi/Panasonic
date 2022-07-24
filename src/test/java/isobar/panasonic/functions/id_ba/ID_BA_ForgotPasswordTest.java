package isobar.panasonic.functions.id_ba;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.CreatePasswordPage;
import isobar.panasonic.page.fo.method.account.EditAccountPage;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.internet.MimeMessage;

public class ID_BA_ForgotPasswordTest extends SeleniumTest {
    CustomerInformation cus, cusForgot;
    ActionUtility actionUtility;
    GmailUtility gmailUtility;
    String recoveryPasswordLink;
    String MSG_REQUIRE, MSG_EMAIL_INVALID, MSG_SENDMAIL_SUCCESS, EMAIL_INVALID, PASSWORD_CHANGED, SENDER_MAIL_FORGOT_PASS, SUBJECT_MAIL_FORGOT_PASS, MSG_NOT_SENDMAIL;
    LoginPopupPage loginPage;
    CreatePasswordPage createPasswordPage;
    AccountInformationPage accountInformationPage;
    EditAccountPage editAccountPage;
    MimeMessage emailForgotPassword;

    @Override
    protected void preCondition() {
        actionUtility = new ActionUtility(getDriver());
        gmailUtility = new GmailUtility(DataTest.getForgotPasswordCredential());
        cus = DataTest.getCustomerForgotPassword();
        cusForgot = DataTest.getCustomerForgotPassword();
        cusForgot.setPassword("Abcd@123456789").setPasswordConfirm("Abcd@123456789");
        MSG_EMAIL_INVALID = "Harap masukkan alamat email yang valid (Ex: johndoe@domain.com).";
        MSG_REQUIRE = "Ini adalah kolom yang harus diisi.";
        MSG_SENDMAIL_SUCCESS = "Jika ada akun yang terkait dengan %s Anda akan menerima email dengan link untuk mereset kata sandi Anda.";
        EMAIL_INVALID = new StringUtility().getRandomCharacter(10);
        PASSWORD_CHANGED = "Anda memperbarui kata sandi Anda.";
        SENDER_MAIL_FORGOT_PASS = "Panasonic Customer";
        MSG_NOT_SENDMAIL = "Not send mail reset password";
        SUBJECT_MAIL_FORGOT_PASS = "Setel ulang kata sandi Panasonic Online Store Anda";
    }

    @Test
    public void forgotPasswordWithEmailInvalid() {
        headerPage.logout();
        loginPage = headerPage.expandLoginPopup();
        loginPage.clickForgotPassword();
        loginPage.forgotPassword("");
        Assert.assertEquals(loginPage.getForgotEmailError(), MSG_REQUIRE);
        loginPage.forgotPassword(EMAIL_INVALID);
        Assert.assertEquals(loginPage.getForgotEmailError(), MSG_EMAIL_INVALID);
    }

    @Test(dependsOnMethods = "forgotPasswordWithEmailInvalid")
    public void forgotPasswordWithEmailValid() {
        long time = gmailUtility.getSendDate(gmailUtility.getLatestEmail(SENDER_MAIL_FORGOT_PASS, null)).getTime();
        loginPage.forgotPassword(cusForgot.getEmail());
        Assert.assertEquals(headerPage.getSuccessMessage(), String.format(MSG_SENDMAIL_SUCCESS, cusForgot.getEmail()));
        gmailUtility.waitForEmailSentBySenderName(SENDER_MAIL_FORGOT_PASS, time);
        emailForgotPassword = gmailUtility.getLatestEmail(SENDER_MAIL_FORGOT_PASS, SUBJECT_MAIL_FORGOT_PASS);
        Assert.assertNotNull(emailForgotPassword, MSG_NOT_SENDMAIL);
        ReportUtility.getInstance().logInfo("<b>Email reset password</b>" + gmailUtility.getEmailContent(emailForgotPassword));
        recoveryPasswordLink = gmailUtility.getResetPasswordLink(emailForgotPassword);
        createPasswordPage = gotoCreatePasswordPage(recoveryPasswordLink);
        loginPage = createPasswordPage.createNewPassword(cusForgot);
        Assert.assertEquals(headerPage.getSuccessMessage(), PASSWORD_CHANGED);
        headerPage.logout();
        accountInformationPage = headerPage.login(cusForgot);
        Assert.assertFalse(headerPage.isGuest());
        Assert.assertTrue(accountInformationPage.checkContactInfo(cusForgot));
    }

    @Test(dependsOnMethods = "forgotPasswordWithEmailValid", alwaysRun = true)
    public void returnConfig() {
        accountInformationPage = headerPage.login(cusForgot);
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.changePassword(cusForgot.getPassword(), cus.getPassword(), cus.getPassword());
        editAccountPage.saveAccount();
    }
}
