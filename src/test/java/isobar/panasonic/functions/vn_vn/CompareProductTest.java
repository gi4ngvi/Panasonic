package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.utility.ComparePage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CompareProductTest extends SeleniumTest {
    SingleProduct sp, sp1;
    CustomerInformation cus;
    ProductDetailsPage productDetailsPage;
    ComparePage comparePage;
    String MSG_ADD_PRODUCT_SUCCESS;

    @Override
    protected void preCondition() {
        sp = DataTest.getSingleProductTest();
        sp1 = DataTest.getSingleProductTest1();
        cus = DataTest.getDefaultCustomerTest();
        MSG_ADD_PRODUCT_SUCCESS = "Bạn đã thêm sản phẩm %s vào danh sách so sánh.";
    }

    @Test
    public void addCompare() {
        headerPage.login(cus);
        headerPage.clearCart();
        productDetailsPage = headerPage.search(sp.getName()).viewPDP(sp);
        productDetailsPage.addCompare();
        Assert.assertEquals(productDetailsPage.getSuccessMsg(), String.format(MSG_ADD_PRODUCT_SUCCESS, sp.getName()));
        productDetailsPage = headerPage.search(sp1.getName()).viewPDP(sp1);
        productDetailsPage.addCompare();
        Assert.assertEquals(productDetailsPage.getSuccessMsg(), String.format(MSG_ADD_PRODUCT_SUCCESS, sp1.getName()));
    }

    @Test(dependsOnMethods = "addCompare")
    public void checkCompare() {
        comparePage = headerPage.gotoComparePage();
        Assert.assertTrue(comparePage.checkProduct(sp));
        Assert.assertTrue(comparePage.checkProduct(sp1));
    }
}
