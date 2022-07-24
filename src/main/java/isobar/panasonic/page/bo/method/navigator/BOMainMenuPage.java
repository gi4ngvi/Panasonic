package isobar.panasonic.page.bo.method.navigator;

import isobar.panasonic.page.bo.locator.navigator.BOMainMenuLocator;
import org.openqa.selenium.WebDriver;

public class BOMainMenuPage extends BOMainMenuLocator {
    public BOMainMenuPage(WebDriver driver) {
        super(driver);
    }

    public BOSalesTab expandSalesTab() {
        weTabSale.click();
        return new BOSalesTab(driver);
    }

    public BOMarketingTab expandMarketingTab() {
        weMarketingSale.click();
        return new BOMarketingTab(driver);
    }
}
