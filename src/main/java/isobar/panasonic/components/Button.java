package isobar.panasonic.components;

import isobar.panasonic.utility.WaitUtility;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class Button extends Component {

    public Button(WebDriver driver, String locator) {
        super(driver, locator);
    }

    public Button(WebDriver driver) {
        super(driver);
    }

    public void clickButton() throws WebDriverException {
        WaitUtility waitU;
        waitU = new WaitUtility(driver);
        try {
            click();
            waitU.waitForPageLoad();
        } catch (TimeoutException ex) {
            waitU.stopPageLoad();
        }

    }

    public void waitBeforeClick() {
        waitUntilToBeClickAble();
        clickButton();
    }
}
