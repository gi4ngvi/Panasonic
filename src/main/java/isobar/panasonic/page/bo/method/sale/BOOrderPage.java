package isobar.panasonic.page.bo.method.sale;

import isobar.panasonic.components.Table;
import isobar.panasonic.page.bo.locator.sale.BOOrderLocator;
import org.openqa.selenium.WebDriver;

public class BOOrderPage extends BOOrderLocator {
    static final String HEADER_PURCHASE_DATE = "Purchase Date";
    static final String HEADER_ORDER_ID = "ID";
    Table orderTable;

    public BOOrderPage(WebDriver driver) {
        super(driver);
    }
}
