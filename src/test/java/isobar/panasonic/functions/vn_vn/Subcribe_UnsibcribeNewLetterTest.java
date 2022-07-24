package isobar.panasonic.functions.vn_vn;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.components.MenuItem;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.NewsletterSubscriptionPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.internet.MimeMessage;

public class Subcribe_UnsibcribeNewLetterTest extends SeleniumTest {
    AccountInformationPage accountInformationPage;
    NewsletterSubscriptionPage newsletterSubscriptionPage;
    CustomerInformation cus;
    GmailUtility gmail;
    String subcribeMailTitle;
    String SUBCRIBE_SUCCESS_TITLE, UNSUBCRIBE_SUCCESS_TITLE, SUBCRIBE_SUCCESS_MSG, UNSUBCRIBE_SUCCESS_MSG,
            SENDER_EMAIL_SUBSCRIBE, SENDER_EMAIL_UNSUBSCRIBE, MSG_NOT_SEND_MAIL_SUBSCRIBE, MSG_NOT_SEND_MAIL_UNSUBSCRIBE;
    ActionUtility actionUtility;
    MimeMessage emailSubscribe, emailUnSubscribe;

    @Override
    protected void preCondition() {
        cus = DataTest.getCustomerForgotPassword();
        actionUtility = new ActionUtility(getDriver());
        SUBCRIBE_SUCCESS_TITLE = "Đăng ký nhận Bản tin thành công";
        UNSUBCRIBE_SUCCESS_TITLE = "Đăng ký hủy nhận Bản tin thành công";
        SUBCRIBE_SUCCESS_MSG = "Chúng tôi đã lưu các đăng ký.";
        UNSUBCRIBE_SUCCESS_MSG = "Chúng tôi đã loại bỏ các đăng ký.";
        SENDER_EMAIL_SUBSCRIBE = "Panasonic Contact";
        SENDER_EMAIL_UNSUBSCRIBE = "Panasonic Customer";
        MSG_NOT_SEND_MAIL_SUBSCRIBE = "Not send mail when subscribe newsletter";
        MSG_NOT_SEND_MAIL_UNSUBSCRIBE = "Not send mail when unsubscribe newsletter";
        gmail = new GmailUtility(DataTest.getForgotPasswordCredential());
    }

    @Test
    public void prepare() {
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        newsletterSubscriptionPage = accountInformationPage.openNewLetterTab();
        newsletterSubscriptionPage.selectSubscriptionOption(false);
    }

    @Test(dependsOnMethods = "prepare", alwaysRun = true)
    public void subscribe() {
        long time = gmail.getSendDate(gmail.getLatestEmail(SENDER_EMAIL_SUBSCRIBE, null)).getTime();
        ReportUtility.getInstance().log(LogStatus.WARNING,"Issue: <a href='https://bluecom.atlassian.net/browse/PNSNC-486'>PNSNC-486</a>");
        accountInformationPage = headerPage.gotoAccountInfo();
        newsletterSubscriptionPage = accountInformationPage.openNewLetterTab();
        newsletterSubscriptionPage.selectSubscriptionOption(true);
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), SUBCRIBE_SUCCESS_MSG);
        gmail.waitForEmailSentBySenderName(SENDER_EMAIL_SUBSCRIBE, time);
        emailSubscribe = gmail.getLatestEmail(SENDER_EMAIL_SUBSCRIBE, SUBCRIBE_SUCCESS_TITLE);
        Assert.assertNotNull(emailSubscribe, MSG_NOT_SEND_MAIL_SUBSCRIBE);
        ReportUtility.getInstance().logInfo("<b>Email subscribe newsletter</b>" + gmail.getEmailContent(emailSubscribe));
        subcribeMailTitle = gmail.getEmailSubject(emailSubscribe);
        Panasonic.assertEquals(subcribeMailTitle, SUBCRIBE_SUCCESS_TITLE);
    }

    @Test(dependsOnMethods = "subscribe", alwaysRun = true)
    public void unsubscribe() {
        long time = gmail.getSendDate(gmail.getLatestEmail(SENDER_EMAIL_UNSUBSCRIBE, null)).getTime();
        accountInformationPage = headerPage.gotoAccountInfo();
        newsletterSubscriptionPage = accountInformationPage.openNewLetterTab();
        newsletterSubscriptionPage.selectSubscriptionOption(false);
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), UNSUBCRIBE_SUCCESS_MSG);
        gmail.waitForEmailSentBySenderName(SENDER_EMAIL_UNSUBSCRIBE, time);
        emailUnSubscribe = gmail.getLatestEmail(SENDER_EMAIL_UNSUBSCRIBE, UNSUBCRIBE_SUCCESS_TITLE);
        Assert.assertNotNull(emailUnSubscribe, MSG_NOT_SEND_MAIL_UNSUBSCRIBE);
        ReportUtility.getInstance().logInfo("<b>Email unsubscribe newsletter</b>" + gmail.getEmailContent(emailUnSubscribe));
        subcribeMailTitle = gmail.getEmailSubject(emailUnSubscribe);
        Panasonic.assertEquals(subcribeMailTitle, UNSUBCRIBE_SUCCESS_TITLE);
    }
}
