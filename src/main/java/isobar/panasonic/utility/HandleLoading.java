package isobar.panasonic.utility;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HandleLoading extends WaitUtility {
    @FindBy(how = How.CSS, using = "aside.modal-popup.modal-system-messages.ui-popup-message:nth-child(1) button")
    protected WebElement closeSystemPopup;
    @FindBy(how = How.CSS, using = ".loading-mask")
    protected WebElement wePageLoadingMask;
    @FindBy(how = How.CSS, using = ".ajax-loading")
    protected WebElement weAjaxLoading;
    @FindBy(how = How.TAG_NAME, using = "body")
    protected WebElement weBodyLoading;

    private static final By byLoadingMask = By.cssSelector(".loading-mask");
    private static final By byAjaxLoading = By.cssSelector(".ajax-loading");

    public HandleLoading(WebDriver driver) {
        super(driver);
        initComponents();
    }

    private void initComponents() {
        PageFactory.initElements(driver, this);
    }

    /**
     * If tag div.loading-mask has attribute display block, wait until the attribute changed to display none, otherwise wait until timeout and do not thing
     */
    public void handleLoadingMask() {
        handleLoadingMask(wePageLoadingMask, 1);
    }

    public void handleLoadingMask(int sec) {
        handleLoadingMask(wePageLoadingMask, sec);
    }

    public void handleLoadingMask(WebElement mask) {
        handleLoadingMask(mask, 1);
    }

    public void handleLoadingMask(WebElement mask, int seconds) {
        try {
            waitUntilVisibilityOf(mask, seconds);
            waitForValueOfAttribute(mask, "style", "display: none;");
        } catch (TimeoutException ex) {
        }
    }

    /**
     * If class .ajax-loading is attached to tag body, wait until the class is deattached, otherwise wait until timeout and do not thing
     */
    public void handleAjaxLoading() {
        handleAjaxLoading(weAjaxLoading, 1);
    }

    public void handleAjaxLoading(int seconds) {
        handleAjaxLoading(weAjaxLoading, seconds);
    }

    public void handleAjaxLoading(WebElement mask, int seconds) {
        try {
            waitUntilVisibilityOf(mask, seconds);
            waitForNotPresentOf(byAjaxLoading);
        } catch (TimeoutException ex) {
            ReportUtility.getInstance().logInfo("Handle ajax fail " + ex.getMessage());
        }
    }

    public void closeCachePopup() {
        try {
            waitUntilVisibilityOf(closeSystemPopup, 1);
            waitUntilToBeClickAble(closeSystemPopup);
            closeSystemPopup.click();
        } catch (TimeoutException ex) {
        } catch (NoSuchElementException ex1) {
        }
    }
}
