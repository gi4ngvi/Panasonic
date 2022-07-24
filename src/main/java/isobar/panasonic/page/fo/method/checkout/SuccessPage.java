package isobar.panasonic.page.fo.method.checkout;

import isobar.panasonic.page.fo.locator.checkout.SuccessLocator;
import isobar.panasonic.page.fo.method.account.OrderDetailsPage;
import org.openqa.selenium.WebDriver;

public class SuccessPage extends SuccessLocator {
    public SuccessPage(WebDriver driver) {
        super(driver);
    }

    public String getOrderID() {
        waitUtility.waitUntilToBeClickAble(weOrderID);
        return weOrderID.getText();
    }

    public OrderDetailsPage gotoOrderDetail() {
        waitUtility.waitUntilToBeClickAble(weOrderID);
        weOrderID.click();
        return new OrderDetailsPage(driver);
    }

    public String getSuccessMesg() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText();
    }
}
