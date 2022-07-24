package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;

public class MyCreditCardLocator extends SeleniumFactory {
    protected static final String CARD_ITEM = "xpath=//div[contains(@class,'item card')]//input[@value='*************%s']/following-sibling::a";

    public MyCreditCardLocator(WebDriver driver) {
        super(driver);
    }
}
