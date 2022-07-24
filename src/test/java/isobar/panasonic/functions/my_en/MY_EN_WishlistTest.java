package isobar.panasonic.functions.my_en;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.account.MyWishlistPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_WishlistTest extends SeleniumTest {
    private CustomerInformation cus;
    private SingleProduct singleProduct;
    private BundleProduct bundleProduct;
    private MyWishlistPage wishListPage;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private String MSG_ADD_WISHLIST_SUCCESS;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        singleProduct = DataTest.getSingleProductTest();
        bundleProduct = DataTest.getBundleProductTest();
        MSG_ADD_WISHLIST_SUCCESS = "%s has been added to your Wish List. Click here to continue shopping.";
    }

    @Test
    public void prepare(){
        headerPage.login(cus);
        wishListPage = headerPage.gotoWishlist();
        wishListPage.clearAllWishlist();
    }

    @Test(dependsOnMethods = "prepare")
    public void addSingleProduct(){
        productListPage = headerPage.search(singleProduct.getName());
        productDetailsPage = productListPage.viewPDP(singleProduct);
        wishListPage = productDetailsPage.addWishList();
        Assert.assertEquals(wishListPage.getSuccessMsg(), String.format(MSG_ADD_WISHLIST_SUCCESS, singleProduct.getName()));
        Assert.assertTrue(wishListPage.checkProduct(singleProduct));
    }

    @Test(dependsOnMethods = "addSingleProduct")
    public void addBundleProduct(){
        productListPage = headerPage.search(bundleProduct.getName());
        productDetailsPage = productListPage.viewPDP(bundleProduct);
        wishListPage = productDetailsPage.addWishList();
        Assert.assertEquals(wishListPage.getSuccessMsg(), String.format(MSG_ADD_WISHLIST_SUCCESS, bundleProduct.getName()));
        Assert.assertTrue(wishListPage.checkProduct(bundleProduct));
    }

    @Test(dependsOnMethods = "addBundleProduct")
    public void delete() {
        wishListPage = headerPage.gotoWishlist();
        wishListPage.delete(singleProduct);
        Assert.assertFalse(wishListPage.checkProduct(singleProduct));
        wishListPage.delete(bundleProduct);
        Assert.assertFalse(wishListPage.checkProduct(bundleProduct));
    }
}
