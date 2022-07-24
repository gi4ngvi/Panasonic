package isobar.panasonic.page.fo.method.account;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.page.fo.locator.account.SendInvitationLocator;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.WebDriver;

public class SendInvitationPage extends SendInvitationLocator {
    public SendInvitationPage(WebDriver driver) {
        super(driver);
    }
    public void enterEmail(String key) {
        waitUtility.waitUntilToBeClickAble(weEmail);
        weEmail.clear();
        weEmail.sendKeys(key);
    }

    public void enterMessage(String key) {
        weMessage.clear();
        weMessage.sendKeys(key);
    }

    public void submit() {
        weSubmit.click();
    }

    public void sendInvite(String email, String message) {
        ReportUtility.getInstance().log(LogStatus.INFO, "Invitation sent with message: " + message);
        enterEmail(email);
        enterMessage(message);
        submit();
    }

    public String getSuccessMsg() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText();
    }
}