package isobar.panasonic.utility;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtility {
    private static final int TIMEOUT_INTERVAL_UNIT = 30;
    protected WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;


    public WaitUtility(WebDriver driver) {
        this.driver = driver;
        initComponents();
    }

    private void initComponents() {
        wait = new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT);
        js = ((JavascriptExecutor) driver);
    }

    /**
     * Wait until element displayed and clickable
     *
     * @param element element to be checked
     * @throws TimeoutException
     */

    public void waitUntilToBeClickAble(WebElement element) throws TimeoutException {
        waitUntilVisibilityOf(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeRefreshedAndClickAble(WebElement element) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    /**
     * Wait until element deattached to DOM
     *
     * @param by to be checked
     * @throws TimeoutException
     */

    public void waitForNotPresentOf(By by) throws TimeoutException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return driver.findElements(by).size() == 0 ? true : false;
            }
        });
    }

    /**
     * Wait until element attribute display is none
     *
     * @param element element to be checked
     * @throws TimeoutException
     */

    public void waitForInvisibilityOf(WebElement element) throws TimeoutException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.isDisplayed();
            }
        });
    }

    /**
     * Wait until element attribute display is none
     *
     * @param by element to be checked
     * @throws TimeoutException
     */

    public void waitForInvisibilityOf(By by) throws TimeoutException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return driver.findElements(by).size() == 0;
            }
        });
    }

    /**
     * Wait until attribute display is not none
     *
     * @param element element to be checked
     * @throws TimeoutException
     */

    public void refreshAndWaitUntilVisibilityOf(WebElement element) throws TimeoutException {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
    }

    public void waitUntilVisibilityOf(WebElement element) throws TimeoutException {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilVisibilityOf(WebElement element, int timeOutSecond) throws TimeoutException {
        WebDriverWait wait1 = new WebDriverWait(driver, timeOutSecond);
        wait1.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait until element's text is not expectedValue
     *
     * @param element       element to be checked
     * @param expectedValue expected element text() value
     */

    public void waitForTextChange(WebElement element, String expectedValue) throws TimeoutException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.getText().equals(expectedValue) ? false : true;
            }
        });
    }

    /**
     * Wait until element's text is same as expectedValue
     *
     * @param element       element to be checked
     * @param expectedValue expected element text() value
     * @throws TimeoutException
     */

    public void waitForText(WebElement element, String expectedValue) {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.getText().equals(expectedValue) ? true : false;
            }
        });
    }

    /**
     * Wait until page title is same as expectedValue
     *
     * @param expectedValue expected page title
     * @throws TimeoutException
     */
    public void waitForPageTitle(String expectedValue) {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return driver.getTitle().equals(expectedValue) ? true : false;
            }
        });
    }

    /**
     * Wait until element's attribute is same as expectedValue
     *
     * @param element       element to be checked
     * @param attribute     attribute name
     * @param expectedValue expected attribute value
     * @throws TimeoutException
     * @throws NullPointerException
     */

    public void waitForValueOfAttribute(WebElement element, String attribute, String expectedValue) throws TimeoutException, NullPointerException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.getAttribute(attribute).equals(expectedValue) ? true : false;
            }
        });
    }

    /**
     * Wait until element's attribute is not contains expectedValue
     *
     * @param element       element to be checked
     * @param attribute     attribute name
     * @param expectedValue expected attribute value
     * @throws TimeoutException
     * @throws NullPointerException
     */

    public void waitForValueOfAttributeDoesNotContains(WebElement element, String attribute, String expectedValue) throws TimeoutException, NullPointerException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.getAttribute(attribute).contains(expectedValue) ? false : true;
            }
        });
    }

    /**
     * Wait until element's attribute is contains expectedValue
     *
     * @param element       element to be checked
     * @param attribute     attribute name
     * @param expectedValue expected attribute value
     * @throws TimeoutException
     * @throws NullPointerException
     */

    public void waitForValueOfAttributeContains(WebElement element, String attribute, String expectedValue) throws TimeoutException, NullPointerException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.getAttribute(attribute).contains(expectedValue) ? true : false;
            }
        });
    }

    /**
     * Wait until element's attribute exists
     *
     * @param element   element to be checked
     * @param attribute attribute name
     * @throws TimeoutException
     * @throws NullPointerException
     */

    public void waitForAttributeExists(WebElement element, String attribute) throws TimeoutException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.getAttribute(attribute) != null;
            }
        });
    }

    /**
     * Wait until element's attribute exists
     *
     * @param by        of element to be checked
     * @param attribute attribute name
     * @throws TimeoutException
     * @throws NullPointerException
     */

    public void waitForAttributeExists(By by, String attribute) throws TimeoutException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return driver.findElement(by).getAttribute(attribute) != null;
            }
        });
    }

    public void waitForPageLoad() {
        waitForPageLoad(TIMEOUT_INTERVAL_UNIT);
    }

    /**
     * Wait until the page is fully loaded
     *
     * @param timeout
     */

    public void waitForPageLoad(int timeout) throws JavascriptException {
        long delay = 1000;
        sleep(delay);
        for (int i = 0; i < timeout; i++) {
            //To check page ready state.
            if (js.executeScript("return document.readyState").toString().equals("complete"))
                break;
            sleep(delay / 2);
        }
    }

    public void stopPageLoad() {
        js.executeScript("return window.jsErrors");
        js.executeScript("return window.stop");
    }

    /**
     * Wait until css attribute is same as expected css value
     *
     * @param element      element to be checked
     * @param cssAttribute css attribute name
     * @param cssValue     expected css attribute value
     * @param timeout      wait timeout
     * @throws TimeoutException
     * @throws NullPointerException
     */

    public void waitForCssValue(WebElement element, String cssAttribute, String cssValue, int timeout) throws TimeoutException, NullPointerException {
        new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return element.getCssValue(cssAttribute).equals(cssValue) ? true : false;
            }
        });
    }

    public void waitForCssValue(WebElement ele, String cssAttribute, String cssValue) {
        int timeout = TIMEOUT_INTERVAL_UNIT;
        waitForCssValue(ele, cssAttribute, cssValue, timeout);
    }

    /**
     * Wait until current url is not same as paramter url
     *
     * @param url
     */

    public void waitForURLChange(String url) throws TimeoutException {
        new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return driver.getCurrentUrl().equals(url) ? false : true;
            }
        });
    }

    public void waitForURLChange(String url, int TIME_OUT) throws TimeoutException {
        new WebDriverWait(driver, TIME_OUT).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return driver.getCurrentUrl().equals(url) ? false : true;
            }
        });
    }

    /*
        hard sleep
     */
    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
