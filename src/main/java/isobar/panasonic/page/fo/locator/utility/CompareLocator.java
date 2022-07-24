package isobar.panasonic.page.fo.locator.utility;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CompareLocator extends SeleniumFactory {
    protected static final String PRODUCT_NAME = "xpath=//strong[@class='product-item-name']//a[contains(text(),'%s')]";
    protected static final String PRODUCT_UNIT_PRICE = PRODUCT_NAME + "//span[@class='price-wrapper price-excluding-tax']/span";
    protected static final String PRODUCT_PRICE_WITH_VAT = "css=div[data-product-id='%s'] span.price";
    protected static final String PRODUCT_SKU = "xpath=//span[@class='attribute label'][contains(text(), 'SKU')]/ancestor::tr//td[%s]//div[@class='attribute value']";
    protected static final String PRODUCT_NAME_LIST = "xpath=//table//tbody[1]//strong[@class='product-item-name']//a";
    protected static final String DELETE_PRODUCT = "a.action.delete";
    @FindBy(how = How.CSS, using = "div.message.info.empty div")
    protected WebElement weMessageInfoEmpty;
    @FindBy(how = How.CSS, using = "button.action-primary.action-accept")
    protected WebElement weAcceptDeletion;
    public CompareLocator(WebDriver driver) {
        super(driver);
    }
}
