package isobar.panasonic.functions.vn_vn;

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

public class WishListTest extends SeleniumTest {
    CustomerInformation cus;
    SingleProduct sp, bsp, bsp1;
    BundleProduct bd;
    MyWishlistPage wishListPage;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    String ADD_WISHLIST_SUCCESS;

    @Override
    protected void preCondition() {
        sp = DataTest.getSingleProductTest();
        cus = DataTest.getDefaultCustomerTest();
        bsp = DataTest.getSingleProductTest();
        bsp.qty(2);
        bsp1 = DataTest.getSingleProductTest1();
        bsp1.qty(2);
        bd = DataTest.getBundleProductTest();
        ADD_WISHLIST_SUCCESS = "%s đã được thêm vào danh sách yêu thích của bạn. Bấm vào đây để tiếp tục mua sắm.";
    }

    @Test
    public void add() {
        headerPage.login(cus);
        wishListPage = headerPage.gotoWishlist();
        wishListPage.clearAllWishlist();
        productDetailsPage = headerPage.search(sp.getName()).viewPDP(sp);
        wishListPage = productDetailsPage.addWishList();
        Assert.assertEquals(wishListPage.getSuccessMsg(), String.format(ADD_WISHLIST_SUCCESS, sp.getName()));
        productListPage = headerPage.search(bd.getName());
        productDetailsPage = productListPage.viewPDP(bd);
        wishListPage = productDetailsPage.addWishList();
        Assert.assertEquals(wishListPage.getSuccessMsg(), String.format(ADD_WISHLIST_SUCCESS, bd.getName()));
        Assert.assertTrue(wishListPage.checkProduct(sp));
        Assert.assertTrue(wishListPage.checkProduct(bd));
    }

    @Test(dependsOnMethods = "add")
    public void delete() {
        wishListPage = headerPage.gotoWishlist();
        wishListPage.delete(sp);
        Assert.assertFalse(wishListPage.checkProduct(sp));
        wishListPage.delete(bd);
        Assert.assertFalse(wishListPage.checkProduct(bd));
    }
}
