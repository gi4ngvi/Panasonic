package isobar.panasonic.page.fo.method.product;

import isobar.panasonic.components.Label;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.product.*;
import isobar.panasonic.page.fo.locator.product.ProductDetailsLocator;
import isobar.panasonic.page.fo.method.account.MyWishlistPage;
import isobar.panasonic.utility.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProductDetailsPage extends ProductDetailsLocator {
    private Label label;
    private HandleLoading handleLoading;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        label = new Label(driver);
        handleLoading = new HandleLoading(driver);
        waitUtility.waitForInvisibilityOf(byLoadingImage);
    }

    public void addToCart() {
        String count = getCartCount();
        addToCart1();
        waitForCartCountUpdated(count);
    }

    private String getCartCount() {
        waitUtility.waitUntilVisibilityOf(weMiniShowCart);
        //handleLoading.handleAjaxLoading();
        try {
            return weCartCountNum.getText();
        } catch (TimeoutException ex) {
            return "0";
        }
    }

    private void waitForCartCountUpdated(String oldNum) {
        handleLoading.handleAjaxLoading();
        String expectedValue = oldNum.equals("") ? "0" : oldNum;
        waitUtility.waitForValueOfAttributeDoesNotContains(weCartCountNum, "innerText", expectedValue);
    }

    private void addToCart1() {
        waitUtility.waitUntilToBeClickAble(weAddToCart);
        weAddToCart.click();
    }

    public void addToCartError() {
        addToCart1();
    }

    public void addToCart(Product product) {
        if (product.getType() == ProductType.CONFIGURATION) {
            selectColor(((ConfigurationProduct) product).getColor());
        }
        addToCart();
    }

    public void selectColor(String color) {
        waitForProductGalleryLoadingSuccess();
        label.setLocator(String.format(COLOR, color));
        label.click();
    }

    public void setQuantity(int quantity) {
        waitForProductGalleryLoadingSuccess();
        weQuantity.clear();
        weQuantity.sendKeys(String.valueOf(quantity));
        waitUtility.waitForValueOfAttribute(weQuantity, "value", String.valueOf(quantity));
    }

    public void setQuantityNull() {
        weQuantity.clear();
    }

    public String getQuantityError() {
        return weQtyError.getText();
    }

    private String getBundleOption(Product p) {
        Country country = DataTest.getCountry();
        if (country == Country.VN) {
            return String.format("%s +%s (Chưa bao gồm thuế: %s)", p.getName(), PriceUtility.convertPriceToString(p.getPrice() + DataTest.calculateTax(p.getPrice())), PriceUtility.convertPriceToString(p.getPrice()));
        } else if (country == Country.MY) {
            return String.format("%s +%s (Excl. tax: %s)", p.getName(), PriceUtility.convertPriceToString(p.getPrice() + DataTest.calculateTax(p.getPrice())), PriceUtility.convertPriceToString(p.getPrice()));
        } else {
            return String.format("%s +%s", p.getName(), PriceUtility.convertPriceToString(p.getPrice()));
        }
    }

    public MyWishlistPage addWishList() {
        String url = driver.getCurrentUrl();
        waitUtility.waitUntilToBeClickAble(weAddWishlist);
        weAddWishlist.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new MyWishlistPage(driver);
    }

    public void addCompare() {
        waitUtility.waitUntilToBeClickAble(weAddCompare);
        weAddCompare.click();
        waitUtility.waitForPageLoad();
    }

    public String getSuccessMsg() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText().trim();
    }

    private void submitReview() {
        waitUtility.waitUntilToBeClickAble(weSubmit);
        weSubmit.click();
    }

    private void setRate(int quantityRate) {
        Label weQuantityRate, weQuantityRateLabel;
        if (quantityRate > 0) {
            weQuantityRate = new Label(driver, String.format(QUANLITY_RATE, quantityRate));
            if (!weQuantityRate.isChecked()) {
                weQuantityRateLabel = new Label(driver, String.format(QUANLITY_RATE_LABEL, quantityRate));
                waitUtility.waitUntilToBeClickAble(weQuantityRateLabel.getWebElement());
                actionUtility.mouseClick(weQuantityRateLabel.getWebElement());
            }
        }
    }

    private void setNickNameReview(String nickName) {
        weNickName.clear();
        weNickName.sendKeys(nickName);
    }

    private void setSummaryReview(String summary) {
        weSummary.clear();
        weSummary.sendKeys(summary);
    }

    private void setReviewContent(String reviewContent) {
        weReview.clear();
        weReview.sendKeys(reviewContent);
    }

    public void reviewProduct(Map<String, String> reviews) {
        setRate(Integer.valueOf(reviews.get("quantityRate")));
        setNickNameReview(reviews.get("nickName"));
        setSummaryReview(reviews.get("summary"));
        setReviewContent(reviews.get("content"));
        submitReview();
    }

    private void waitForProductGalleryLoadingSuccess(){
        handleLoading.handleLoadingMask(weProductGalleryLoading);
        handleLoading.handleAjaxLoading();
    }
}

