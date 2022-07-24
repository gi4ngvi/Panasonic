package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SendInvitationLocator extends SeleniumFactory {
    @FindBy(how = How.ID, using = "email_0")
    protected WebElement weEmail;
    @FindBy(how = How.ID, using = "message")
    protected WebElement weMessage;
    @FindBy(how = How.CSS, using = ".action.submit")
    protected WebElement weSubmit;
    @FindBy(how = How.CSS, using = ".message-success.success.message div")
    protected WebElement weSuccessMsg;

    public SendInvitationLocator(WebDriver driver) {
        super(driver);
    }
}
