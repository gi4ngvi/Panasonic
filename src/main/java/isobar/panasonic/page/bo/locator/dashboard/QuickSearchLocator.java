package isobar.panasonic.page.bo.locator.dashboard;


import isobar.panasonic.components.Button;
import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by Hieu Nguyen on 10/2/2017.
 */

public class QuickSearchLocator extends SeleniumFactory {
    protected static final String WARNING_POPUP = "css=aside.modal-popup.modal-system-messages.ui-popup-message._show>div>header>button.action-close";
    @FindBy(how = How.CSS, using = "button[data-action='grid-filter-expand']")
    protected WebElement weFilters;
    @FindBy(how = How.CSS, using = "button[data-action='grid-filter-reset']")
    protected WebElement weReset;
    @FindBy(how = How.CSS, using = "button[data-action='grid-filter-apply']")
    protected WebElement weApply;
    @FindBy(how = How.CSS, using = "div#container div.admin__data-grid-loading-mask")
    protected WebElement weLoadingMask;

    @FindBy(how = How.CSS, using = "input[name='shipping_name']")
    protected WebElement weSearchByShippingName;

    public QuickSearchLocator(WebDriver driver) {
        super(driver);
    }
}
