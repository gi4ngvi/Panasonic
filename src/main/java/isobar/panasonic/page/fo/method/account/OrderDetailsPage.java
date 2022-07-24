package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.components.Label;
import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.StorePickup;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.product.BundleProduct;
import isobar.panasonic.entity.product.ConfigurationProduct;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.entity.product.ProductType;
import isobar.panasonic.page.fo.locator.account.OrderDetailLocator;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class OrderDetailsPage extends OrderDetailLocator {
    Label label;

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
        label = new Label(driver);
    }

    public void selectOrderByID(String orderID) {
        int id = getOrderIndex(orderID);
        weListOrder.get(id).click();
    }

    private int getOrderIndex(String orderID) {
        for (WebElement ele : weListOrderID) {
            if (ele.getText().indexOf(orderID) > 0) {
                return weListOrderID.indexOf(ele);
            }
        }
        return -1;
    }

    public boolean checkOrderID(String orderID) {
        waitUtility.waitUntilVisibilityOf(weOrderID);
        if (weOrderID.getText().indexOf(orderID) != -1) return true;
        return false;
    }

    public boolean checkProduct(Product product) {
        boolean status = checkProductSKU(product);
        if (DataTest.getCountry() == Country.VN || DataTest.getCountry() == Country.MY) {
            ReportUtility.getInstance().logInfo("check for VN");
            status &= checkProductPriceWithVAT(product);
            status &= checkProductSubtotalWithVAT(product);
        } else {
            status &= checkProductPrice(product);
            status &= checkProductSubtotal(product);
        }
        return status;
    }

    private boolean checkProductSKU(Product product) {
        String gui, data;
        label.setLocator(String.format(PRODUCT_SKU, product.getName()));
        if (!label.isDisplay()) return false;
        gui = label.getText();
        // Bundle product sku won't show items's sku for temporary - Confirmed with chi Trinh
        /*
        if (product.getType().equals(ProductType.BUNDLE)) {
            BundleProduct bp = (BundleProduct) product;
            //apply work around by replace space character
            data = product.getSku();
            for (Product child : bp.getListSingleProducts()) {
                data += "-" + child.getSku();
            }
            gui = gui.replaceAll(" ", "");
            data = data.replaceAll(" ", "");
        } else
         */
        if (product.getType().equals(ProductType.CONFIGURATION)) {
            ConfigurationProduct cp = (ConfigurationProduct) product;
            data = cp.getSubSKU();
        } else {
            data = product.getSku();
        }
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking sku of product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductPrice(Product product) {
        label.setLocator(String.format(PRODUCT_PRICE, product.getName()));
        if (!label.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field price of %s has been hidden", product.getName()));
            return false;
        }
        String gui = label.getText();
        String data = PriceUtility.convertPriceToString(product.getPrice());
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking price of product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductQty(Product product) {
        if (product.getType().equals(ProductType.BUNDLE)) return true;
        label.setLocator(String.format(PRODUCT_QTY, product.getName()));
        if (!label.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field qty of %s has been hidden", product.getName()));
            return false;
        }
        String gui = label.getText();
        String data = String.valueOf(product.getQty());
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking qty of product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductSubtotal(Product product) {
        String gui, data;
        label.setLocator(String.format(PRODUCT_SUBTOTAL, product.getName()));
        if (!label.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field subtotal of %s has been hidden", product.getName()));
            return false;
        }
        gui = label.getText();
        data = PriceUtility.convertPriceToString(product.getPrice() * product.getQty());
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking subtotal of product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductSubtotalWithVAT(Product product) {
        String gui, data;
        label.setLocator(String.format(PRODUCT_SUBTOTAL_WITH_VAT, product.getName()));
        if (!label.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field SubtotalWithVAT of %s has been hidden", product.getName()));
            return false;
        }
        gui = label.getText();
        int subtotal = product.getPrice() * product.getQty();
        data = PriceUtility.convertPriceToString(subtotal + subtotal * DataTest.getTaxRate() / 100);
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking SubtotalWithVAT of product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    private boolean checkProductPriceWithVAT(Product product) {
        String gui, data;
        label.setLocator(String.format(PRODUCT_PRICE_WITH_VAT, product.getName()));
        if (!label.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field ProductPriceWithVAT of %s has been hidden", product.getName()));
            return false;
        }
        gui = label.getText();
        data = PriceUtility.convertPriceToString(product.getPrice() + product.getPrice() * DataTest.getTaxRate() / 100);
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking ProductPriceWithVAT of product %s GUI: %s, DATA: %s", product.getName(), gui, data));
            return false;
        }
        return true;
    }

    public String getSubtotal() {
        return weSubtotal.getText();
    }

    public boolean checkSubtotal(int price) {
        String gui, data;
        gui = getSubtotal();
        data = PriceUtility.convertPriceToString(price + DataTest.calculateTax(price));
        if (!gui.equals(data)) {
            ReportUtility.getInstance().logFail(String.format("checking subtotal of product %s GUI: %s, DATA: %s", gui, data));
            return false;
        }
        return true;
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

    public boolean checkPaymentMethod(String key) {
        if (wePaymentmethod.getText().indexOf(key) != -1)
            return true;
        return false;
    }

    public boolean checkPurchaseOrderID(String key) {
        if (wePaymentmethod.getText().indexOf(key) != -1)
            return true;
        return false;
    }

    public String getOrderStatus() {
        waitUtility.waitUntilVisibilityOf(weOrderstatus);
        return weOrderstatus.getText().toLowerCase();
    }

    public String getOrderDate() {
        return weOrderDate.getText().replace("Order Date: ", "");
    }

    //work around check shipping fee difference format currency
    public boolean checkShippingFee(int shippingFee) {
        String gui = getShippingFee();
        if (PriceUtility.convertPriceStringToNumber(gui) != shippingFee) {
            ReportUtility.getInstance().logFail(String.format("checking shipping fee GUI: %s, DATA: %s", PriceUtility.convertPriceStringToNumber(gui), shippingFee));
            return false;
        }
        return true;
    }

    private String formatAddress(Address address) {
        switch (DataTest.getCountry()) {
            case ID:
                return address.getName() + "\n" +
                        address.getCompany() + "\n" +
                        address.getStreet() + ",\n" +
                        address.getCity() + ", " + address.getState() + " " + address.getZip() + "\n" +
                        address.getCountry() + "\n" +
                        "T: " + address.getTelephone();
            case TH:
                return address.getFirstName() + " " + address.getLastName() + "\n" +
                        address.getStreet() + "," + "\n" +
                        address.getState() + " " + address.getZip() + "\n" +
                        address.getCountry() + "\n" +
                        "T: " + address.getTelephone();
            case VN:
                return address.getName() + "\n" +
                        address.getCompany() + "\n" +
                        address.getStreet() + ", " + address.getWard() + ", " + address.getDistrict() + ", " + address.getCity() + "\n" +
                        address.getCountry() + "\n" +
                        "T: " + address.getTelephone();
            case MY:
                return address.getFirstName() + " " + address.getLastName() + "\n" +
                        address.getAddressName() + "\n" +
                        address.getCompany() + "\n" +
                        address.getStreet() + ",\n" +
                        address.getCity() + ", " + address.getState() + " " + address.getZip() + "\n" +
                        address.getCountry() + "\n" +
                        "T: " + address.getTelephone() + "\n" +
                        "M: " + address.getMobileNumber();
            default:
                return null;
        }
    }

    private String formatStorePickupAddress(Address address, StorePickup storePickup) {
        StringBuilder result = new StringBuilder();
        result.append(address.getFirstName()).append(" ").append(address.getLastName()).append("\n");
        result.append(storePickup.getStoreName()).append("\n");
        result.append(storePickup.getStreet1());
        result.append(", ").append(storePickup.getStreet2());
        result.append(", ").append(storePickup.getStreet3());
        result.append("\n");
        result.append(storePickup.getCity()).append(", ").append(storePickup.getState()).append(" ").append(storePickup.getZipCode()).append("\n");
        result.append(storePickup.getCountry()).append("\n");
        result.append("T: ").append(address.getTelephone()).append("\n");
        result.append("M: ").append(address.getMobileNumber());
        return result.toString();
    }

    public boolean checkShippingAddress(Address address) {
        String gui, data;
        gui = getShippingAddress();
        data = formatAddress(address);
        ReportUtility.getInstance().logInfo(String.format("check shipping address- gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    public boolean checkShippingAddressStorePickup(Address address, StorePickup storePickup) {
        String gui, data;
        gui = getShippingAddress();
        data = formatStorePickupAddress(address, storePickup);
        ReportUtility.getInstance().logInfo(String.format("check shipping address- gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    public boolean checkBillingAddress(Address address) {
        if (DataTest.getCountry() != Country.TH) {
            String gui, data;
            gui = getBillingAddress();
            data = formatAddress(address);
            ReportUtility.getInstance().logInfo(String.format("check billing address- gui: %s - data: %s", gui, data));
            return gui.equals(data);
        }
        return true;
    }

    public String getCouponTitle() {
        waitUtility.waitUntilVisibilityOf(weCouponTitle);
        return weCouponTitle.getText().trim();
    }

    public String getCouponAmount() {
        waitUtility.waitUntilVisibilityOf(weCouponAmount);
        return weCouponAmount.getText().trim();
    }

    public ShoppingCartPage reOrder() {
        waitUtility.waitUntilToBeClickAble(weReOrder);
        weReOrder.click();
        waitUtility.waitForPageLoad();
        return new ShoppingCartPage(driver);
    }

}
