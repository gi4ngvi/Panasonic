package isobar.panasonic.page.bo.locator.customer;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BOAllCustomerLocator extends SeleniumFactory {
    public static final String CUSTOMER_EDIT = "xpath=//div[text()='%1$s']/ancestor::tr//a[@class='action-menu-item']";
    public  static final String CUSTOMER_TICK = "xpath=//div[text()='%s']/ancestor::tr//input[@class='admin__control-checkbox']";
    @FindBy(how = How.CSS, using = ".admin__data-grid-filters-wrap._show input[name='email']")
    protected WebElement weFilterEmail;
    @FindBy(how = How.XPATH, using = "//div[@class='admin__data-grid-header-row row row-gutter']//button[@class='action-select']")
    protected WebElement weStickyAction;
    @FindBy(how = How.XPATH, using = "//div[@class='admin__data-grid-header-row row row-gutter']//span[text()='Delete']")
    protected WebElement weStickyDelete;
    @FindBy(how = How.CSS, using = ".action-primary.action-accept")
    protected WebElement weConfirmDel;
    @FindBy(how = How.NAME,using = "website_id")
    protected WebElement weFilterWebSite;
    @FindBy(how = How.CSS,using = "div.admin__data-grid-header-row.row.row-gutter div.admin__control-support-text")
    protected WebElement weCountRecordSelected;
    @FindBy(how = How.XPATH,using = "//div[@class='admin__data-grid-header']//div[@class='admin__data-grid-filters-current _show']//ul[@class='admin__current-filters-list']")
    protected WebElement weFilterActive;
    public BOAllCustomerLocator(WebDriver driver) {
        super(driver);
    }
}