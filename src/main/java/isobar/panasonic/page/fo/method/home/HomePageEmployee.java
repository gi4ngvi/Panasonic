package isobar.panasonic.page.fo.method.home;

import isobar.panasonic.page.fo.locator.home.HomeLocator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class HomePageEmployee extends HomeLocator {

    public HomePageEmployee(WebDriver driver) {
        super(driver);
    }

    public boolean verifyEmployeeHomePage() {
        waitUtility.waitUntilVisibilityOf(weBreadcrumbs);
        try {
            return weBreadcrumbs.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
