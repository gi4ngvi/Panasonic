package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class MyOrderLocator extends SeleniumFactory {
    protected static final String ORDER_ROW ="xpath=//table[@id='my-orders-table']//tbody/tr[%d]";
    protected static final String ORDER_ID = ORDER_ROW +"//td[@class='col id']";
    protected static final String ORDER_STATUS = ORDER_ROW +"//td[@class='col status']";
    protected static final String ORDER_TOTAL = ORDER_ROW +"//td[@class='col total']//span[@class='price']";
    protected static final String VIEW_ORDER = ORDER_ROW +"//td[@class='col actions']//a[@class='action view']";
    protected static final String PRODUCT_SKU = "xpath=//h3[contains(text(),'%s')]/ancestor::tr/td[2]";
    protected static final String PRODUCT_PRICE = "xpath=//h3[contains(text(),'%s')]/ancestor::tr/td[3]";
    protected static final String PRODUCT_QTY = "xpath=//h3[contains(text(),'%s')]/ancestor::tr/td[4]";
    protected static final String PRODUCT_SUBTOTAL = "xpath=//h3[contains(text(),'%s')]/ancestor::tr/td[5]";
    protected static final String ORDER_ACTIONS ="xpath=//table[@id='my-orders-table']//td[@class='col id' and normalize-space(text())='%s']/ancestor::tr//td[@class='col actions']";
    protected static final String REORDER =  ORDER_ACTIONS + "//a[@class='action order']";
    protected static final String ORDER_DETAILS = ORDER_ACTIONS + "//a[@class='action view']";
    @FindBy(how = How.CSS, using = "a[href*='/sales/order/view/order_id/']")
    protected List<WebElement> weListOrder;
    @FindBy(how = How.CSS, using = ".order-items .item .content>span")
    protected List<WebElement> weListOrderID;
    @FindBy(how = How.XPATH, using = "//tfoot//td[contains(text(),'Subtotal')]/following-sibling::td//span")
    protected WebElement weSubtotal;
    @FindBy(how = How.XPATH, using = "//tfoot//td[contains(text(),'Shipping & Handling')]/following-sibling::td//span")
    protected WebElement weShippingfee;
    @FindBy(how = How.CSS, using = "#my-orders-table tfoot tr:nth-child(3) span")
    protected WebElement weTax;
    @FindBy(how = How.XPATH, using = "//tfoot//strong[contains(text(),'Grand Total')]/../following-sibling::td//span")
    protected WebElement weGrandtotal;
    @FindBy(how = How.XPATH, using = "//h2[text()='Shipping Address']/ancestor::div[@class='box']//address")
    protected WebElement weShippingaddress;
    @FindBy(how = How.XPATH, using = "//h2[text()='Billing Address']/ancestor::div[@class='box']//address")
    protected WebElement weBillingAddress;
    @FindBy(how = How.XPATH, using = "//h2[text()='Shipping Method']/ancestor::div[@class='box']/div[@class='box-content']")
    protected WebElement weShippingmethod;
    @FindBy(how = How.XPATH, using = "//h2[text()='Payment Method']/ancestor::div[@class='box box-payment']/div[@class='box-content']")
    protected WebElement wePaymentmethod;
    @FindBy(how = How.CSS, using = ".order-status")
    protected WebElement weOrderstatus;
    @FindBy(how = How.CSS, using = "p.order-date")
    protected WebElement weOrderDate;
    @FindBy(how = How.CSS, using = ".page-title.title-buttons h1")
    protected WebElement weOrderID;

    public MyOrderLocator(WebDriver driver) {
        super(driver);
    }
}
