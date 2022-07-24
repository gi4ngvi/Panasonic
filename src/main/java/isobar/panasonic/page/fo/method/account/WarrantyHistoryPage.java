package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.components.Label;
import isobar.panasonic.entity.data.Warranty;
import isobar.panasonic.page.fo.locator.account.WarrantyHistoryLocator;
import org.openqa.selenium.WebDriver;

public class WarrantyHistoryPage extends WarrantyHistoryLocator {
    public WarrantyHistoryPage(WebDriver driver) {
        super(driver);
    }

    public void viewWarranty(Warranty warranty) {
        Label weViewWarranty = new Label(driver, String.format(VIEW_WARRANTY, warranty.getProductName()));
        waitUtility.waitUntilToBeClickAble(weViewWarranty.getWebElement());
        weViewWarranty.click();
        waitUtility.waitForPageLoad();
    }

    public String getProductCategory(){
        waitUtility.waitUntilVisibilityOf(weProductCategory);
        return weProductCategory.getText().trim();
    }

    public String getProductSKU(){
        return weProductSKU.getText().trim();
    }

    public String getSerialNo(){
        return weSerialNo.getText().trim();
    }
}
