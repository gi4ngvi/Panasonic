package isobar.panasonic.page.fo.locator.checkout;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SuccessLocator extends SeleniumFactory {

    @FindBy(how = How.CSS, using = "a[href*='sales/order/view/order_id/']")
    protected WebElement weOrderID;
    @FindBy(how = How.CSS, using = ".checkout-onepage-success h1.page-title span")
    protected WebElement weSuccessMsg;

    public SuccessLocator(WebDriver driver) {
        super(driver);
    }
}
