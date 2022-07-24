package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreatePasswordLocator extends SeleniumFactory {
    @FindBy(how = How.ID, using = "password")
    protected WebElement wePassword;
    @FindBy(how = How.ID, using = "password-confirmation")
    protected WebElement wePasswordConfirm;
    @FindBy(how = How.CSS, using = ".action.submit")
    protected WebElement weSubmit;

    public CreatePasswordLocator(WebDriver driver) {
        super(driver);
    }
}
