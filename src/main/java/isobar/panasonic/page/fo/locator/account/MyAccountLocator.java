package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MyAccountLocator extends SeleniumFactory {
    protected static final String ORDER_ID = "xpath=//ul[contains(@class,'orders-recent')]//li[%d]//span[@class='order-number']";
    protected static final String ORDER_STATUS = "xpath=//ul[contains(@class,'orders-recent')]//li[%d]//span[contains(@class,'status')]";
    protected static final String PRODUCT_ITEM = "xpath=//a[@class='product-item-link' and contains(@href,'%s')]";
    protected static final String DELETE_PRODUCT = PRODUCT_ITEM + "/ancestor::li[@class='product-item']//a[contains(@class,'btn-remove')]";
    protected static final String DELETE_CARD_ITEM = "xpath=//div[@id='form-credit-card']//input[@value='*************%s']/following-sibling::a";
    @FindBy(how = How.XPATH, using = "(//ul[contains(@class,'orders-recent')]//span[@class='order-number'])[1]")
    protected WebElement weFirstOrder;
    @FindBy(how = How.CSS, using = "a[href$='/orders/']")
    protected WebElement weMyOrder;
    @FindBy(how = How.CSS, using = "a.action.btn.primary[href*='/customer/address/']")
    protected WebElement weMyAddress;
    @FindBy(how = How.CSS, using = "a[href*='/customer/account/edit/']")
    protected WebElement weAccountInfomation;
    @FindBy(how = How.CSS, using = "div.dashboard .field.welcome-text")
    protected WebElement weContactName;
    @FindBy(how = How.CSS, using = "div.dashboard .field.email")
    protected WebElement weContactEmail;
    @FindBy(how = How.CSS, using = "a[href*='/wishlist/']")
    protected WebElement weMyWishlist;
    @FindBy(how = How.CSS, using = ".box-billing-address address")
    protected WebElement weDefaultBillingAddress;
    @FindBy(how = How.CSS, using = ".box-shipping-address address")
    protected WebElement weDefaultShippingAddress;
    @FindBy(how = How.CSS, using = ".box-billing-address a.edit")
    protected WebElement weEditBillingAddress;
    @FindBy(how = How.CSS, using = ".box-shipping-address a.edit")
    protected WebElement weEditShippingAddress;
    @FindBy(how = How.CSS, using = "#form-validate #advice-required-entry-email")
    protected WebElement weEmailRequiredError;
    @FindBy(how = How.CSS, using = "a[href*='/customer/account']")
    protected WebElement weAccountInfoTop;
    @FindBy(how = How.CSS, using = "a[href*='/customer/card'].primary")
    protected WebElement weMyCreditCard;
    @FindBy(how = How.CSS, using = "div.message-success div")
    protected WebElement weSuccessMsg;
    @FindBy(how = How.CSS, using = "div.message-error div")
    protected WebElement weErrorMsg;
    @FindBy(how = How.CSS, using = "a[href*='/customer/account/logout/']")
    protected WebElement weLogout;
    @FindBy(how = How.CSS, using = ".block-wishlist .empty")
    protected WebElement weWishlistEmpty;

    public MyAccountLocator(WebDriver driver) {
        super(driver);
    }
}
