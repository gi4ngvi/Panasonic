package isobar.panasonic.functions.my_en;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.ConfigurationProduct;
import isobar.panasonic.entity.product.SingleProduct;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.product.ProductDetailsPage;
import isobar.panasonic.page.fo.method.product.ProductListPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_ShoppingCartTest extends SeleniumTest {
    private SingleProduct singleProduct;
    private BundleProduct bundleProduct;
    private ConfigurationProduct configurationProduct;
    private CustomerInformation cus;
    private ShoppingCartPage shoppingCartPage;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;
    private String QTY_REQUIRED_MSG, QUANTITY_ERROR_MSG, ADD_TO_CART_SUCCESS_MSG, CART_EMPTY_MSG;
    private int subTotal, grandTotal, tax;

    @Override
    protected void preCondition() {
        singleProduct = DataTest.getSingleProductTest();
        bundleProduct = DataTest.getBundleProductTest();
        configurationProduct = DataTest.getConfigurationProduct();
        cus = DataTest.getDefaultCustomerTest();
        ADD_TO_CART_SUCCESS_MSG = "You added %s to your shopping cart.";
        QUANTITY_ERROR_MSG = "Please enter a quantity greater than 0.";
        QTY_REQUIRED_MSG = "Please enter a valid number in this field.";
        CART_EMPTY_MSG = "You have no items in your shopping cart.\n" +
                "Click here to continue shopping.";
    }

    @Test
    public void prepare() {
        headerPage.login(cus);
        headerPage.clearCart();
    }

    @Test(dependsOnMethods = "prepare")
    public void addSingleProduct() {
        productListPage = headerPage.search(singleProduct.getName());
        productDetailsPage = productListPage.viewPDP(singleProduct);
        productDetailsPage.setQuantity(-5);
        productDetailsPage.addToCartError();
        Assert.assertEquals(productDetailsPage.getQuantityError(), QUANTITY_ERROR_MSG);
        productDetailsPage.setQuantityNull();
        productDetailsPage.addToCartError();
        Assert.assertEquals(productDetailsPage.getQuantityError(), QTY_REQUIRED_MSG);
        productDetailsPage.setQuantity(2);
        productDetailsPage.addToCart();
        Assert.assertEquals(headerPage.getSuccessMessage(), String.format(ADD_TO_CART_SUCCESS_MSG, singleProduct.getName()));
        singleProduct.qty(2);
        shoppingCartPage = headerPage.viewCart();
        Assert.assertTrue(shoppingCartPage.checkProduct(singleProduct));
        calculateForSingleProduct();
        Assert.assertEquals(shoppingCartPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
    }

    @Test(dependsOnMethods = "addSingleProduct")
    public void addBundleProduct() {
        productListPage = headerPage.gotoCategory(bundleProduct.getCategory());
        productDetailsPage = productListPage.viewPDP(bundleProduct);
        productDetailsPage.setQuantity(-5);
        productDetailsPage.addToCartError();
        Assert.assertEquals(productDetailsPage.getQuantityError(), QUANTITY_ERROR_MSG);
        productDetailsPage.setQuantityNull();
        productDetailsPage.addToCartError();
        Assert.assertEquals(productDetailsPage.getQuantityError(), QTY_REQUIRED_MSG);
        productDetailsPage.setQuantity(4);
        productDetailsPage.addToCart();
        Assert.assertEquals(headerPage.getSuccessMessage(), String.format(ADD_TO_CART_SUCCESS_MSG, bundleProduct.getName()));
        bundleProduct.qty(4);
        shoppingCartPage = headerPage.viewCart();
        Assert.assertTrue(shoppingCartPage.checkProduct(bundleProduct));
        calculateForBundleProduct();
        Assert.assertEquals(shoppingCartPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
    }

    @Test(dependsOnMethods = "addBundleProduct")
    public void addConfigurationProduct() {
        productListPage = headerPage.search(configurationProduct.getName());
        productDetailsPage = productListPage.viewPDP(configurationProduct);
        productDetailsPage.selectColor(configurationProduct.getColor());
        productDetailsPage.setQuantity(-5);
        productDetailsPage.addToCartError();
        Assert.assertEquals(productDetailsPage.getQuantityError(), QUANTITY_ERROR_MSG);
        productDetailsPage.setQuantityNull();
        productDetailsPage.addToCartError();
        Assert.assertEquals(productDetailsPage.getQuantityError(), QTY_REQUIRED_MSG);
        productDetailsPage.setQuantity(2);
        productDetailsPage.addToCart();
        Assert.assertEquals(headerPage.getSuccessMessage(), String.format(ADD_TO_CART_SUCCESS_MSG, configurationProduct.getName()));
        configurationProduct.qty(2);
        shoppingCartPage = headerPage.viewCart();
        Assert.assertTrue(shoppingCartPage.checkProduct(configurationProduct));
        calculateForConfigurationProduct();
        Assert.assertEquals(shoppingCartPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
    }

    @Test(dependsOnMethods = "addConfigurationProduct")
    public void updateCart() {
        shoppingCartPage.setQuantity(singleProduct, 4);
        shoppingCartPage.setQuantity(bundleProduct, 2);
        shoppingCartPage.setQuantity(configurationProduct,1);
        shoppingCartPage.updateShoppingCart();
        singleProduct.qty(4);
        bundleProduct.qty(2);
        configurationProduct.qty(1);
        Assert.assertTrue(shoppingCartPage.checkProduct(singleProduct));
        Assert.assertTrue(shoppingCartPage.checkProduct(bundleProduct));
        Assert.assertTrue(shoppingCartPage.checkProduct(configurationProduct));
        calculate();
        Assert.assertEquals(shoppingCartPage.getSubtotal(), PriceUtility.convertPriceToString(subTotal + tax));
        Assert.assertEquals(shoppingCartPage.getTotal(), PriceUtility.convertPriceToString(grandTotal));
    }

    @Test(dependsOnMethods = "updateCart")
    public void deleteProduct() {
        shoppingCartPage.delete(singleProduct);
        Assert.assertFalse(shoppingCartPage.checkProduct(singleProduct));
        shoppingCartPage.delete(bundleProduct);
        Assert.assertFalse(shoppingCartPage.checkProduct(bundleProduct));
        shoppingCartPage.delete(configurationProduct);
        Assert.assertEquals(shoppingCartPage.getCartEmptyMsg(), CART_EMPTY_MSG);
    }

    private void calculateForSingleProduct() {
        subTotal = singleProduct.getPrice() * singleProduct.getQty();
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + tax;
    }

    private void calculateForBundleProduct() {
        subTotal += bundleProduct.getPrice() * bundleProduct.getQty();
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + tax;
    }

    private void calculateForConfigurationProduct() {
        subTotal += configurationProduct.getPrice() * configurationProduct.getQty();
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + tax;
    }

    private void calculate() {
        subTotal = singleProduct.getPrice() * singleProduct.getQty() + bundleProduct.getQty() * bundleProduct.getPrice() + configurationProduct.getQty() * configurationProduct.getPrice();
        tax = DataTest.calculateTax(subTotal);
        grandTotal = subTotal + tax;
    }
}