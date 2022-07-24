package isobar.panasonic.page.bo.method.navigator;

import isobar.panasonic.page.bo.method.sale.BOOrderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BOSalesTab extends BOMainMenuPage {
    @FindBy(how = How.CSS, using = "li.item-sales-operation a[href*='/admin/sales/order/']")
    protected WebElement weOrder;

    public BOSalesTab(WebDriver driver) {
        super(driver);
    }

    public BOOrderPage gotoOrderPage() {
        waitUtility.waitUntilToBeClickAble(weOrder);
        weOrder.click();
        return new BOOrderPage(driver);
    }

    public void searchShippingName(String key) {

    }
}
