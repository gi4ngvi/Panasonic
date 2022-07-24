package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NewsletterSubscriptionLocator extends SeleniumFactory {
    @FindBy(how = How.ID, using = "subscription")
    protected WebElement weSubscriptionOption;
    @FindBy(how = How.CSS,using = "button.action.save.primary")
    protected WebElement weSave;

    public NewsletterSubscriptionLocator(WebDriver driver) {
        super(driver);
    }
}
