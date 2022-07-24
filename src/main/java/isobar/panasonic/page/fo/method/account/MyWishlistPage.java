package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.components.Label;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.page.fo.locator.account.MyWishlistLocator;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class MyWishlistPage extends MyWishlistLocator {
    public MyWishlistPage(WebDriver driver) {
        super(driver);
    }

    public void clearAllWishlist() {
        WebElement weBlockProduct, btnDelete;
        List<WebElement> listEle = driver.findElements(By.xpath(ALL_WISHLIST));
        while (!listEle.isEmpty()) {
            waitUtility.waitForPageLoad();
            weBlockProduct = listEle.get(0);
            hoverWishItem(weBlockProduct);
            btnDelete = weBlockProduct.findElement(By.xpath(BTN_DELETE));
            btnDelete.click();
            listEle = driver.findElements(By.xpath(ALL_WISHLIST));
        }
        waitUtility.waitUntilVisibilityOf(weWishlistEmpty);
    }

    private void hoverWishItem(WebElement e) {
        waitUtility.waitUntilToBeClickAble(e);
        actionUtility.scrollToElement(e, 0, -300);
        actionUtility.mouseMove(e);
    }

    public String getSuccessMsg() {
        waitUtility.waitUntilVisibilityOf(weWishlistSuccess);
        return weWishlistSuccess.getText();
    }

    public boolean checkProduct(Product product) {
        Label weProduct = new Label(driver, String.format(WISHLIST_ITEM, product.getName()));
        if (!weProduct.isDisplay()) {
            return false;
        }
        hoverWishItem(weProduct.getWebElement());
        boolean status = checkProductQty(product);
        if (DataTest.getCountry() == Country.VN || DataTest.getCountry() == Country.MY) {
            status &= checkProductPriceWithVAT(product);
        } else {
            status &= checkProductPrice(product);
            status &= checkProductPriceWithVAT(product);
        }
        return status;
    }

    public boolean checkProductPrice(Product product) {
        String gui, data;
        Label weProductPrice = new Label(driver, String.format(PRODUCT_UNIT_PRICE, product.getName()));
        if(!weProductPrice.isDisplay()){
            return false;
        }
        gui = weProductPrice.getText();
        data = PriceUtility.convertPriceToString(product.getPrice());
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking sku of product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductPriceWithVAT(Product product) {
        String gui, data;
        Label weProductPriceWithVAT = new Label(driver, String.format(PRODUCT_PRICE_WITH_VAT, product.getName()));
        if (!weProductPriceWithVAT.isDisplay()) return false;
        gui = weProductPriceWithVAT.getText();
        data = PriceUtility.convertPriceToString(product.getPrice() + product.getPrice() * DataTest.getTaxRate() / 100);
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking PriceWithVAT product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    public boolean checkProductQty(Product product) {
        Label weProductQty = new Label(driver, String.format(PRODUCT_QTY, product.getName()));
        if (!weProductQty.isDisplay()) return false;
        int qty = Integer.parseInt(weProductQty.getAttribute("value"));
        if (qty != product.getQty()) {
            ReportUtility.getInstance().logFail(String.format("checking qty product %s GUI: %s, DATA: %s", product.getName(), qty, product.getQty()));
            return false;
        }
        return true;
    }

    public void delete(Product product) {
        Label weBlockProduct, btnDelete;
        weBlockProduct = new Label(driver, String.format(WISHLIST_ITEM, product.getName()));
        hoverWishItem(weBlockProduct.getWebElement());
        btnDelete = new Label(driver, String.format(PRODUCT_DELETE, product.getName()));
        waitUtility.waitUntilToBeClickAble(btnDelete.getWebElement());
        btnDelete.click();
        waitUtility.waitForPageLoad();
    }
}

