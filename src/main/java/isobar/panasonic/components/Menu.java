package isobar.panasonic.components;

import isobar.panasonic.utility.ActionUtility;
import isobar.panasonic.utility.WaitUtility;
import org.openqa.selenium.WebDriver;

public class Menu extends Component {
    private static final String MENU_ITEM_XPATH = "xpath=(//*[text()='%1$s'])[1]";
    private MenuItem menuItem;
    private ActionUtility action;
    private WaitUtility waitUtility;

    public Menu(WebDriver driver, String locator) {
        super(driver, locator);
        action = new ActionUtility(driver);
        waitUtility = new WaitUtility(driver);
    }

    public void selectMenuItem(String menuPath) {
        hoverMenuItem(menuPath);
        menuItem.clickMenuItem();
    }

    public void hoverMenuItem(String menuPath) {
        String[] items = menuPath.split(">");
        String xpath;
        for (String item: items) {
            xpath = String.format(MENU_ITEM_XPATH, item);
            menuItem = new MenuItem(driver, xpath);
            waitUtility.waitUntilToBeClickAble(menuItem.getWebElement());
            action.mouseMove(menuItem.getWebElement());
        }
    }
}
