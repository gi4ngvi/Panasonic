package isobar.panasonic.page.fo.method.checkout;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.components.Label;
import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.StorePickup;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.data.ShippingMethod;
import isobar.panasonic.entity.data.SiteCode;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.page.fo.locator.checkout.CheckoutLocator;
import isobar.panasonic.utility.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends CheckoutLocator {
    private HandleLoading handleLoading;
    private static final int TIME_OUT = 10;
    private String gui, data;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        handleLoading = new HandleLoading(driver);
    }

    public void waitForLoadingSuccess() {
        waitUtility.waitUntilVisibilityOf(weNext);
        handleLoading.handleAjaxLoading(TIME_OUT);
        handleLoading.handleLoadingMask(weOrderSummaryLoadingMask, 1);
    }

    private void expandMiniCart() {
        handleLoading.handleLoadingMask(weOrderSummaryLoadingMask, 2);
        waitUtility.waitUntilToBeClickAble(weMiniCart);
        actionUtility.scrollToElement(weMiniCart, 0, -100);
        if (weMiniCart.getAttribute("aria-expanded").equals("false")) weMiniCart.click();
    }

    public void setShippingMethod(ShippingMethod method) {
        waitUtility.waitForPageLoad();
        Label weShippingMethod = new Label(driver, String.format(SHIPING_METHOD_TITLE, method.toString()));
        waitUtility.waitUntilToBeClickAble(weShippingMethod.getWebElement());
        if (!weShippingMethod.isChecked()) {
            weShippingMethod.click();
            handleLoading.handleAjaxLoading(TIME_OUT);
            handleLoading.handleLoadingMask(weOrderSummaryLoadingMask, 1);
        }
    }

    public void setShippingMethodStorePickup(StorePickup storePickup) {
        setShippingMethod(ShippingMethod.StorePickup);
        String storeAddress = storePickup.getStreet1() + ", " + storePickup.getStreet2() + ", "
                + storePickup.getStreet3() + ", " + storePickup.getCity() + ", " + storePickup.getState()
                + " " + storePickup.getZipCode() + ", " + storePickup.getCountry();
        Label weStoreAddress = new Label(driver, String.format(STORE_PICKUP_OPTION, storeAddress));
        waitUtility.waitUntilToBeClickAble(weStoreAddress.getWebElement());
        weStoreAddress.click();
        handleLoading.handleAjaxLoading(TIME_OUT);
        handleLoading.handleLoadingMask(weOrderSummaryLoadingMask, 1);
    }

    public PaymentPage gotoPayment() {
        String url = driver.getCurrentUrl();
        weNext.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        return new PaymentPage(driver);
    }

    public boolean checkProduct(Product product) {
        ReportUtility.getInstance().log(LogStatus.WARNING, "Temporary disabled expandMiniCart because it's not clickable.");
//        expandMiniCart();
        if (!checkProductQty(product)) return false;
        if(DataTest.getCountry() == Country.VN || DataTest.getCountry() == Country.MY) {
            // Site VN display only price inc tax
            if (!checkPriceIncTax(product))
                return false;
        } else if (!checkPriceExcTax(product)) return false;
        return true;
    }

    public boolean checkPriceIncTax(Product product) {
        Label wePrice = new Label(driver, String.format(PRODUCT_PRICE_INC_TAX, product.getName()));
        if (!wePrice.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field price inc tax of %s has been hidden", product.getName()));
            return false;
        }
        gui = wePrice.getText();
        int price = product.getPrice() * product.getQty();
        data = PriceUtility.convertPriceToString(price + DataTest.calculateTax(price));
        ReportUtility.getInstance().logInfo(String.format("Checking price inc tax of product: %s - actual: %s - expected: %s", product.getName(), gui, data));
        return gui.equals(data);
    }

    private boolean checkPriceExcTax(Product product) {
        Label wePrice = new Label(driver, String.format(PRODUCT_PRICE_EXC_TAX, product.getName()));
        if (!wePrice.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field price exc tax of %s has been hidden", product.getName()));
            return false;
        }
        gui = wePrice.getText();
        data = PriceUtility.convertPriceToString(product.getPrice() * product.getQty());
        ReportUtility.getInstance().logInfo(String.format("Checking price exc tax of product: %s - actual: %s - expected: %s", product.getName(), gui, data));
        return gui.equals(data);
    }

    private boolean checkProductQty(Product product) {
        Label weQty = new Label(driver, String.format(PRODUCT_QTY, product.getName()));
        if (!weQty.isDisplay()) {
            ReportUtility.getInstance().logFail(String.format("field qty of %s has been hidden", product.getName()));
            return false;
        }
        int qty = Integer.parseInt(weQty.getText());
        ReportUtility.getInstance().logInfo(String.format("Checking qty of product %s - actual: %s - expected: %d", product.getName(), qty, product.getQty()));
        return qty == product.getQty();
    }

    public void newAddress(Address address) {
        waitForLoadingSuccess();
        Panasonic.checkStoreViewOnShipping(driver);
        clickNewAddress();
        waitUtility.waitUntilToBeClickAble(weSaveAddress);
        switch (DataTest.getCountry()) {
            case VN:
                fillInShippingAddressVN(address);
                break;
            case MY:
                fillInShippingAddressMY(address);
                break;
            case TH:
                fillInShippingAddressTH(address);
                break;
            case ID:
                fillInShippingAddressID(address);
                break;
            default:
                break;
        }
        setSaveInAddressBook(false);
        saveAddress();
        handleLoading.handleAjaxLoading(TIME_OUT);
        handleLoading.handleLoadingMask(weOrderSummaryLoadingMask);
    }

    private void fillInShippingAddressVN(Address address) {
        enterName(address.getName());
        enterCompany(address.getCompany());
        selectCityVN(address.getCity());
        selectDistrict(address.getDistrict());
        selectWard(address.getWard());
        enterStreet(address.getStreet());
        enterTelephone(address.getTelephone());
    }

    private void fillInShippingAddressMY(Address address) {
        enterAddressName(address.getAddressName());
        enterFirstName(address.getFirstName());
        enterLastName(address.getLastName());
        enterCompany(address.getCompany());
        selectState(address.getState());
        enterMobileNumber(address.getMobileNumber());
        enterCityMY(address.getCity());
        enterZip(address.getZip());
        enterStreet(address.getStreet());
        enterTelephone(address.getTelephone());
    }

    private void fillInShippingAddressID(Address address) {
        enterName(address.getName());
        enterCompany(address.getCompany());
        selectState(address.getState());
        selectCity(address.getCity());
        enterZip(address.getZip());
        enterStreet(address.getStreet());
        enterTelephone(address.getTelephone());
    }

    private void fillInShippingAddressTH(Address address) {
        enterFirstName(address.getFirstName());
        enterLastName(address.getLastName());
        enterStreet(address.getStreet());
        enterZip(address.getZip());
        selectState(address.getState());
        enterTelephone(address.getTelephone());
    }

    private void selectState(String state) {
        new Select(weState).selectByVisibleText(state);
    }

    private void enterName(String key) {
        weName.clear();
        weName.sendKeys(key);
    }

    private void enterFirstName(String firstName) {
        waitUtility.waitUntilToBeClickAble(weFirstName);
        weFirstName.clear();
        weFirstName.sendKeys(firstName);
    }

    private void enterLastName(String lastName) {
        waitUtility.waitUntilToBeClickAble(weLastName);
        weLastName.clear();
        weLastName.sendKeys(lastName);
    }

    private void enterZip(String zip) {
        weZip.clear();
        weZip.sendKeys(zip);
    }

    private void enterCityMY(String city) {
        weCityMY.clear();
        weCityMY.sendKeys(city);
    }

    private void enterCompany(String key) {
        weCompany.clear();
        weCompany.sendKeys(key);
    }

    private void selectCityVN(String key) {
        new Select(weCityVN).selectByVisibleText(key);
    }

    private void selectDistrict(String key) {
        if (!key.equals("")) {
            new Select(weDistrict).selectByVisibleText(key);
        }
    }

    private void selectWard(String key) {
        if (!key.equals("")) {
            new Select(weWard).selectByVisibleText(key);
        }
    }

    private void enterStreet(String key) {
        weStreet.clear();
        weStreet.sendKeys(key);
    }

    private void enterTelephone(String key) {
        weTelephone.clear();
        weTelephone.sendKeys(key);
    }

    private void enterAddressName(String key) {
        waitUtility.waitUntilToBeClickAble(weAddressName);
        weAddressName.clear();
        weAddressName.sendKeys(key);
    }

    private void enterMobileNumber(String key) {
        weMobileNumber.clear();
        weMobileNumber.sendKeys(key);
    }

    private void setSaveInAddressBook(boolean status) {
        if (weTickSaveAddress.isSelected() != status) {
            weTickSaveAddress.click();
        }
    }

    private void selectCity(String city) {
        if (!city.equals("")) {
            new Select(weCity).selectByVisibleText(city);
        }
    }

    public void saveAddress() {
        waitUtility.waitUntilToBeClickAble(weSaveAddress);
        weSaveAddress.click();
    }

    public void clickNewAddress() {
        try {
            waitUtility.waitUntilToBeClickAble(weNewAddress);
            weNewAddress.click();
        } catch (TimeoutException e) {
            ReportUtility.getInstance().logError("Sometimes when clicking on checkout button at cart page, it redirects to login page - QC still investigates root cause - noted to QC manual");
            DataTest.extractJSLogs(driver);
        }
    }

    public void selectAddress(Address address) {
        String addressFormat;
        waitForLoadingSuccess();
        Panasonic.checkStoreViewOnShipping(driver);
        Label weAddressSelected = new Label(driver, ADDRESS_SELECTED);
        /*
         * work around for format address wrong in TH TH
         */
        if (DataTest.getSiteCode() == SiteCode.TH_TH) {
            addressFormat = formatAddressTHTH(address);
        } else {
            addressFormat = formatAddress(address);
        }
        String addressSelected = formatSelectedAddress(weAddressSelected.getText());
        if (addressSelected.equals(addressFormat))
            return;
        else {
            new Label(driver, String.format(ADDRESS_BOX, address.getStreet())).click();
            handleLoading.handleAjaxLoading();
        }
    }

    public boolean checkAddress(Address address) {
        gui = formatSelectedAddress(new Label(driver, ADDRESS_SELECTED).getText());
        data = formatAddress(address);
        ReportUtility.getInstance().logInfo("Check address. GUI: " + gui + " | Data: " + data);
        return gui.equals(data);
    }

    private String formatSelectedAddress(String address) {
        SiteCode site = DataTest.getSiteCode();
        //Workaround for address format is not the same in BO and FO
        address = address.replace("\n0123456789", "");
        switch (site) {
            case ID_BA:
                return address.replace("\n\nUbah", "");
            case TH_TH:
                return address.replace("\n\nแก้ไข", "");
            case VN_VN:
                return address.replace("\n\nChỉnh sửa", "");
            default:
                return address.replace("\n\nEdit", "");
        }
    }

    public int getShippingFee(String shippingMethod) {
        ReportUtility.getInstance().log(LogStatus.WARNING, "work around get shipping fee in UI, because shipping fee changes constantly.");
        String feeIncTaxStr;
        feeIncTaxStr = new Label(driver, String.format(SHIPPING_FEE_INC_TAX, shippingMethod)).getText();
        if (PriceUtility.convertPriceStringToNumber(feeIncTaxStr) == 0)
            return 0;
        return PriceUtility.convertPriceStringToNumber(new Label(driver, String.format(SHIPPING_FEE_EXCL_TAX, shippingMethod)).getText());
    }

    public void cancelAddress() {
        waitUtility.waitUntilToBeClickAble(weCancelAddress);
        weCancelAddress.click();
        waitUtility.waitForValueOfAttribute(weFormShippingAddress, "style", "display: none;");
    }

    public String getNameAddressError() {
        waitUtility.waitUntilVisibilityOf(weAddressNameError);
        return weAddressNameError.getText().trim();
    }

    public String getFirstNameError() {
        waitUtility.waitUntilVisibilityOf(weFirstNameError);
        return weFirstNameError.getText().trim();
    }

    public String getLastNameError() {
        waitUtility.waitUntilVisibilityOf(weLastNameError);
        return weLastNameError.getText().trim();
    }

    public String getStateError() {
        waitUtility.waitUntilVisibilityOf(weStateError);
        return weStateError.getText().trim();
    }

    public String getMobileNumberError() {
        waitUtility.waitUntilVisibilityOf(weMobileNumberError);
        return weMobileNumberError.getText().trim();
    }

    public String getCityError() {
        waitUtility.waitUntilVisibilityOf(weCityError);
        return weCityError.getText().trim();
    }

    public String getZipError() {
        waitUtility.waitUntilVisibilityOf(weZipError);
        return weZipError.getText().trim();
    }

    public String getStreetError() {
        waitUtility.waitUntilVisibilityOf(weStreetError);
        return weStreetError.getText().trim();
    }

    public String getTelephoneError() {
        waitUtility.waitUntilVisibilityOf(weTelephoneError);
        return weTelephoneError.getText().trim();
    }

    public String getFullNameError() {
        waitUtility.waitUntilVisibilityOf(weFullNameError);
        return weFullNameError.getText().trim();
    }

    public String getDistrictError() {
        waitUtility.waitUntilVisibilityOf(weDistrictError);
        return weDistrictError.getText().trim();
    }

    public String getWardError() {
        waitUtility.waitUntilVisibilityOf(weWardError);
        return weWardError.getText().trim();
    }

    /*
     * work around for format address wrong in TH TH
     */
    private String formatAddressTHTH(Address address) {
        String stateTH = DataTest.translateStateTHToEN(address.getState());
        return String.format("%s %s\n" +
                "%s\n" +
                "%s %s\n" +
                "%s\n" +
                "%s", address.getFirstName(), address.getLastName(), address.getStreet(), stateTH, address.getZip(), address.getCountry(), address.getTelephone());
    }

    private String formatAddress(Address address) {
        switch (DataTest.getCountry()) {
            case ID:
                return String.format("%s\n" +
                        "%s\n" +
                        "%s, %s %s\n" +
                        "%s\n" +
                        "%s", address.getName(), address.getStreet(), address.getCity(), address.getState(), address.getZip(), address.getCountry(), address.getTelephone());
            case TH:
                return String.format("%s %s\n" +
                        "%s\n" +
                        "%s %s\n" +
                        "%s\n" +
                        "%s", address.getFirstName(), address.getLastName(), address.getStreet(), address.getState(), address.getZip(), address.getCountry(), address.getTelephone());
            case VN:
                return String.format("%s\n" +
                        "%s\n" +
                        "%s, %s, %s\n" +
                        "%s\n" +
                        "%s", address.getName(), address.getStreet(), address.getWard(), address.getDistrict(), address.getCity(), address.getCountry(), address.getTelephone());
            case MY:
                return String.format("%s %s\n" +
                                "%s\n" +
                                "%s\n" +
                                "%s, %s %s\n" +
                                "%s\n" +
                                "%s\n" +
                                "%s\n" +
                                "%s", address.getFirstName(), address.getLastName(), address.getCompany(), address.getStreet(), address.getCity(),
                        address.getState(), address.getZip(), address.getCountry(), address.getTelephone(), address.getAddressName(), address.getMobileNumber());
            default:
                return null;
        }
    }
}
