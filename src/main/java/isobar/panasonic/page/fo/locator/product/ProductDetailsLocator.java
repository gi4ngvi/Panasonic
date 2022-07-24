package isobar.panasonic.page.fo.locator.product;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProductDetailsLocator extends SeleniumFactory {
    protected static final String QUANLITY_RATE = "id=Quality_%d";
    protected static final String QUANLITY_RATE_LABEL = "id=Quality_%d_label";
    protected static final String COLOR = "xpath=//div[@option-label='%s']";

    @FindBy(how = How.CSS, using = ".action.showcart")
    protected WebElement weMiniShowCart;
    @FindBy(how = How.CSS, using = ".minicart-wrapper .counter.qty .counter-number")
    protected WebElement weCartCountNum;
    @FindBy(how = How.ID, using = "product-addtocart-button")
    protected WebElement weAddToCart;
    @FindBy(how = How.ID, using = "qty")
    protected WebElement weQuantity;
    @FindBy(how = How.ID, using = "qty-error")
    protected WebElement weQtyError;
    @FindBy(how = How.CSS, using = "button[data-action='add-to-wishlist']")
    protected WebElement weAddWishlist;
    @FindBy(how = How.CSS, using = "a.action.tocompare")
    protected WebElement weAddCompare;
    @FindBy(how = How.CSS, using = ".message-success.success.message>div")
    protected WebElement weSuccessMsg;
    @FindBy(how = How.CSS,using = "div.product.media .loading-mask")
    protected WebElement weProductGalleryLoading;
    @FindBy(how = How.XPATH,using = "//form[@id='review-form']//button[contains(@class,'submit')]")
    protected WebElement weSubmit;
    @FindBy(how = How.ID,using = "nickname_field")
    protected WebElement weNickName;
    @FindBy(how = How.ID,using = "summary_field")
    protected WebElement weSummary;
    @FindBy(how = How.ID,using = "review_field")
    protected WebElement weReview;

    protected static final By byLoadingImage = By.cssSelector("div.loader");

    public ProductDetailsLocator(WebDriver driver) {
        super(driver);
    }
}
