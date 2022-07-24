package isobar.panasonic.functions.id_ba;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ID_BA_ShoppingCartTest extends SeleniumTest {
    SingleProduct singleProduct;
    BundleProduct bundleProduct;
    CustomerInformation cus;
    ShoppingCartPage shoppingCartPage;
    ProductListPage productListPage;
    ProductDetailsPage productDetailsPage;
    ShippingMethod shippingMethod;
    String QTY_REQUIRED_MSG, QUANTITY_ERROR_MSG, ADD_TO_CART_SUCCESS_MSG, CART_EMPTY_MSG;
    int subTotal, grandTotal;

    @Override
    protected void preCondition() {
        singleProduct = DataTest.getSingleProductTest();
        bundleProduct = DataTest.getBundleProductTest();
        cus = DataTest.getDefaultCustomerTest();
        shippingMethod = ShippingMethod.SelfCollection;
        ADD_TO_CART_SUCCESS_MSG = "Anda menambahkan %s ke keranjang belanja Anda.";
        QUANTITY_ERROR_MSG = "Please enter a quantity greater than 0.";
        QTY_REQUIRED_MSG = "Ini adalah kolom yang harus diisi.";
        CART_EMPTY_MSG = "Anda tidak memiliki barang di keranjang belanja Anda.\n" +
                "Click here to continue shopping.";
    }

    @Test
    public void prepare() {
        headerPage.login(cus);
        headerPage.clearCart();
    }

    @Test(dependsOnMethods = "prepare")
    public void addSingleProduct() {
        productListPage = headerPage.gotoCategory(singleProduct.getCategory());
        productListPage.addToCart(singleProduct);
        Assert.assertEquals(headerPage.getSuccessMessage(), String.format(ADD_TO_CART_SUCCESS_MSG, singleProduct.getName()));
        productDetailsPage = productListPage.viewPDP(singleProduct);
        productDetailsPage.setQuantity(-5);
        productDetailsPage.addToCartError();
        Assert.assertEquals(headerPage.getCartCount(), "1");
        productDetailsPage.setQuantityNull();
        productDetailsPage.addToCartError();
        Assert.assertEquals(headerPage.getCartCount(), "1");
        Panasonic.assertEquals(productDetailsPage.getQuantityError(), QTY_REQUIRED_MSG);
        productDetailsPage.setQuantity(2);
        productDetailsPage.addToCart();
        Assert.assertEquals(headerPage.getSuccessMessage(), String.format(ADD_TO_CART_SUCCESS_MSG, singleProduct.getName()));
        Assert.assertEquals(headerPage.getCartCount(), "3");
        singleProduct.qty(3);
        headerPage.showMiniCart();
        Assert.assertTrue(headerPage.checkProduct(singleProduct));
        shoppingCartPage = headerPage.gotoShoppingCartPage();
        Assert.assertTrue( shoppingCartPage.checkProduct(singleProduct));
        calculateForSingleProduct();
        Assert.assertEquals(shoppingCartPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
    }

    @Test(dependsOnMethods = "addSingleProduct")
    public void addBundleProduct() {
        productListPage = headerPage.gotoCategory(bundleProduct.getCategory());
        productDetailsPage = productListPage.viewPDP(bundleProduct);
        productDetailsPage.setQuantity(-5);
        productDetailsPage.addToCartError();
        Assert.assertEquals(headerPage.getCartCount(), "3");
        productDetailsPage.setQuantityNull();
        productDetailsPage.addToCartError();
        Assert.assertEquals(headerPage.getCartCount(), "3");
        Panasonic.assertEquals(productDetailsPage.getQuantityError(), QTY_REQUIRED_MSG);
        productDetailsPage.setQuantity(4);
        productDetailsPage.addToCart();
        Assert.assertEquals(headerPage.getSuccessMessage(), String.format(ADD_TO_CART_SUCCESS_MSG, bundleProduct.getName()));
        Assert.assertEquals(headerPage.getCartCount(), "7");
        bundleProduct.qty(4);
        headerPage.showMiniCart();
        Assert.assertTrue(headerPage.checkProduct(bundleProduct));
        shoppingCartPage = headerPage.gotoShoppingCartPage();
        Assert.assertTrue(shoppingCartPage.checkProduct(bundleProduct));
        calculateForBundleProduct();
        Assert.assertEquals(shoppingCartPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
    }

    @Test(dependsOnMethods = "addBundleProduct")
    public void updateCart() {
        shoppingCartPage.setQuantity(singleProduct, 4);
        shoppingCartPage.setQuantity(bundleProduct, 2);
        shoppingCartPage.updateShoppingCart();
        singleProduct.qty(4);
        bundleProduct.qty(2);
        Assert.assertTrue(shoppingCartPage.checkProduct(singleProduct));
        Assert.assertTrue(shoppingCartPage.checkProduct(bundleProduct));
        shoppingCartPage.selectShippingMethod(shippingMethod);
        calculate();
        Assert.assertEquals(shoppingCartPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
    }

    @Test(dependsOnMethods = "updateCart")
    public void deleteProduct(){
        shoppingCartPage.delete(singleProduct);
        Assert.assertFalse(shoppingCartPage.checkProduct(singleProduct));
        shoppingCartPage.delete(bundleProduct);
        Assert.assertEquals(shoppingCartPage.getCartEmptyMsg(),CART_EMPTY_MSG);
    }

    private void calculateForSingleProduct(){
        subTotal = singleProduct.getPrice() * singleProduct.getQty();
        grandTotal = subTotal;
    }

    private void calculateForBundleProduct(){
        subTotal += bundleProduct.getPrice() * bundleProduct.getQty();
        grandTotal = subTotal;
    }

    private void calculate() {
        subTotal = singleProduct.getPrice() * singleProduct.getQty() + bundleProduct.getQty() * bundleProduct.getPrice();
        grandTotal = subTotal + shippingMethod.getFee();
    }
}
