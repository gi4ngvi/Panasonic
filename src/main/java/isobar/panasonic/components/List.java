package isobar.panasonic.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class List extends Component {
    private Actions action;
    private Label lb;
    private String xpath;

    public List(WebDriver driver, String locator) {
        super(driver, locator);
        initComponents();
    }

    private void initComponents() {
        action = new Actions(driver);
        lb = new Label(driver);
        xpath = this.getLocator() + "/option[text()='%1$s']";
    }

    public void select(String... options) {
        xpath = this.getLocator() + "/option[text()='%1$s']";
        action.keyDown(Keys.CONTROL);
        for (String option : options) {
            if (option != null) {
                lb.setLocator(String.format(xpath, option));
                lb.click();
            }
        }
        action.keyUp(Keys.CONTROL);
    }

    public void clearAll() {
        new Select(this.getWebElement()).deselectAll();
    }
} 
