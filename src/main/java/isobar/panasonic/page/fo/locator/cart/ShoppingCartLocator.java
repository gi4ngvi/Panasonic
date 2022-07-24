package isobar.panasonic.page.fo.locator.cart;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ShoppingCartLocator extends SeleniumFactory {
    protected static final String PRODUCT_NAME = "xpath=//a[text()='%s']/ancestor::tr";
    protected static final String PRODUCT_UNIT_PRICE = PRODUCT_NAME + "//td[@class='col price']//span[@class='price-excluding-tax']//span[@class='price']";
    protected static final String PRODUCT_SUB_PRICE = PRODUCT_NAME + "//td[@class='col subtotal']//span[@class='price-excluding-tax']//span[@class='price']";
    protected static final String PRODUCT_QTY = PRODUCT_NAME + "//td[@class='col qty']//input";
    protected static final String PRODUCT_QTY_ERROR = PRODUCT_NAME + "//td[@class='col qty']//div[@class='mage-error']";
    protected static final String PRODUCT_EDIT_BTN = PRODUCT_NAME + "/following-sibling::tr//a[@class='action action-edit']";
    protected static final String PRODUCT_DELETE = PRODUCT_NAME + "/following-sibling::tr//a[@class='action action-delete']";
    protected static final String PRODUCT_SUBTOTAL_WITH_VAT = PRODUCT_NAME + "//td[@class='col subtotal']//span[@class='price-including-tax']//span[@class='price']";
    protected static final String PRODUCT_PRICE_WITH_VAT = PRODUCT_NAME + "//td[@class='col price']//span[@class='price-including-tax']//span[@class='price']";
    protected static final String SHIPPING_METHOD = "xpath=//dt[@class='item-title']//span[normalize-space(text())='%s']//ancestor::dl[@class='items methods']//input";
    protected static final String DELETE_ALL_PRODUCT = ".action.action-delete";
    @FindBy(how = How.CSS, using = ".cart-summary .action.primary.checkout")
    protected WebElement weCheckOutBtn;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals sub']//td//span")
    protected WebElement weSubtotal;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals shipping excl']//td//span")
    protected WebElement weShippingFee;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals shipping incl']//td//span")
    protected WebElement weShippingFeeWithTax;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals-tax']//td//span")
    protected WebElement weTax;
    @FindBy(how = How.XPATH, using = "//tr[@class='grand totals']//td[@class='amount']//span")
    protected WebElement weGrandtotal;
    @FindBy(how = How.CSS, using = ".table-wrapper>.loading-mask")
    protected WebElement weCartLoadingMask;
    @FindBy(how = How.CSS, using = "button.action.update")
    protected WebElement weUpdateShoppingCart;
    @FindBy(how = How.CSS, using = ".button.btn-continue")
    protected WebElement weContinueShopping;
    @FindBy(how = How.CSS, using = "#block-shipping")
    protected WebElement weShippingSelection;
    @FindBy(how = How.ID, using = "s_method_freeshipping_freeshipping")
    protected WebElement weFreeShipping;
    @FindBy(how = How.CSS, using = ".cart-empty")
    protected WebElement weCartEmpty;
    @FindBy(how = How.XPATH,using = "//div[@id='cart-totals']//div[@class='loading-mask']")
    protected WebElement weCartTotalLoadingMask;

    public ShoppingCartLocator(WebDriver driver) {
        super(driver);
    }
}
