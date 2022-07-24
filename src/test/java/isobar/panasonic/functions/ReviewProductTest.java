package isobar.panasonic.functions;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ReviewProductTest extends SeleniumTest {
    CustomerInformation cus;
    SingleProduct product;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    String MSG_REVIEW_SUCCESS;
    StringUtility stringUtility;
    Map<String, String> reviews;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        product = DataTest.getSingleProductTest();
        MSG_REVIEW_SUCCESS = "You submitted your review for moderation.";
        reviews = new HashMap<>();
        stringUtility = new StringUtility();
    }

    @Test
    public void review(){
        headerPage.login(cus);
        productListPage = headerPage.gotoCategory(product.getCategory());
        productDetailsPage = productListPage.viewPDP(product);
        reviews.put("quantityRate", "3");
        reviews.put("nickName", "test_rating");
        reviews.put("summary", stringUtility.getRandomCharacter(5));
        reviews.put("content", stringUtility.getRandomCharacter(10));
        productDetailsPage.reviewProduct(reviews);
        Assert.assertEquals(headerPage.getSuccessMessage(), MSG_REVIEW_SUCCESS);
    }
}