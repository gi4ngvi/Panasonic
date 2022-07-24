package isobar.panasonic.page.bo.locator.sale;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;

public class BOOrderLocator extends SeleniumFactory {
    protected static final String ORDER_TABLE = "xpath=//div[@id='container']/div/div/table";

    public BOOrderLocator(WebDriver driver) {
        super(driver);
    }
}
