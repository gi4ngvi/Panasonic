package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.page.fo.locator.account.NewsletterSubscriptionLocator;
import isobar.panasonic.utility.Panasonic;
import org.openqa.selenium.WebDriver;

public class NewsletterSubscriptionPage extends NewsletterSubscriptionLocator {
    public NewsletterSubscriptionPage(WebDriver driver) {
        super(driver);
    }

    public AccountInformationPage selectSubscriptionOption(boolean status) {
        setSubscriptionOption(status);
        save();
        return new AccountInformationPage(driver);
    }

    private void setSubscriptionOption(boolean status) {
        waitUtility.waitForPageLoad();
        waitUtility.waitUntilToBeClickAble(weSubscriptionOption);
        if (weSubscriptionOption.isSelected() != status) {
            weSubscriptionOption.click();
        }
    }

    private void save() {
        waitUtility.waitUntilToBeClickAble(weSave);
        weSave.click();
        waitUtility.waitForPageLoad();
        Panasonic.checkStoreView(driver);
    }

    public boolean isSubscribed() {
        waitUtility.waitUntilVisibilityOf(weSubscriptionOption);
        return weSubscriptionOption.isSelected();
    }
}
