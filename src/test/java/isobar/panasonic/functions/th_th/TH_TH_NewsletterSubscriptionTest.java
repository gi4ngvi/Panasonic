package isobar.panasonic.functions.th_th;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.NewsletterSubscriptionPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.internet.MimeMessage;

public class TH_TH_NewsletterSubscriptionTest extends SeleniumTest {
    CustomerInformation cus;
    GmailUtility gmailUtility;
    ActionUtility actionUtility;
    AccountInformationPage accountInformationPage;
    NewsletterSubscriptionPage newsletterSubscriptionPage;
    String SUBSCRIPTION_SUCCESS_MSG, UNSUBSCRIPTION_SUCCESS_MSG, SUBSCRIPTION_MAIL_TITLE, UNSUBSCRIPTION_MAIL_TITLE,
            SENDER_EMAIL_SUBSCRIBE, SENDER_EMAIL_UNSUBSCRIBE, MSG_NOT_SEND_MAIL_SUBSCRIBE, MSG_NOT_SEND_MAIL_UNSUBSCRIBE;
    String subcribeMailTitle, unsubcribeMailTitle;
    MimeMessage emailSubscribe, emailUnSubscribe;

    @Override
    protected void preCondition() {
        cus = DataTest.getCustomer3();
        UNSUBSCRIPTION_SUCCESS_MSG = "เรานำการสมัครสมาชิกออก";
        SUBSCRIPTION_SUCCESS_MSG = "เราบันทึกการสมัครรับข้อมูลแล้ว";
        SUBSCRIPTION_MAIL_TITLE = "สมัครรับข่าวสารเรียบร้อยแล้ว";
        UNSUBSCRIPTION_MAIL_TITLE = "ยกเลิกการรับข่าวสารเรียบร้อยแล้ว";
        gmailUtility = new GmailUtility(DataTest.getGmailCredential());
        actionUtility = new ActionUtility(getDriver());
        SENDER_EMAIL_SUBSCRIBE = "Panasonic Contact";
        SENDER_EMAIL_UNSUBSCRIBE = "Panasonic Customer";
        MSG_NOT_SEND_MAIL_SUBSCRIBE = "Not send mail when subscribe newsletter";
        MSG_NOT_SEND_MAIL_UNSUBSCRIBE = "Not send mail when unsubscribe newsletter";
    }

    @Test
    public void prepare() {
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        newsletterSubscriptionPage = accountInformationPage.openNewLetterTab();
        accountInformationPage = newsletterSubscriptionPage.selectSubscriptionOption(false);
    }

    @Test(dependsOnMethods = "prepare",alwaysRun = true)
    public void subscribe() {
        long time = gmailUtility.getSendDate(gmailUtility.getLatestEmail(SENDER_EMAIL_SUBSCRIBE, null)).getTime();
        newsletterSubscriptionPage = accountInformationPage.openNewLetterTab();
        accountInformationPage = newsletterSubscriptionPage.selectSubscriptionOption(true);
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), SUBSCRIPTION_SUCCESS_MSG);
        gmailUtility.waitForEmailSentBySenderName(SENDER_EMAIL_SUBSCRIBE, time);
        emailSubscribe = gmailUtility.getLatestEmail(SENDER_EMAIL_SUBSCRIBE, SUBSCRIPTION_MAIL_TITLE);
        Assert.assertNotNull(emailSubscribe, MSG_NOT_SEND_MAIL_SUBSCRIBE);
        ReportUtility.getInstance().logInfo("<b>Email subscribe newsletter</b>" + gmailUtility.getEmailContent(emailSubscribe));
        subcribeMailTitle = gmailUtility.getEmailSubject(emailSubscribe);
        Panasonic.assertEquals(subcribeMailTitle, SUBSCRIPTION_MAIL_TITLE);
    }

    @Test(dependsOnMethods = "subscribe", alwaysRun = true)
    public void unsubscribe() {
        long time = gmailUtility.getSendDate(gmailUtility.getLatestEmail(SENDER_EMAIL_UNSUBSCRIBE, null)).getTime();
        newsletterSubscriptionPage = accountInformationPage.openNewLetterTab();
        accountInformationPage = newsletterSubscriptionPage.selectSubscriptionOption(false);
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), UNSUBSCRIPTION_SUCCESS_MSG);
        gmailUtility.waitForEmailSentBySenderName(SENDER_EMAIL_UNSUBSCRIBE, time);
        emailUnSubscribe = gmailUtility.getLatestEmail(SENDER_EMAIL_UNSUBSCRIBE, UNSUBSCRIPTION_MAIL_TITLE);
        Assert.assertNotNull(emailUnSubscribe, MSG_NOT_SEND_MAIL_UNSUBSCRIBE);
        ReportUtility.getInstance().logInfo("<b>Email unsubscribe newsletter</b>" + gmailUtility.getEmailContent(emailUnSubscribe));
        unsubcribeMailTitle = gmailUtility.getEmailSubject(emailUnSubscribe);
        Panasonic.assertEquals(unsubcribeMailTitle, UNSUBSCRIPTION_MAIL_TITLE);
    }
}
