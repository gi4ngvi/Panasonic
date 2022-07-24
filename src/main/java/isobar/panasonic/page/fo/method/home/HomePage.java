package isobar.panasonic.page.fo.method.home;

import isobar.panasonic.page.fo.locator.home.HomeLocator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class HomePage extends HomeLocator {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyGeneralHomePage() {
        waitUtility.waitUntilVisibilityOf(weSlideBanner);
        try {
            return weSlideBanner.isDisplayed();
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
