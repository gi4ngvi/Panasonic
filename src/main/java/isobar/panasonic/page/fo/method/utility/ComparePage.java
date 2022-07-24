package isobar.panasonic.page.fo.method.utility;

import isobar.panasonic.components.Label;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.page.fo.locator.utility.CompareLocator;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ComparePage extends CompareLocator {
    public ComparePage(WebDriver driver) {
        super(driver);
    }

    public boolean checkProduct(Product product) {
        // Site VN and MY is only display price with VAT
        boolean status = checkProductPriceWithVAT(product);
        // status &= checkProductSKU(product);        // New GUI does not have sku now
        status &= checkProductName(product);
        return status;
    }

    public boolean checkProductPrice(Product product) {
        String gui, data;
        Label weProductPrice = new Label(driver, String.format(PRODUCT_UNIT_PRICE, product.getName()));
        gui = weProductPrice.getText();
        data = PriceUtility.convertPriceToString(product.getPrice());
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking price of product GUI: %s, DATA: %s", gui, data));
            return false;
        }
        return true;

    }

    private boolean checkProductPriceWithVAT(Product product) {
        String gui, data;
        Label weProductPriceWithVAT = new Label(driver, String.format(PRODUCT_PRICE_WITH_VAT, product.getId()));
        if (!weProductPriceWithVAT.isDisplay()) return false;
        gui = weProductPriceWithVAT.getText();
        data = PriceUtility.convertPriceToString(product.getPrice() + product.getPrice() * DataTest.getTaxRate() / 100);
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking PriceWithVAT of product GUI: %s, DATA: %s", gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductSKU(Product product) {
        String gui, data;
        int index = 0;
        List<WebElement> listName = new Label(driver, String.format(PRODUCT_NAME_LIST, product.getName())).getWebElements();
        for (int i = 0; i < listName.size(); i++) {
            if (listName.get(i).getText().indexOf(product.getName()) != -1) index = i + 1;
        }
        Label weProductSKU = new Label(driver, String.format(PRODUCT_SKU, String.valueOf(index)));
        gui = weProductSKU.getText();
        data = product.getSku();
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking SKU of product %s GUI: %s, DATA: %s", gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductName(Product product) {
        String gui, data;
        Label productName = new Label(driver, String.format(PRODUCT_NAME, product.getName()));
        if (!productName.isDisplay()) {
            return false;
        }
        gui = productName.getText();
        data = product.getName();
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking name of product GUI: %s, DATA: %s", gui, data));
            return false;
        }
        return true;
    }

    public void deleteAllCompare() {
        List<WebElement> listBtnDelete;
        WebElement btnDelete;
        listBtnDelete = driver.findElements(By.cssSelector(DELETE_PRODUCT));
        while (!listBtnDelete.isEmpty()) {
            waitUtility.waitForPageLoad();
            btnDelete = listBtnDelete.get(0);
            waitUtility.waitUntilToBeClickAble(btnDelete);
            btnDelete.click();
            actionUtility.sleep(500);
            waitUtility.waitUntilToBeClickAble(weAcceptDeletion);
            weAcceptDeletion.click();
            waitUtility.waitForPageLoad();
            listBtnDelete = driver.findElements(By.cssSelector(DELETE_PRODUCT));
        }
        waitUtility.waitUntilVisibilityOf(weMessageInfoEmpty);
    }
}

