package isobar.panasonic.page.bo.method.dashboard;

import isobar.panasonic.components.Button;
import isobar.panasonic.page.bo.locator.dashboard.BODashBoardLocator;
import isobar.panasonic.page.bo.method.customer.BOAllCustomerPage;
import isobar.panasonic.page.bo.method.stores.ConfigurationPage;
import isobar.panasonic.utility.ActionUtility;
import isobar.panasonic.utility.HandleLoading;
import isobar.panasonic.utility.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BODashBoardPage extends BODashBoardLocator {
    HandleLoading handleLoading;
    Button btnAdminMenu, btnAdminSubMenu, btnPopup;

    public BODashBoardPage(WebDriver driver) {
        super(driver);
        handleLoading = new HandleLoading(driver);
        initComponents();
    }

    private void initComponents() {
        btnAdminMenu = new Button(driver);
        btnAdminSubMenu = new Button(driver);
        waitUtility = new WaitUtility(driver);
        actionUtility = new ActionUtility(driver);
        handleLoading = new HandleLoading(driver);
        waitForPageLoad();
        btnPopup = new Button(driver, WARNING_POPUP);
        if (btnPopup.isDisplay())
            btnPopup.click();
    }

    private void waitForPageLoad() {
        WebElement ele = driver.findElement(By.cssSelector("span.admin-user-account-text"));
        waitUtility.waitUntilVisibilityOf(ele);
        waitUtility.waitUntilToBeClickAble(ele);
    }
    /*
        Customer
    */

    private void clickCustomers() {
        btnAdminMenu.setLocator(String.format(ADMIN_MENU, "menu-magento-customer-customer"));
        btnAdminMenu.waitUntilToBeClickAble();
        btnAdminMenu.click();

    }

    public BOAllCustomerPage viewAllCustomersPage() {
        String url = driver.getCurrentUrl();
        clickCustomers();
        btnAdminSubMenu.setLocator(String.format(ADMIN_SUB_MENU, "menu-magento-customer-customer-manage"));
        actionUtility.sleep(1000);
        btnAdminSubMenu.waitUntilToBeClickAble();
        btnAdminSubMenu.click();
        waitUtility.waitForURLChange(url);
        return new BOAllCustomerPage(driver);
    }

    /*
        Configuration
     */

    private void expandStores() {
        waitUtility.waitUntilToBeClickAble(weStoresMenu);
        weStoresMenu.click();
        waitUtility.waitForValueOfAttributeContains(weStoresMenu, "class", "_active _show");
    }

    public ConfigurationPage goToConfigurationPage() {
        String url = driver.getCurrentUrl();
        expandStores();
        waitUtility.waitUntilToBeClickAble(weStoresConfiguration);
        weStoresConfiguration.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new ConfigurationPage(driver);
    }
}

