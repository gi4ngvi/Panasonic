package isobar.panasonic.page.bo.method.dashboard;

import isobar.panasonic.components.Button;
import isobar.panasonic.page.bo.locator.dashboard.QuickSearchLocator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class QuickSearchPage extends QuickSearchLocator {
    protected Button btnPopup;
    public QuickSearchPage(WebDriver driver) {
        super(driver);
        btnPopup = new Button(driver, WARNING_POPUP);
    }

    public void clearAll() {
        if (btnPopup.isDisplay())
            btnPopup.click();

        try {
            weReset.click();
            waitUtility.waitForValueOfAttribute(weLoadingMask, "style", "display: none;");
        } catch (NoSuchElementException ex) {
        } catch (WebDriverException ex1) {
            waitUtility.waitForValueOfAttribute(weLoadingMask, "style", "display: none;");
            waitUtility.waitUntilToBeClickAble(weReset);
            weReset.click();
        }
    }

    public void expandFilter() {
        waitUtility.waitUntilToBeClickAble(weFilters);
        String str = weFilters.getAttribute("class");
        if (!str.contains("_active")) {
            try {
                weFilters.click();
            } catch (WebDriverException ex1) {
                waitUtility.waitForValueOfAttribute(weLoadingMask, "style", "display: none;");
                waitUtility.waitUntilToBeClickAble(weFilters);
                weFilters.click();
            }
        }
    }

    public void apply() {
        weApply.click();
        waitUtility.waitForValueOfAttribute(weLoadingMask, "style", "display: none;");
    }
}
