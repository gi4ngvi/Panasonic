package isobar.panasonic.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Combobox extends Component {
    Select selectElement;

    public Combobox(WebDriver driver, String locator) {
        super(driver, locator);
    }

    public void initComponent() {
        selectElement = new Select(getWebElement());
    }

    public void selectItem(String value) {
        initComponent();
        selectElement.selectByVisibleText(value);
    }

    public String getSelectedItem() {
        initComponent();
        return selectElement.getFirstSelectedOption().getText();
    }

    public void selectItem(int value) {
        initComponent();
        selectElement.selectByVisibleText(String.valueOf(value));
    }

    public void selectIndexItem(int index) {
        initComponent();
        selectElement.selectByIndex(index);
    }
}
