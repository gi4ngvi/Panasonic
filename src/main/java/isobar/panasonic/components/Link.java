package isobar.panasonic.components;

import isobar.panasonic.utility.WaitUtility;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class Link extends Component {

    public Link(WebDriver driver, String locator) {
        super(driver, locator);
    }

    public Link(WebDriver driver) {
        super(driver);
    }

    public void clickLink() {
        WaitUtility wait = new WaitUtility(driver);
        try {
            waitUntilToBeClickAble();
            click();
            wait.waitForPageLoad();
        } catch (TimeoutException ex) {
            wait.stopPageLoad();
        }

    }
}
