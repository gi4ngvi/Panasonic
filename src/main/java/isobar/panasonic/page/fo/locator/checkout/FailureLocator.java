package isobar.panasonic.page.fo.locator.checkout;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FailureLocator extends SeleniumFactory {

    @FindBy(how = How.XPATH,using = "//p[starts-with(text(),'Đơn hàng')]")
    protected WebElement weOrderID;

    public FailureLocator(WebDriver driver) {
        super(driver);
    }
}
