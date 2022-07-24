package isobar.panasonic.page.fo.method.checkout;

import isobar.panasonic.page.fo.locator.checkout.FailureLocator;
import org.openqa.selenium.WebDriver;

public class FailurePage extends FailureLocator {
    public FailurePage(WebDriver driver) {
        super(driver);
    }

    public String getOrderID(){
        waitUtility.waitUntilVisibilityOf(weOrderID);
        return weOrderID.getText().split("#")[1].replaceAll("\\D+","");
    }
}
