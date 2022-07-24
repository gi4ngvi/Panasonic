package isobar.panasonic.page.bo.locator.navigator;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BOMainMenuLocator extends SeleniumFactory {
    @FindBy(how = How.CSS, using = "#menu-magento-sales-sales")
    protected WebElement weTabSale;
    @FindBy(how = How.CSS, using = "#menu-magento-backend-marketing")
    protected WebElement weMarketingSale;
    @FindBy(how = How.CSS, using = "li.item-customer-giftcardaccount a[href*='/admin/giftcardaccount/']")
    protected WebElement weGiftCardAccount;

    public BOMainMenuLocator(WebDriver driver) {
        super(driver);
    }
}
