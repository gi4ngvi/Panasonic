package isobar.panasonic.utility;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by user on 4/3/2017.
 */
public class ActionUtility {
    private WebDriver driver;
    private JavascriptExecutor js;
    private WaitUtility waitU;
    private Actions actions;
    private final static long TIMEOUT = 200;

    public ActionUtility(WebDriver driver) {
        this.driver = driver;
        initComponents();
    }

    private void initComponents() {
        js = (JavascriptExecutor) driver;
        waitU = new WaitUtility(driver);
        actions = new Actions(driver);
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrollToElement(By by) {
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        sleep(TIMEOUT);
    }

    public void scrollToElement(WebElement ele) {
        js.executeScript("arguments[0].scrollIntoView(true);", ele);
        sleep(TIMEOUT);
    }

    public void scrollToElement(By by, int extraX, int extraY) {
        scrollToElement(by);
        js.executeScript("window.scrollBy(arguments[0],arguments[1])", extraX, extraY);
        sleep(TIMEOUT);
    }

    public void scrollToElement(WebElement ele, int extraX, int extraY) {
        scrollToElement(ele);
        js.executeScript("window.scrollBy(arguments[0],arguments[1])", extraX, extraY);
        sleep(TIMEOUT);
    }

    public void scrollWindow(int pixel) {
        js.executeScript("window.scrollBy(0,arguments[0])", pixel);
    }

    public void scrollWindow(int extraX, int extraY) {
        js.executeScript("window.scrollBy(arguments[0],arguments[1])", extraX, extraY);
        sleep(TIMEOUT);
    }

    public void refreshPage() {
        driver.navigate().refresh();
        waitU.waitForPageLoad();
    }

    public void stopPageLoad() {
        js.executeScript("return window.stop");
    }

    public void goToSite(String url) {
        try {
            driver.navigate().to(url);
            waitU.waitForPageLoad();
        } catch (TimeoutException ex) {
            waitU.stopPageLoad();
        }

    }

    public void mouseClick(WebElement e) {
        actions.moveToElement(e);
        actions.click().build().perform();
    }

    public void mouseClick(WebElement e, int xOffSet, int yOffSet) {
        actions.moveToElement(e, xOffSet, yOffSet);
        actions.click().build().perform();
    }

    public void jsClick(WebElement ele) {
        js.executeScript("arguments[0].click();", ele);
    }

    public void mouseMove(WebElement we) {
        Actions action = new Actions(driver);
        action.moveToElement(we).build().perform();
    }
}
