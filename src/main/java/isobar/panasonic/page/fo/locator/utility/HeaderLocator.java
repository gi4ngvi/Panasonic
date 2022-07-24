package isobar.panasonic.page.fo.locator.utility;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by hieu nguyen on 12/6/2017.
 */

public abstract class HeaderLocator extends SeleniumFactory {
    protected static final String PRODUCT_ITEM = "xpath=//ol[@id='mini-cart']//strong[@class='product-item-name']//a[normalize-space(text())='%s']/ancestor::div[@class='product-item-details']";
    protected static final String PRODUCT_PRICE = PRODUCT_ITEM +"//div[@class='product-item-pricing']//span[@class='price']";
    protected static final String PRODUCT_QTY = PRODUCT_ITEM +"//div[@class='details-qty qty']//select";
    protected static final String DELETE_ALL_PRODUCT = "#mini-cart .action.delete";
    protected static final String SELECT_STORE = "xpath=//div[@class='content-popup']//a[text()='%s']";
    protected static final String MENU_ITEM = "xpath=//ul[@role='menu']";
    protected static final String LINK_SITE_LANGUAGE = "xpath=//div[contains(@class,'switcher-language')]//a[contains(.,'%s')]";
    @FindBy(how = How.CSS, using = ".page-header a.login")
    protected WebElement weLogin;
    @FindBy(how = How.CSS, using = ".account-login")
    protected WebElement weAccountPopup;
    @FindBy(how = How.CSS, using = "button.action-primary.action-accept")
    protected WebElement weAcceptDeletion;
    @FindBy(how = How.CSS, using = ".greet.welcome span")
    protected WebElement weWelcomeMsg;
    @FindBy(how = How.ID, using = "search")
    protected WebElement weSearch;
    @FindBy(how = How.CSS, using = "a.action.showcart[href*='/checkout/cart/']")
    protected WebElement weShowCart;
    @FindBy(how = How.CSS, using = "a.action.viewcart[href*='/checkout/cart/']")
    protected WebElement weViewCart;
    @FindBy(how = How.CSS, using = "div.account-login a[href$='/customer/account/']")
    protected WebElement weAccountInfo;
    @FindBy(how = How.CSS, using = ".account-login .authorization-link")
    protected WebElement weLogout;
    @FindBy(how = How.CSS, using = ".account-login a[href*='/wishlist/']")
    protected WebElement weWishlist;
    @FindBy(how = How.CSS, using = ".account-login a[href*='/invitation/index/send/']")
    protected WebElement weSendInvite;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] button[type='button']")
    protected WebElement weRegisterSubmit;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] #first_name")
    protected WebElement weRegisterFirstname;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] #last_name")
    protected WebElement weRegisterLastname;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] #email")
    protected WebElement weRegisterEmail;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] #birth_month")
    protected WebElement weRegisterBirthdaymonth;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] #birth_year")
    protected WebElement weRegisterBirthdayyear;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] #password_one")
    protected WebElement weRegisterPassword;
    @FindBy(how = How.CSS, using = "#loginForm>form[action*='register'] #password_two")
    protected WebElement weRegisterPasswordconfirm;
    @FindBy(how = How.CSS, using = "div.message-error div")
    protected WebElement weErrorMsg;
    @FindBy(how = How.CSS, using = "div.message-success div")
    protected WebElement weSuccessMsg;
    @FindBy(how = How.CSS, using = "div.switcher-trigger")
    protected WebElement weLang;
    @FindBy(how = How.CSS, using = ".panel.header .dropdown.switcher-dropdown")
    protected WebElement weLangSwitcher;
    @FindBy(how = How.CSS, using = ".page-header a.isLogin")
    protected WebElement weCustomerSwitcher;
    @FindBy(how = How.CSS, using = ".customer-welcome span[data-bind*='fullname']")
    protected WebElement weCustomerName;
    @FindBy(how = How.CSS, using = ".action.showcart")
    protected WebElement weMiniShowCart;
    @FindBy(how = How.CSS, using = ".minicart-wrapper .counter.qty .counter-number")
    protected WebElement weCartCountNum;
    @FindBy(how = How.CSS, using = ".action.compare")
    protected WebElement weCompare;
    @FindBy(how = How.ID, using = "top-cart-btn-checkout")
    protected WebElement weCheckout;
    @FindBy(how = How.CSS,using = "div.choose-localtion-popup")
    protected WebElement weChooseLocationPopup;
    @FindBy(how = How.CSS,using = "a.action-register")
    protected WebElement weRegister;
    @FindBy(how = How.XPATH,using = "//nav//a[contains(@href,'ewarranty/index/index')]")
    protected WebElement weWarranty;
    @FindBy(how = How.ID,using = "btn-minicart-close")
    protected WebElement weMiniCartClose;
    @FindBy(how = How.CSS, using = ".popup-cookies .popup-close")
    protected WebElement weCloseCookiePopup;
    @FindBy(how = How.CSS, using = "aside[role='dialog']")
    protected WebElement weLoginPopup;

    public HeaderLocator(WebDriver driver) {
        super(driver);
    }
}
