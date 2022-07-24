package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.components.Label;
import isobar.panasonic.page.fo.locator.account.MyOrderLocator;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;

public class MyOrderPage extends MyOrderLocator {
    Label label;

    public MyOrderPage(WebDriver driver) {
        super(driver);
        label = new Label(driver);
    }

    public String getSubtotal() {
        return weSubtotal.getText();
    }

    public String getTotal() {
        return weGrandtotal.getText();
    }

    public String getShippingFee() {
        return weShippingfee.getText();
    }

    public String getTax() {
        return weTax.getText();
    }

    public String getShippingAddress() {
        return weShippingaddress.getText();
    }

    public String getBillingAddress() {
        return weBillingAddress.getText();
    }

    public String getShippingMethod() {
        return weShippingmethod.getText();
    }

    public String getPaymentMethod() {
        return wePaymentmethod.getText();
    }

    public String getOrderStatus() {
        return weOrderstatus.getText().toLowerCase();
    }

    public String getOrderDate() {
        return weOrderDate.getText().replace("Order Date: ", "");
    }

    public String getOrderId(int index) {
        Label lbOrderId = new Label(driver, String.format(ORDER_ID, index));
        return lbOrderId.getText();
    }

    public String getOrderStatus(int index) {
        Label lbOrderStatus = new Label(driver, String.format(ORDER_STATUS, index));
        return lbOrderStatus.getText().trim().toLowerCase();
    }

    public OrderDetailsPage viewOrder(String orderNumber) {
        label.setLocator(String.format(ORDER_DETAILS, orderNumber));
        waitUtility.waitUntilToBeClickAble(label.getWebElement());
        label.click();
        return new OrderDetailsPage(driver);
    }

    public String getOrderTotal(int index){
        label.setLocator(String.format(ORDER_TOTAL, index));
        return label.getText().trim();
    }

    public OrderDetailsPage viewOrder(int index){
        label.setLocator(String.format(VIEW_ORDER, index));
        label.click();
        waitUtility.waitForPageLoad();
        return new OrderDetailsPage(driver);
    }

    public boolean hasOrderInPage() {
        waitUtility.waitForPageLoad();
        return weListOrder.size() > 0;
    }

    public ShoppingCartPage reOrder(String orderID) {
        label.setLocator(String.format(REORDER, orderID));
        label.click();
        waitUtility.waitForPageLoad();
        return new ShoppingCartPage(driver);
    }
}
