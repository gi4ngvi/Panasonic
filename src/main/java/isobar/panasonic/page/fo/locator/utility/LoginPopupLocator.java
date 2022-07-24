package isobar.panasonic.page.fo.locator.utility;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by hieu nguyen on 12/7/2017.
 */

public abstract class LoginPopupLocator extends SeleniumFactory {

    @FindBy(how = How.CSS, using = "aside[role='dialog'] #customer-email")
    protected WebElement weLoginEmail;
    @FindBy(how = How.CSS, using = "aside[role='dialog'] #pass")
    protected WebElement weLoginPassword;
    @FindBy(how = How.ID, using = "customer-email-error")
    protected WebElement weErrorEmail;
    @FindBy(how = How.ID, using = "pass-error")
    protected WebElement weErrorPass;
    @FindBy(how = How.ID, using = "email_address")
    protected WebElement weForgotEmail;
    @FindBy(how = How.CSS, using = ".fieldset.login button.action.action-login")
    protected WebElement weLoginSubmit;
    @FindBy(how = How.CSS, using = "aside[role='dialog'] a[href*='/customer/account/forgotpassword/']")
    protected WebElement weForgotPassLink;
    @FindBy(how = How.ID, using = "email_address-error")
    protected WebElement weForgotEmailError;
    @FindBy(how = How.CSS, using = ".message-success.success.message>div")
    protected WebElement weSuccessMsg;
    @FindBy(how = How.CSS, using = ".message-error.error.message>div")
    protected WebElement weErrorMsg;
    @FindBy(how = How.CSS, using = ".action.submit")
    protected WebElement weSubmit;
    @FindBy(how = How.ID, using = "email")
    protected WebElement weEmail;
    @FindBy(how = How.ID, using = "pass")
    protected WebElement wePass;
    @FindBy(how = How.CSS, using = "button.action.login")
    protected WebElement weLogin;

    public LoginPopupLocator(WebDriver driver) {
        super(driver);
    }
}
