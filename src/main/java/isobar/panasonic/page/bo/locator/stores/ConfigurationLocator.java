package isobar.panasonic.page.bo.locator.stores;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ConfigurationLocator extends SeleniumFactory {

    protected static final String STORE_VIEW = "xpath=//li[contains(@class,'store-switcher-store-view')]/*[normalize-space(text())='%s']";
    @FindBy(how = How.ID, using = "store-change-button")
    protected WebElement weChangeSite;
    @FindBy(how = How.XPATH, using = "//button[@class='action-primary action-accept']")
    protected WebElement weConfirmSwitching;
    @FindBy(how = How.XPATH, using = "//div[@class='admin__data-grid-loading-mask' and not(@data-component)]")
    protected WebElement weLoader;
    @FindBy(how = How.XPATH, using = "//a/span[.='Live Stock Status']")
    protected WebElement weLiveStockMenu;
    @FindBy(how = How.ID, using = "live_stock_status_live_stock_status_group_active")
    protected WebElement weLiveStockStatus;
    @FindBy(how = How.ID, using = "save")
    protected WebElement weSaveConfig;

    public ConfigurationLocator(WebDriver driver) {
        super(driver);
    }
}
