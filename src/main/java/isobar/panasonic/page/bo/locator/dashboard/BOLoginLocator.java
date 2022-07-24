package isobar.panasonic.page.bo.locator.dashboard;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BOLoginLocator extends SeleniumFactory {
    @FindBy(how = How.ID, using="username")
    protected WebElement weUsername;
    @FindBy(how = How.ID, using="login")
    protected WebElement wePassword;
    @FindBy(how = How.CSS, using="button.action-login.action-primary")
    protected WebElement weLogin;

    public BOLoginLocator(WebDriver driver) {
        super(driver);
    }
}
