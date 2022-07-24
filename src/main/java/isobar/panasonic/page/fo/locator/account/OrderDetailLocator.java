package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class OrderDetailLocator extends SeleniumFactory {
    protected static final String PRODUCT_PREFIX = "xpath=//strong[text()='%s']/ancestor::tr";
    protected static final String PRODUCT_SKU = PRODUCT_PREFIX + "//td[@class='col sku']";
    protected static final String PRODUCT_PRICE = PRODUCT_PREFIX + "//td[@class='col price']//span[@class='price-excluding-tax']//span[@class='price']";
    protected static final String PRODUCT_PRICE_WITH_VAT = PRODUCT_PREFIX + "//td[@class='col price']//span[@class='price-including-tax']//span[@class='price']";
    protected static final String PRODUCT_QTY = PRODUCT_PREFIX + "//td[@class='col qty']//span[@class='content']";
    protected static final String PRODUCT_SUBTOTAL = PRODUCT_PREFIX + "//td[@class='col subtotal']//span[@class='price-excluding-tax']//span[@class='price']";
    protected static final String PRODUCT_SUBTOTAL_WITH_VAT = PRODUCT_PREFIX + "//td[@class='col subtotal']//span[@class='price-including-tax']//span[@class='price']";
    @FindBy(how = How.CSS, using = "a[href*='/sales/order/view/order_id/']")
    protected List<WebElement> weListOrder;
    @FindBy(how = How.CSS, using = ".order-items .item .content>span")
    protected List<WebElement> weListOrderID;
    @FindBy(how = How.XPATH, using = "//tfoot//tr[@class='subtotal']//span[@class='price']")
    protected WebElement weSubtotal;
    @FindBy(how = How.XPATH, using = "//tfoot//tr[@class='shipping']//span[@class='price']")
    protected WebElement weShippingfee;
    @FindBy(how = How.XPATH, using = "//tfoot//tr[@class='totals-tax']//span[@class='price']")
    protected WebElement weTax;
    @FindBy(how = How.XPATH, using = "//tfoot//tr[@class='grand_total']//span[@class='price']")
    protected WebElement weGrandtotal;
    @FindBy(how = How.CSS, using = ".order-status")
    protected WebElement weOrderstatus;
    @FindBy(how = How.CSS, using = ".box-order-shipping-address address")
    protected WebElement weShippingaddress;
    @FindBy(how = How.CSS, using = ".box-order-billing-address address")
    protected WebElement weBillingAddress;
    @FindBy(how = How.CSS, using = ".box-order-shipping-method div")
    protected WebElement weShippingmethod;
    @FindBy(how = How.CSS, using = ".box-order-billing-method dt")
    protected WebElement wePaymentmethod;
    @FindBy(how = How.CSS, using = "div.order-date date")
    protected WebElement weOrderDate;
    @FindBy(how = How.CSS, using = "h1.page-title span")
    protected WebElement weOrderID;
    @FindBy(how = How.CSS,using = "tr.discount th.mark")
    protected WebElement weCouponTitle;
    @FindBy(how = How.CSS,using = "tr.discount span.price")
    protected WebElement weCouponAmount;
    @FindBy(how = How.CSS,using = "a.action.order")
    protected WebElement weReOrder;

    public OrderDetailLocator(WebDriver driver) {
        super(driver);
    }
}
