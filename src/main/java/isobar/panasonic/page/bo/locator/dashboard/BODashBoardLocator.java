package isobar.panasonic.page.bo.locator.dashboard;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BODashBoardLocator extends SeleniumFactory {
    public static final String ADMIN_MENU = "id=%1$s";
    public static final String ADMIN_SUB_MENU = "css=li[data-ui-id='%1$s']";
    public static final String WARNING_POPUP = "css=aside.modal-popup.modal-system-messages._show>div>header>button.action-close";
    @FindBy(how = How.ID, using = "menu-magento-backend-stores")
    protected WebElement weStoresMenu;
    @FindBy(how = How.XPATH, using = "//li[@data-ui-id='menu-magento-config-system-config']")
    protected WebElement weStoresConfiguration;

    public BODashBoardLocator(WebDriver driver) {
        super(driver);
    }
}
