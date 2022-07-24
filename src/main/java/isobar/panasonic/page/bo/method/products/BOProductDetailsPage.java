package isobar.panasonic.page.bo.method.products;

import isobar.panasonic.page.bo.locator.products.BOProductDetailsLocator;
import org.openqa.selenium.WebDriver;

public class BOProductDetailsPage extends BOProductDetailsLocator {
    public BOProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    private void expandInventoryTab() {
        waitUtility.waitUntilToBeClickAble(weInventoryTab);
        weInventoryTab.click();
    }

    public double getProductStock() {
        expandInventoryTab();
        String stockText = weProductStock.getText().replaceAll(",", "");
        return Double.valueOf(stockText);
    }
}
