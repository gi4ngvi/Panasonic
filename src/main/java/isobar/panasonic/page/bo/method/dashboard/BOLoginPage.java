package isobar.panasonic.page.bo.method.dashboard;

import isobar.panasonic.page.bo.locator.dashboard.BOLoginLocator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class BOLoginPage extends BOLoginLocator {
    public BOLoginPage(WebDriver driver) {
        super(driver);
    }

    public BODashBoardPage login(String username, String password) {
        try {
            weUsername.isDisplayed();
        } catch (NoSuchElementException ex) {
            return new BODashBoardPage(driver);
        }

        String url = driver.getCurrentUrl();
        weUsername.sendKeys(username);
        wePassword.sendKeys(password);
        weLogin.click();
        waitUtility.waitForURLChange(url);
        return new BODashBoardPage(driver);
    }
}
