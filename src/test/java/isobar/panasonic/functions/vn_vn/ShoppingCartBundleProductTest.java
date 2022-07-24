package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartBundleProductTest extends SeleniumTest {
    SingleProduct sp, bsp, bsp1;
    CustomerInformation cus;
    BundleProduct bd;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    String QUANTITY_ERROR, SHOPPINGCART_QUANTITY_ERROR, CART_EMPTY_MSG;

    @Override
    protected void preCondition() {
        sp = DataTest.getSingleProductTest();
        bsp = DataTest.getSingleProductTest();
        bsp.qty(2);
        bsp1 = DataTest.getSingleProductTest1();
        bsp1.qty(2);
        bd = DataTest.getBundleProductTest();
        cus = DataTest.getDefaultCustomerTest();
        QUANTITY_ERROR = "Vui lòng nhập một số hợp lệ trong lĩnh vực này.";
        SHOPPINGCART_QUANTITY_ERROR = "Vui lòng nhập số lớn hơn 0.";
        CART_EMPTY_MSG = "Bạn có không có sản phẩm nào trong giỏ hàng của bạn.\n" +
                "Bấm vào đây để tiếp tục mua sắm.";
    }

    @Test
    public void addNagativeQuantityCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(sp.getName());
        productListPage.addToCart(sp);
        Assert.assertTrue(productListPage.checkAddCartMessage(sp));
        Assert.assertEquals(headerPage.getCartCount(), "1");
        productListPage = headerPage.search(bd.getName());
        productDetailsPage = productListPage.viewPDP(bd);
        productDetailsPage.setQuantity(-1);
        productDetailsPage.addToCartError();
        Assert.assertEquals(headerPage.getCartCount(), "1");
    }

    @Test(dependsOnMethods = "addNagativeQuantityCart")
    public void addNullQuantityCart() {
        productDetailsPage.setQuantityNull();
        productDetailsPage.addToCartError();
        Assert.assertEquals(headerPage.getCartCount(), "1");
        Assert.assertEquals(productDetailsPage.getQuantityError(), QUANTITY_ERROR);
    }

    @Test(dependsOnMethods = "addNullQuantityCart")
    public void addValidQuantityCart() {
        productDetailsPage.setQuantity(2);
        productDetailsPage.addToCart();
        bd.qty(2);
        Assert.assertTrue(productListPage.checkAddCartMessage(bd));
        Assert.assertEquals(headerPage.getCartCount(), "3");
    }


    @Test(dependsOnMethods = "addValidQuantityCart")
    public void updateNagativeQuantityCart() {
        shoppingCartPage = headerPage.viewCart();
        shoppingCartPage.setQuantityError(sp, -1);
        shoppingCartPage.setQuantityError(bd, -1);
        shoppingCartPage.updateShoppingCart();
        Assert.assertEquals(shoppingCartPage.getQuantityError(sp), SHOPPINGCART_QUANTITY_ERROR);
        Assert.assertEquals(shoppingCartPage.getQuantityError(bd), SHOPPINGCART_QUANTITY_ERROR);
    }

    @Test(dependsOnMethods = "updateNagativeQuantityCart")
    public void updateCart() {
        shoppingCartPage.setQuantity(sp, 3);
        shoppingCartPage.setQuantity(bd, 3);
        shoppingCartPage.updateShoppingCart();
        sp.qty(3);
        bd.qty(3);
        Assert.assertTrue(shoppingCartPage.checkProduct(sp));
        Assert.assertTrue(shoppingCartPage.checkProduct(bd));
        Assert.assertEquals(headerPage.getCartCount(), "6");
    }

    @Test(dependsOnMethods = "updateCart")
    public void deletePartialCart() {
        shoppingCartPage.delete(sp);
        sp.qty(0);
        Assert.assertFalse(shoppingCartPage.checkProduct(sp));
        Assert.assertEquals(headerPage.getCartCount(), "3");
    }

    @Test(dependsOnMethods = "deletePartialCart")
    public void deleteCart() {
        shoppingCartPage.delete(bd);
        bd.qty(0);
        Assert.assertFalse(shoppingCartPage.checkProduct(bd));
        Assert.assertEquals(shoppingCartPage.getCartEmptyMsg(), CART_EMPTY_MSG);
    }
}
