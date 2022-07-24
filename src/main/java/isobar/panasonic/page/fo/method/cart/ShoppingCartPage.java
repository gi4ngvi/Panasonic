package isobar.panasonic.page.fo.method.cart;

import isobar.panasonic.components.Label;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.page.fo.locator.cart.ShoppingCartLocator;
import isobar.panasonic.page.fo.method.checkout.CheckoutPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.utility.HeaderPage;
import isobar.panasonic.utility.*;
import org.openqa.selenium.*;

public class ShoppingCartPage extends ShoppingCartLocator {

    HandleLoading handleLoading;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        handleLoading = new HandleLoading(driver);
        waitUtility.waitUntilVisibilityOf(weSubtotal);
        handleLoading.handleAjaxLoading();
    }

    public String getSubtotal() {
        return weSubtotal.getText();
    }

    public boolean checkSubtotal(int price) {
        return getSubtotal().equals(PriceUtility.convertPriceToString(price + DataTest.calculateTax(price)));
    }

    public String getTax() {
        return weTax.getText();
    }

    public String getTotal() {
        return weGrandtotal.getText();
    }

    public boolean checkTotal(int price) {
        return getTotal().equals(PriceUtility.convertPriceToString(price + DataTest.calculateTax(price)));
    }

    public String getShippingFeeWithTax() {
        return weShippingFeeWithTax.getText();
    }

    public String getShippingFee() {
        return weShippingFee.getText();
    }

    private boolean checkProductPrice(Product product) {
        Label lbProductUnitPrice = new Label(driver, String.format(PRODUCT_UNIT_PRICE, product.getName()));
        if (!lbProductUnitPrice.isDisplay()) {
            return false;
        }
        String price = lbProductUnitPrice.getText();
        ReportUtility.getInstance().logInfo(String.format("check ProductPrice of product: %s, expected: %s - actual: %s", product.getName(), PriceUtility.convertPriceToString(product.getPrice()), price));
        return price.equals(PriceUtility.convertPriceToString(product.getPrice()));
    }

    private boolean checkSubtotalPrice(Product product) {
        String gui, data;
        Label lbProductSubPrice = new Label(driver, String.format(PRODUCT_SUB_PRICE, product.getName()));
        if (!lbProductSubPrice.isDisplay()) {
            return false;
        }
        gui = lbProductSubPrice.getText();
        data = PriceUtility.convertPriceToString(product.getPrice() * product.getQty());
        ReportUtility.getInstance().logInfo(String.format("check SubtotalPrice of product: %s, expected: %s - actual: %s", product.getName(), data, gui));
        return gui.equals(data);
    }

    private boolean checkProductQty(Product product) {
        Label lbProductQty = new Label(driver, String.format(PRODUCT_QTY, product.getName()));
        if (!lbProductQty.isDisplay()) {
            return false;
        }
        int qty = Integer.parseInt(lbProductQty.getAttribute("value"));
        ReportUtility.getInstance().logInfo(String.format("check ProductQty of product: %s, expected: %s - actual: %s", product.getName(), product.getQty(), qty));
        return qty == product.getQty();
    }

    public boolean checkProduct(Product product) {
        boolean status;
        if (DataTest.getCountry() == Country.VN || DataTest.getCountry() == Country.MY) {
            status = checkProductPriceWithVAT(product);
            status &= checkProductSubtotalWithVAT(product);
        } else {
            status = checkProductPrice(product);
            status &= checkSubtotalPrice(product);
        }
        status &= checkProductQty(product);
        return status;
    }

    public CheckoutPage checkout() {
        String url = driver.getCurrentUrl();
        clickCheckout();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new CheckoutPage(driver);
    }

    private void clickCheckout() {
        waitUtility.waitUntilToBeClickAble(weCheckOutBtn);
        weCheckOutBtn.click();
    }

    public void setQuantity(Product product, int quantity) {
        setQuantity1(product, quantity);
    }

    private void setQuantity1(Product product, int quantity) {
        WebElement we = new Label(driver, String.format(PRODUCT_QTY, product.getName())).getWebElement();
        we.clear();
        we.sendKeys(String.valueOf(quantity));
    }

    public void setQuantityError(Product product, int quantity) {
        setQuantity1(product, quantity);
    }

    public ProductDetailsPage editQuantity(Product product) {
        WebElement we = new Label(driver, String.format(PRODUCT_EDIT_BTN, product.getName())).getWebElement();
        waitUtility.waitUntilToBeClickAble(we);
        we.click();
        return new ProductDetailsPage(driver);
    }

    public void delete(Product product) {
        HeaderPage headerPage = new HeaderPage(driver);
        String count = headerPage.getCartCount();
        Label lbDelete = new Label(driver, String.format(PRODUCT_DELETE, product.getName()));
        waitUtility.waitUntilToBeClickAble(lbDelete.getWebElement());
        actionUtility.scrollToElement(lbDelete.getWebElement(), 0, -300);
        lbDelete.click();
        headerPage.waitForCartCountUpdated(count);
    }

    public void updateShoppingCart() {
        waitUtility.waitUntilVisibilityOf(weGrandtotal);
        weUpdateShoppingCart.click();
        waitUtility.waitForPageLoad();
        waitUtility.waitUntilVisibilityOf(weSubtotal);
        handleLoading.handleAjaxLoading();
        handleLoading.handleLoadingMask(weCartTotalLoadingMask, 3);
    }

    private boolean checkProductSubtotalWithVAT(Product product) {
        String gui, data;
        Label lbProductSubtotal = new Label(driver, String.format(PRODUCT_SUBTOTAL_WITH_VAT, product.getName()));
        if (!lbProductSubtotal.isDisplay()) {
            return false;
        }
        gui = lbProductSubtotal.getText();
        int subtotal = product.getPrice() * product.getQty();
        data = PriceUtility.convertPriceToString(subtotal + subtotal * DataTest.getTaxRate() / 100);
        ReportUtility.getInstance().logInfo(String.format("check SubtotalWithVAT of product: %s, expected: %s - actual: %s",
                product.getName(), data, gui));
        return gui.equals(data);
    }

    private boolean checkProductPriceWithVAT(Product product) {
        String gui, data;
        Label lbProductPriceInclTax = new Label(driver, String.format(PRODUCT_PRICE_WITH_VAT, product.getName()));
        if (!lbProductPriceInclTax.isDisplay()) {
            return false;
        }
        gui = lbProductPriceInclTax.getText();
        data = PriceUtility.convertPriceToString(product.getPrice() + product.getPrice() * DataTest.getTaxRate() / 100);
        ReportUtility.getInstance().logInfo(String.format("check price of product: %s, expected: %s - actual: %s", product.getName(), data, gui));
        return gui.equals(data);
    }

    private void expandShippingSelection() {
        waitUtility.waitUntilToBeClickAble(weShippingSelection);
        if (weShippingSelection.getAttribute("class").equals("block shipping")) {
            weShippingSelection.findElement(By.cssSelector("div.title")).click();
        }
    }

    public void setFreeShipping() {
        handleLoading.handleAjaxLoading();
        expandShippingSelection();
        waitUtility.waitUntilToBeClickAble(weFreeShipping);
        if (!weFreeShipping.isSelected()) {
            weFreeShipping.click();
            handleLoading.handleLoadingMask();
            actionUtility.sleep(500);
        }
    }

    public String getQuantityError(Product product) {
        Label lbProductQtyError = new Label(driver, String.format(PRODUCT_QTY_ERROR, product.getName()));
        return lbProductQtyError.getText();
    }

    public String getCartEmptyMsg() {
        waitUtility.waitUntilVisibilityOf(weCartEmpty);
        return weCartEmpty.getText();
    }

    public void selectShippingMethod(ShippingMethod shippingMethod) {
        handleLoading.handleAjaxLoading();
        expandShippingSelection();
        Label weShippingMethod = new Label(driver, String.format(SHIPPING_METHOD, shippingMethod.toString()));
        if (!weShippingMethod.isChecked()) {
            weShippingMethod.click();
            handleLoading.handleLoadingMask();
            actionUtility.sleep(500);
        }
    }
}