package isobar.panasonic.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class InputText extends Component {
    public InputText(WebDriver driver, String locator) {
        super(driver, locator);
    }

    public InputText(WebDriver driver) {
        super(driver);
    }

    public void enterValue(String value) {
        if (value != null)
            enter(value);
    }

    public void pressEnter() {
        pressKey(Keys.ENTER);

    }

    public void enterValue(int value) {
        enter(value);
    }
}
