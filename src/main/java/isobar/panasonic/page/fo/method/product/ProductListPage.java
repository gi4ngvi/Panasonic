package isobar.panasonic.page.fo.method.product;

import isobar.panasonic.components.Link;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.page.fo.locator.product.ProductListLocator;
import isobar.panasonic.page.fo.method.utility.HeaderPage;
import isobar.panasonic.utility.ActionUtility;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductListPage extends ProductListLocator {
    private ActionUtility actionUtility;
    private WebElement link;

    public ProductListPage(WebDriver driver) {
        super(driver);
        actionUtility = new ActionUtility(driver);
    }

    public ProductDetailsPage viewPDP(Product product) {
        String url = driver.getCurrentUrl();
        link = new Link(driver, String.format(PRODUCT_ITEM, product.getUrl())).getWebElement();
        waitUtility.waitForPageLoad();
        waitUtility.waitUntilToBeClickAble(link);
        hoverProduct(product);
        actionUtility.scrollToElement(link);
        link.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        Panasonic.checkStoreView(driver);
        return new ProductDetailsPage(driver);
    }

    public void addToCart(Product product) {
        HeaderPage headerPage = new HeaderPage(driver);
        int count = parseCartCountNum(headerPage.getCartCount().trim());
        link = new Link(driver, String.format(ADD_CART_BTN, product.getUrl())).getWebElement();
        waitUtility.waitUntilToBeClickAble(link);
        actionUtility.scrollToElement(link, 0, -500);
        hoverProduct(product);
        link.click();
        headerPage.waitProductAddedToCart(String.valueOf(count + 1));
    }

    private int parseCartCountNum(String count) {
        if (count.equals("")) return 0;
        return Integer.parseInt(count);
    }

    private void hoverProduct(Product product) {
        actionUtility.mouseMove(new Link(driver, String.format(ADD_CART_BOX, product.getUrl())).getWebElement());
    }

    public boolean checkProductExist(Product product) {
        findProductOnList(product);
        return new Link(driver, String.format(ADD_CART_BTN, product.getUrl())).isDisplay();
    }

    public String getSearchResultMsg() {
        return weSearchMsg.getText();
    }

    public void findProductOnList(Product product) {
        while (true) {
            try {
                new Link(driver, String.format(PRODUCT_ITEM, product.getUrl())).getWebElement();
                break;
            } catch (NoSuchElementException e) {
                if (!isNextDisplayed()) break;
                weNextBtn.click();
            }
        }
    }

    private boolean isNextDisplayed() {
        try {
            waitUtility.waitUntilToBeClickAble(weNextBtn);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean checkAddCartMessage(Product p) {
        waitUtility.waitUntilVisibilityOf(weAddCartMsg);
        String msg = String.format("Bạn đã thêm %s vào giỏ mua hàng của bạn.", p.getName());
        return weAddCartMsg.getText().equals(msg);
    }

    public void setModeListView() {
        weModeListView.click();
        waitUtility.waitForPageLoad();
    }
}
