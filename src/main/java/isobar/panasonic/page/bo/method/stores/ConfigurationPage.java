package isobar.panasonic.page.bo.method.stores;

import isobar.panasonic.components.Button;
import isobar.panasonic.entity.data.SiteCode;
import isobar.panasonic.page.bo.locator.stores.ConfigurationLocator;
import isobar.panasonic.utility.DataTest;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ConfigurationPage extends ConfigurationLocator {

    public ConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public void goToLiveStockMenu(SiteCode siteCode) {
        switchToStoreView(siteCode);
        waitUtility.waitUntilToBeClickAble(weLiveStockMenu);
        weLiveStockMenu.click();
    }

    public void setBackupLiveStockStatus() {
        waitUtility.waitUntilToBeClickAble(weLiveStockStatus);
        Select liveStockStatus = new Select(weLiveStockStatus);
        String status = liveStockStatus.getFirstSelectedOption().getText();
        if (status.equals("Yes")) {
            DataTest.setBackupLiveStockStatus(true);
        } else {
            DataTest.setBackupLiveStockStatus(false);
        }
    }

    public void setLiveStockStatus(boolean isEnabled) {
        waitUtility.waitUntilToBeClickAble(weLiveStockStatus);
        Select liveStockStatus = new Select(weLiveStockStatus);
        if (isEnabled) {
            liveStockStatus.selectByVisibleText("Yes");
        } else {
            liveStockStatus.selectByVisibleText("No");
        }
        waitUtility.waitUntilToBeClickAble(weSaveConfig);
        weSaveConfig.click();
        waitUtility.waitForPageLoad();
    }

    private void switchToStoreView(SiteCode site) {
        Button btnStoreView;
        waitUtility.waitUntilToBeClickAble(weChangeSite);
        if (!weChangeSite.getText().trim().equals(site.getSite())) {
            weChangeSite.click();
            btnStoreView = new Button(driver, String.format(STORE_VIEW, site.getSite()));
            btnStoreView.waitBeforeClick();
            waitUtility.waitUntilToBeClickAble(weConfirmSwitching);
            weConfirmSwitching.click();
            waitUtility.waitForPageLoad();
            waitForLoader();
        }
    }

    private void waitForLoader() {
        try {
            waitUtility.waitUntilVisibilityOf(weLoader, 20);
            waitUtility.waitForValueOfAttributeContains(weLoader, "style", "display: none;");
        } catch (TimeoutException e) {
        }
    }

}
