package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends SeleniumTest {
    SingleProduct sp;
    CustomerInformation cus;
    ProductListPage productListPage;

    @Override
    protected void preCondition() {
        sp = DataTest.getSingleProductTest1();
        cus = DataTest.getDefaultCustomerTest();
    }

    @Test
    public void searchBySKU() {
        headerPage.login(cus);
        productListPage = headerPage.search(sp.getSku());
        Assert.assertTrue(productListPage.checkProductExist(sp));
    }

    @Test(dependsOnMethods = "searchBySKU")
    public void searchByName() {
        productListPage = headerPage.search(sp.getName());
        Assert.assertTrue(productListPage.checkProductExist(sp));
    }

    @Test(dependsOnMethods = "searchByName")
    public void searchNoResult() {
        productListPage = headerPage.search("1111111111111111111111111111111");
        Assert.assertEquals(productListPage.getSearchResultMsg(), "Tìm kiếm của bạn không có kết quả.");
    }
}
