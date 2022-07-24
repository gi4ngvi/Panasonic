package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartSimpleProductTest extends SeleniumTest {
    SingleProduct sp, sp1;
    CustomerInformation cus;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    ShoppingCartPage shoppingCartPage;
    String QUANTITY_ERROR, SHOPPINGCART_QUANTITY_ERROR, CART_EMPTY_MSG;

    @Override
    protected void preCondition() {
        sp = DataTest.getSingleProductTest();
        sp1 = DataTest.getSingleProductTest1();
        cus = DataTest.getDefaultCustomerTest();
        QUANTITY_ERROR = "Vui lòng nhập một số hợp lệ trong lĩnh vực này.";
        SHOPPINGCART_QUANTITY_ERROR = "Vui lòng nhập số lớn hơn 0.";
        CART_EMPTY_MSG = "Bạn có không có sản phẩm nào trong giỏ hàng của bạn.\n" +
                "Bấm vào đây để tiếp tục mua sắm.";
    }

    @Test
    public void addCart() {
        headerPage.login(cus);
        headerPage.clearCart();
        productListPage = headerPage.search(sp.getName());
        productListPage.addToCart(sp);
        Assert.assertTrue(productListPage.checkAddCartMessage(sp));
        Assert.assertEquals(headerPage.getCartCount(), "1");
    }

    @Test(dependsOnMethods = "addCart")
    public void addNagativeQuantityCart() {
        productListPage = headerPage.search(sp1.getName());
        productDetailsPage = productListPage.viewPDP(sp1);
        productDetailsPage.setQuantity(-5);
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
        sp1.qty(2);
        Assert.assertEquals(headerPage.getCartCount(), "3");
    }

    @Test(dependsOnMethods = "addValidQuantityCart")
    public void updateNagativeQuantityCart() {
        shoppingCartPage = headerPage.viewCart();
        shoppingCartPage.setQuantityError(sp, -1);
        shoppingCartPage.setQuantityError(sp1, -1);
        shoppingCartPage.updateShoppingCart();
        Assert.assertEquals(shoppingCartPage.getQuantityError(sp), SHOPPINGCART_QUANTITY_ERROR);
        Assert.assertEquals(shoppingCartPage.getQuantityError(sp1), SHOPPINGCART_QUANTITY_ERROR);
    }

    @Test(dependsOnMethods = "updateNagativeQuantityCart")
    public void updateCart() {
        shoppingCartPage.setQuantity(sp, 3);
        shoppingCartPage.setQuantity(sp1, 3);
        shoppingCartPage.updateShoppingCart();
        sp.qty(3);
        sp1.qty(3);
        Assert.assertTrue(shoppingCartPage.checkProduct(sp));
        Assert.assertTrue(shoppingCartPage.checkProduct(sp1));
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
        shoppingCartPage.delete(sp1);
        sp1.qty(0);
        Assert.assertFalse(shoppingCartPage.checkProduct(sp1));
        Assert.assertEquals(shoppingCartPage.getCartEmptyMsg(), CART_EMPTY_MSG);
    }
}
