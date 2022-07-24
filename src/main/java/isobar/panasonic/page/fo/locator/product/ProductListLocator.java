package isobar.panasonic.page.fo.locator.product;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProductListLocator extends SeleniumFactory {
    protected static final String ADD_CART_BOX = "xpath=//a[contains(@href,'%s')]/ancestor::div[contains(@class,'product-item-info')]";
    protected static final String ADD_CART_BTN = ADD_CART_BOX + "//button[@class='action tocart primary']";
    protected static final String PRODUCT_ITEM = ADD_CART_BOX + "//a[@class='product-item-link']";
    @FindBy(how = How.CSS, using = ".next.i-next")
    protected WebElement weNextBtn;
    @FindBy(how = How.CSS, using = ".message.notice>div")
    protected WebElement weSearchMsg;
    @FindBy(how = How.CSS, using = "div[data-bind='html: message.text']")
    protected WebElement weAddCartMsg;
    @FindBy(how = How.XPATH,using = "(//div[@class='toolbar toolbar-products'])[1]//a[@id='mode-list']")
    protected WebElement weModeListView;

    public ProductListLocator(WebDriver driver) {
        super(driver);
    }
}
