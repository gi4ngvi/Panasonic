package isobar.panasonic.page.bo.method.navigator;

import isobar.panasonic.page.bo.method.marketing.BOGiftCardAccountPage;
import org.openqa.selenium.WebDriver;

public class BOMarketingTab extends BOMainMenuPage {

    public BOMarketingTab(WebDriver driver) {
        super(driver);
    }

    public BOGiftCardAccountPage gotoGiftCardAccount() {
        waitUtility.waitUntilToBeClickAble(weGiftCardAccount);
        weGiftCardAccount.click();
        return new BOGiftCardAccountPage(driver);
    }
}
