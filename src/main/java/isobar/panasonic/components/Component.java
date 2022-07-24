package isobar.panasonic.components;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class Component {
    protected WebDriver driver;
    private String locator;
    private By by;
    private WebElement webElement;
    private static final String HIGHLIGHT_STYLE = "3px solid red";
    private WebDriverWait wait, wait1;
    private static final int TIMEOUT_INTERVAL_UNIT = 30;
    private JavascriptExecutor js;

    public Component(WebDriver driver) {
        this.driver = driver;
        initComponents();
    }

    public Component(WebDriver driver, String locator) {
        this.driver = driver;
        this.locator = locator;
        initComponents();
        by = getBy();
    }

	/*
	 *  init components
	 */

    private void initComponents() {
        wait = new WebDriverWait(driver, TIMEOUT_INTERVAL_UNIT);
        js = ((JavascriptExecutor) driver);
    }

    public By getBy() {
        if (locator.startsWith("css="))
            return By.cssSelector(locator.substring(4));
        else if (locator.startsWith("xpath="))
            return By.xpath(locator.substring(6));
        else if (locator.startsWith("id="))
            return By.id(locator.substring(3));
        return null;
    }

    public By getBy(String locator) {
        this.locator = locator;
        return getBy();
    }

    public WebElement getWebElement() {
        webElement = driver.findElement(by);
        highlight();
        return webElement;
    }

    public WebElement getWebElement(String locator) {
        by = getBy(locator);
        return getWebElement();
    }

    public List<WebElement> getWebElements() {
        return driver.findElements(by);
    }
	
	/*
	 *  Actions
	 */

    public void refresh() {
        by = getBy();
    }

    public void click() throws WebDriverException {
        getWebElement();
        webElement.click();
    }

    public void click(String locator) {
        getWebElement(locator);
        webElement.click();
    }

    public String getText() {
        getWebElement();
        return webElement.getText();
    }

    public String getText(String locator) {
        getWebElement(locator);
        return webElement.getText();
    }

    public String getValue() {
        getWebElement();
        return webElement.getAttribute("value");
    }

    public String getInnerText() {
        getWebElement();
        return webElement.getAttribute("innerText");
    }

    public String getAttribute(String attribute) {
        getWebElement();
        return webElement.getAttribute(attribute);
    }

    public void enter(String value) {
        getWebElement();
        webElement.clear();
        webElement.sendKeys(value);
    }

    public void enter(int value) {
        enter(String.valueOf(value));
    }

    public void waitBeforeEnter(int value) {
        Label lbMask = new Label(driver, "css=div.loading-mask");
        getWebElement();
        sleep(3000);
        webElement.clear();
        sleep(3000);
        lbMask.waitForUntilInvisibility(30);
        sleep(3000);
        webElement.sendKeys(String.valueOf(value));
        lbMask.waitForUntilInvisibility(30);
        sleep(2000);
    }

    public void pressKey(Keys key) {
        getWebElement();
        webElement.sendKeys(key);
    }

    public boolean isChecked() {
        getWebElement();
        return webElement.isSelected();
    }

    public int getSize() {
        return driver.findElements(by).size();
    }

    public boolean isPresent() {
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isDisplay() {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
	
	/*
	 * Highlight to visual view
	 */

    private void highlight() {
        //js.executeScript("arguments[0].style.border='"+HIGHLIGHT_STYLE+"'", webElement);
        sleep(150);
        //js.executeScript("arguments[0].style.border=''", webElement);
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /*
     *  Wait functions
     */

    public void waitUntilPresent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitUntilVisibility() {
        waitUntilPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitUntilToBeClickAble() {
        waitUntilVisibility();
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitUntilPresent(String locator) {
        getBy(locator);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitUntilVisibility(String locator) {
        waitUntilPresent(locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitUntilToBeClickAble(String locator) {
        waitUntilVisibility(locator);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitForExpectedValue(String value) {
        wait.until(ExpectedConditions.textToBePresentInElementValue(by, value));
    }

    public void waitForUntilInvisibility() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForUntilInvisibility(int timeout) {
        wait1 = new WebDriverWait(driver, timeout);
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void setLocator(String locator) {
        this.locator = locator;
        by = getBy();
    }

    public String getLocator() {
        return locator;
    }

    public String getLocatorWithoutPrefix() {
        String xpath = null;
        if (locator.startsWith("css="))
            xpath = locator.substring(4);
        else if (locator.startsWith("xpath="))
            xpath = locator.substring(6);
        else if (locator.startsWith("id="))
            xpath = locator.substring(3);

        return xpath;
    }
}
