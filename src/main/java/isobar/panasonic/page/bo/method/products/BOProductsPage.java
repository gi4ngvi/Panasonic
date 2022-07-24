package isobar.panasonic.page.bo.method.products;

import isobar.panasonic.components.Label;
import isobar.panasonic.page.bo.locator.products.BOProductsLocator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class BOProductsPage extends BOProductsLocator {
    Label label;

    public BOProductsPage(WebDriver driver) {
        super(driver);
        label = new Label(driver);
    }

    private void quickSearch(String key) {
        weQuickSearch.clear();
        weQuickSearch.sendKeys(key);
        weQuickSearch.sendKeys(Keys.ENTER);
    }

    public BOProductDetailsPage gotoProductDetail(String sku) {
        quickSearch(sku);
        label.setLocator(String.format(EDIT_PRODUCT, sku));
        waitUtility.waitUntilToBeClickAble(label.getWebElement());
        label.click();
        return new BOProductDetailsPage(driver);
    }
}
