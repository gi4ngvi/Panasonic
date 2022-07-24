package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MyWishlistLocator extends SeleniumFactory {
    protected static final String ALL_WISHLIST = "//form[@id='wishlist-view-form']//ol/li";
    protected static final String WISHLIST_ITEM = "xpath=//a[@class='product-item-link'][contains(text(),'%s')]";
    protected static final String PRODUCT_UNIT_PRICE = WISHLIST_ITEM + "/ancestor::li//span[@class='price-wrapper price-excluding-tax']/span";
    protected static final String PRODUCT_PRICE_WITH_VAT = WISHLIST_ITEM + "/ancestor::li//span[@data-price-type='finalPrice']";
    protected static final String PRODUCT_QTY = WISHLIST_ITEM + "/ancestor::li//input[@class='input-text qty']";
    protected static final String PRODUCT_DELETE = WISHLIST_ITEM + "/ancestor::li//a[@class='btn-remove action delete']";
    protected static final String BTN_DELETE = "//a[@class='btn-remove action delete']";
    @FindBy(how = How.CSS, using = ".message.info.empty")
    protected WebElement weWishlistEmpty;
    @FindBy(how = How.CSS, using = ".message-success.success.message")
    protected WebElement weWishlistSuccess;

    public MyWishlistLocator(WebDriver driver) {
        super(driver);
    }
}
