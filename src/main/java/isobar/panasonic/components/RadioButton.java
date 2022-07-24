package isobar.panasonic.components;

import org.openqa.selenium.WebDriver;

public class RadioButton extends Component {
    public RadioButton(WebDriver driver, String locator) {
        super(driver, locator);
    }

    public RadioButton(WebDriver driver) {
        super(driver);
    }

    public void set(boolean value) {
        waitUntilToBeClickAble(getLocator());
        if (value && !this.isChecked())
            click();
        else if (!value && this.isChecked())
            click();
    }

    public void set(String value) {
        waitUntilToBeClickAble(getLocator());
        if (value.equals("unchecked") && this.isChecked())
            click();
        else if (value.equals("checked") && !this.isChecked())
            click();
        waitUntilToBeClickAble();
    }
}
