package isobar.panasonic.components;

import isobar.panasonic.utility.WaitUtility;
import org.openqa.selenium.WebDriver;

public class MenuItem extends Component {

    public MenuItem(WebDriver driver, String locator) {
        super(driver, locator);
    }

    public void clickMenuItem() {
        click();
        new WaitUtility(driver).waitForPageLoad();
    }
}
