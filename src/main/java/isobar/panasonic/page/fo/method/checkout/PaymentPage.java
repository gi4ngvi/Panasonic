package isobar.panasonic.page.fo.method.checkout;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.components.Label;
import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.customer.StorePickup;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.data.CreditCard;
import isobar.panasonic.entity.data.PaymentMethod;
import isobar.panasonic.entity.product.Product;
import isobar.panasonic.page.fo.locator.checkout.PaymentLocator;
import isobar.panasonic.utility.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PaymentPage extends PaymentLocator {
    private HandleLoading handleLoading;
    private static final int TIME_OUT = 10;
    private String gui, data;

    public PaymentPage(WebDriver driver) {
        super(driver);
        handleLoading = new HandleLoading(driver);
    }

    public void waitForLoadingSuccess() {
        handleLoading.handleAjaxLoading(TIME_OUT);
        handleLoading.handleLoadingMask(3);
        handleLoading.handleLoadingMask(weOrderSummaryLoadingMask, 2);
    }

    public void setPaymentCOD() {
        waitUtility.waitUntilToBeClickAble(weCOD);
        if (!weCOD.isSelected()) {
            weCOD.click();
            handleLoading.handleAjaxLoading(TIME_OUT);
        }
    }

    public void setPaymentCyberSource() {
        waitUtility.waitUntilToBeClickAble(weCybersource);
        if (!weCybersource.isSelected()) {
            weCybersource.click();
            handleLoading.handleAjaxLoading(TIME_OUT);
        }
    }

    public void setPaymentCMO() {
        waitUtility.waitUntilToBeClickAble(weCMO);
        if (!weCMO.isSelected()) {
            weCMO.click();
            handleLoading.handleAjaxLoading(TIME_OUT);
        }
    }

    public void setPaymentOnePAYDomestic() {
        waitUtility.waitUntilToBeClickAble(weOnePAYDomestic);
        if (!weOnePAYDomestic.isSelected()) {
            weOnePAYDomestic.click();
            handleLoading.handleLoadingMask(weOrderSummaryLoadingMask);
        }
    }

    public void setPaymentOnePAYInternational() {
        waitUtility.waitUntilToBeClickAble(weOnePAYInternational);
        if (!weOnePAYInternational.isSelected()) {
            weOnePAYInternational.click();
            handleLoading.handleLoadingMask(weOrderSummaryLoadingMask);
        }
    }

    public void agreeTermsAndConditions() {
        waitUtility.waitUntilToBeClickAble(weTermAgreement);
        if (!weTermAgreement.isSelected()) {
            weTermAgreement.click();
        }
    }

    public SuccessPage placeOrder() {
        placeOrder1();
        // New GUI does not have header icon to check language store view.
//        Panasonic.checkStoreView(driver);
        return new SuccessPage(driver);
    }

    public OnePayPaymentPage placeOrderOnePayDomestic() {
        placeOrder1();
        return new OnePayPaymentPage(driver);
    }

    public VisaPage placeOrderOnePayInternational() {
        placeOrder1();
        return new VisaPage(driver);
    }

    public void placeOrderError() {
        placeOrder1();
    }

    public void placeOrder1() {
        String url = driver.getCurrentUrl();
        waitUtility.waitUntilToBeClickAble(wePlaceOrder);
        wePlaceOrder.click();
        waitUtility.waitForURLChange(url, 60);
        waitUtility.waitForPageLoad();
    }

    public boolean checkProduct(Product product) {
        waitUtility.waitForPageLoad();
        ReportUtility.getInstance().log(LogStatus.WARNING, "Temporary disabled expandMiniCart because it's not clickable.");
//        expandMiniCart();
        waitUtility.waitForValueOfAttributeDoesNotContains(weBlockOrderSummary, "class", "block-content-loading");
        boolean status = checkProductQty(product);
        if(DataTest.getCountry() == Country.VN || DataTest.getCountry() == Country.MY) {
            status &= checkPriceIncTax(product);
        } else {
            status &= checkPriceExcTax(product);
        }
        return status;
    }

    public boolean checkPriceIncTax(Product product) {
        try {
            WebElement we = new Label(driver, String.format(PRODUCT_PRICE_INC_TAX, product.getName())).getWebElement();
            if (!we.isDisplayed()) return false;
            return we.getText().equals(PriceUtility.convertPriceToString(product.getPrice() * product.getQty() + DataTest.calculateTax(product.getPrice() * product.getQty())));
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    private boolean checkPriceExcTax(Product product) {
        Label wePrice = new Label(driver, String.format(PRODUCT_PRICE_EXC_TAX, product.getName()));
        waitUtility.refreshAndWaitUntilVisibilityOf(wePrice.getWebElement());
        if (!wePrice.isDisplay()) return false;
        gui = wePrice.getText().trim();
        data = PriceUtility.convertPriceToString(product.getPrice() * product.getQty());
        ReportUtility.getInstance().logInfo(String.format("check price: %s - actual: %s - expected: %s", product.getName(), gui, data));
        return gui.equals(data);
    }

    private boolean checkProductQty(Product product) {
        int qty;
        Label lbProductQty = new Label(driver, String.format(PRODUCT_QTY, product.getName()));
        if (!lbProductQty.isDisplay()) return false;
        try {
            qty = Integer.parseInt(lbProductQty.getText());
        } catch (StaleElementReferenceException ex) {
            actionUtility.sleep(500);
            qty = Integer.parseInt(lbProductQty.getText());
        }
        ReportUtility.getInstance().logInfo(String.format("Checking product: %s - actual: %s - expected: %d", product.getName(), qty, product.getQty()));
        return qty == product.getQty();
    }

    private void expandMiniCart() {
        waitUtility.waitUntilToBeClickAble(weMiniCart);
        if (weMiniCart.getAttribute("aria-expanded").equals("false")) weMiniCart.click();
        waitUtility.waitForValueOfAttribute(weMiniCart, "aria-expanded", "true");
    }

    public String getSubtotal() {
        waitUtility.waitUntilVisibilityOf(weSubtotal);
        return weSubtotal.getText();
    }

    public boolean checkSubtotal(int price) {
        gui = getSubtotal();
        if(DataTest.getCountry() == Country.VN || DataTest.getCountry() == Country.MY) {
            data = PriceUtility.convertPriceToString(price + DataTest.calculateTax(price));
        } else {
            data = PriceUtility.convertPriceToString(price);
        }
        ReportUtility.getInstance().logInfo(String.format("check subtotal: gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    public String getTax() {
        return weTax.getText();
    }

    public String getTotal() {
        return weGrandtotal.getText();
    }

    public boolean checkTotal(int price) {
        return getTotal().equals(PriceUtility.convertPriceToString(price + DataTest.calculateTax(price)));
    }

    public String getShippingFeeWithoutTax() {
        return weShippingFeeWithoutTax.getText();
    }

    public String getShippingFee() {
        return weShippingFee.getText();
    }

    public boolean checkShippingAddress(Address address) {
        gui = weShipAddress.getText().trim();
        data = formatAddress(address);
        ReportUtility.getInstance().logInfo(String.format("check shipping address info: gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    public boolean checkShippingAddressStorePickup(StorePickup storePickup, CustomerInformation cus) {
        gui = weShipAddress.getText().trim();
        data = formatStorePickupAddress(storePickup, cus);
        ReportUtility.getInstance().logInfo(String.format("check shipping address info: gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    private String formatStorePickupAddress(StorePickup storePickup, CustomerInformation cus) {
        StringBuilder result = new StringBuilder();
        result.append(cus.getFirstName()).append(" ").append(cus.getLastName()).append("\n");
        result.append(storePickup.getStoreName()).append("\n");
        result.append(storePickup.getStreet1());
        result.append(", ").append(storePickup.getStreet2());
        result.append(", ").append(storePickup.getStreet3()).append("\n");
        result.append(storePickup.getCity()).append(", ").append(storePickup.getState()).append(" ").append(storePickup.getZipCode()).append("\n");
        result.append(storePickup.getCountry());
        return result.toString();
    }

    public boolean checkBillingAddress(Address address) {
        gui = weBillingAddressDetails.getText().trim();
        gui = gui.replace("\nEdit", "");
        data = formatAddress(address);
        ReportUtility.getInstance().logInfo(String.format("check billing address info: gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    public boolean checkShippingAddressTHTH(Address address) {
        /*
         * work around for format address wrong in TH TH
         */
        gui = weShipAddress.getText().trim();
        data = formatAddressTHTH(address);
        ReportUtility.getInstance().logInfo(String.format("check shipping address info: gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    public String getShippingMethod() {
        return weShipMethod.getText();
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

    public void enterCardNumber(String key) {
        weCardNumber.clear();
        if (key != null)
            weCardNumber.sendKeys(key);
    }

    public void enterCardMonth(String key) {
        if (key != null)
            new Select(weCardMonth).selectByVisibleText(key);
    }

    public void enterCardYear(String key) {
        if (key != null)
            new Select(weCardYear).selectByVisibleText(key);
    }

    public void enterCardCvv(String key) {
        weCardCvv.clear();
        if (key != null)
            weCardCvv.sendKeys(key);
    }

    public void fillInCyberSource(CreditCard card) {
        enterCardNumber(card.getCreditCardNumber());
        enterCardMonth(card.getExpMonth());
        enterCardYear(card.getExpYear());
        enterCardCvv(card.getSecurityCode());
    }

    public void setBillingAddressSameAsShipping(boolean tick) {
        waitForLoadingSuccess();
        Panasonic.checkStoreViewOnPayment(driver);
        waitUtility.waitUntilVisibilityOf(weBillingAddressSameAsShipping);
        if (weBillingAddressSameAsShipping.isSelected() != tick) {
            waitUtility.waitUntilToBeClickAble(weBillingAddressSameAsShipping);
            weBillingAddressSameAsShipping.click();
            handleLoading.handleAjaxLoading();
            handleLoading.handleLoadingMask(weOrderSummaryLoadingMask);
        }
    }

    public void setBillingAddress(Address address) {
        new Select(weBillingAddress).selectByVisibleText(formatAddressPayment(address));
        updateAddress();
    }

    private void updateAddress() {
        waitUtility.waitUntilToBeClickAble(weUpdateAddress);
        weUpdateAddress.click();
        handleLoading.handleAjaxLoading(TIME_OUT);
    }

    private String formatAddressPayment(Address address) {
        return String.format("%s , %s ,%s , %s,  %s,%s", address.getName(), address.getStreet(), address.getDistrict(), address.getWard(), address.getCity(), address.getCountry());
    }

    public String getCardNumberError() {
        return weCardNumberError.getText();
    }

    public String getCardExpirationError() {
        return weCardExpirationError.getText();
    }

    public String getCardExpiratioYearError() {
        return weCardExpiratioYearError.getText();
    }

    public String getCardCvvError() {
        return weCardCvvError.getText();
    }

    public void cancelCoupon() {
        handleLoading.handleLoadingMask(2);
        expandCoupon();
        if (weSubmitCoupon.getAttribute("value").equals("Cancel")) {
            submitCoupon();
        }
    }

    private void applyCoupon(String couponCode) {
        handleLoading.handleLoadingMask(2);
        cancelCoupon();
        setCouponCode(couponCode);
        submitCoupon();
    }

    public void applyCouponSuccess(String couponCode) {
        applyCoupon(couponCode);
        waitUtility.waitUntilVisibilityOf(weCouponMessageSuccess);
    }

    public void applyCouponError(String couponCode) {
        applyCoupon(couponCode);
        waitUtility.waitUntilVisibilityOf(weCouponMessageError);
    }

    private void setCouponCode(String couponCode) {
        waitUtility.waitUntilToBeClickAble(weCouponCode);
        weCouponCode.clear();
        weCouponCode.sendKeys(couponCode);
    }

    private void submitCoupon() {
        waitUtility.waitUntilToBeClickAble(weSubmitCoupon);
        weSubmitCoupon.click();
    }

    public String getCouponMessageSuccess() {
        waitUtility.waitUntilVisibilityOf(weCouponMessageSuccess);
        return weCouponMessageSuccess.getText().trim();
    }

    public String getCouponMessageError() {
        waitUtility.waitUntilVisibilityOf(weCouponMessageError);
        return weCouponMessageError.getText().trim();
    }

    public void expandCoupon() {
        waitUtility.waitUntilToBeClickAble(weExpandCoupon);
        if (!weExpandCoupon.getAttribute("aria-expanded").equals("true")) {
            weExpandCoupon.click();
            waitUtility.waitForValueOfAttribute(weExpandCoupon, "aria-expanded", "true");
        }
    }

    public String getCouponTitle() {
        waitUtility.waitUntilVisibilityOf(weCouponTitleSummary);
        return weCouponTitleSummary.getText().trim();
    }

    public String getCouponAmount() {
        waitUtility.waitUntilVisibilityOf(weCouponAmountSummary);
        return weCouponAmountSummary.getText().trim();
    }

    public boolean isCouponCodeSummaryDisplay() {
        handleLoading.handleLoadingMask(2);
        try {
            return weCouponTitleSummary.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void setPaymentCashCheque() {
        waitUtility.waitUntilToBeClickAble(weCashCheque);
        if (!weCashCheque.isSelected()) {
            weCashCheque.click();
        }
    }

    private void setPaymentStaffInstallment() {
        waitUtility.waitUntilToBeClickAble(weStaffInstallment);
        if (!weStaffInstallment.isSelected()) {
            weStaffInstallment.click();
        }
    }

    public void selectPaymentMethod(PaymentMethod paymentMethod) {
        waitForLoadingSuccess();
        Panasonic.checkStoreViewOnPayment(driver);
        switch (paymentMethod) {
            case CASH_CHEQUE:
                setPaymentCashCheque();
                break;
            case STAFF_INSTALLMENT:
                setPaymentStaffInstallment();
                break;
            case ONEPAY_DOMESTIC:
                setPaymentOnePAYDomestic();
                break;
            case ONEPAY_INTERNATIONAL:
                setPaymentOnePAYInternational();
                break;
            case CMO:
                setPaymentCMO();
                break;
            case COD:
                setPaymentCOD();
                break;
            case CREDIT_CARD:
                setPaymentCyberSource();
                break;
        }
    }

    public void selectInstallmentPackage(String installment_package) {
        waitUtility.waitUntilVisibilityOf(weInstallmentPackage);
        new Select(weInstallmentPackage).selectByVisibleText(installment_package);
        handleLoading.handleLoadingMask(weOrderSummaryLoadingMask);
    }

    public void addNewBillingAddress(Address address) {
        actionUtility.scrollWindow(0, -300);
        setBillingAddressSameAsShipping(false);
        selectUseNewBillingAddress();
        switch (DataTest.getCountry()) {
            case TH:
                fillInBillingAddressTH(address);
                break;
            case ID:
                fillInBillingAddressID(address);
                break;
            case MY:
                fillInBillingAddressMY(address);
                break;
            case VN:
                fillInBillingAddressVN(address);
            default:
                break;
        }
        setSaveInAddressBook(false);
        updateAddress();
    }

    private void fillInBillingAddressTH(Address address) {
        setBillingFirstName(address.getFirstName());
        setBillingLastName(address.getLastName());
        setBillingCompany(address.getCompany());
        setBillingTelephone(address.getTelephone());
        setBillingZip(address.getZip());
        setBillingState(address.getState());
        setBillingStreet(address.getStreet());
    }

    private void fillInBillingAddressID(Address address) {
        setBillingFullName(address.getName());
        setBillingCompany(address.getCompany());
        setBillingTelephone(address.getTelephone());
        setBillingZip(address.getZip());
        setBillingState(address.getState());
        selectCity(address.getCity());
        setBillingStreet(address.getStreet());
    }

    private void fillInBillingAddressVN(Address address) {
        setBillingFullName(address.getName());
        setBillingCompany(address.getCompany());
        setBillingTelephone(address.getTelephone());
        selectBillingCityVN(address.getCity());
        selectBillingDistrict(address.getDistrict());
        selectBillingWard(address.getWard());
        setBillingStreet(address.getStreet());
    }

    private void fillInBillingAddressMY(Address address) {
        setBillingFirstName(address.getFirstName());
        setBillingLastName(address.getLastName());
        setBillingState(address.getState());
        setBillingCity(address.getCity());
        setBillingStreet(address.getStreet());
        setBillingCompany(address.getCompany());
        setBillingZip(address.getZip());
        setBillingAddressName(address.getAddressName());
        setBillingMobileNumber(address.getMobileNumber());
        setBillingTelephone(address.getTelephone());
    }

    public String getBillingFirstNameError() {
        waitUtility.waitUntilVisibilityOf(weBillingFirstNameError);
        return weBillingFirstNameError.getText().trim();
    }

    public String getBillingLastNameError() {
        waitUtility.waitUntilVisibilityOf(weBillingLastNameError);
        return weBillingLastNameError.getText().trim();
    }

    public String getBillingStateError() {
        waitUtility.waitUntilVisibilityOf(weBillingStateError);
        return weBillingStateError.getText().trim();
    }

    public String getBillingZipError() {
        waitUtility.waitUntilVisibilityOf(weBillingZipError);
        return weBillingZipError.getText().trim();
    }

    public String getBillingTelephoneError() {
        waitUtility.waitUntilVisibilityOf(weBillingTelephoneError);
        return weBillingTelephoneError.getText().trim();
    }

    public String getBillingStreetError() {
        waitUtility.waitUntilVisibilityOf(weBillingStreetError);
        return weBillingStreetError.getText().trim();
    }

    public String getBillingCityError() {
        if (DataTest.getCountry() == Country.VN) {
            waitUtility.waitUntilVisibilityOf(weBillingCityVNError);
            return weBillingCityVNError.getText().trim();
        } else {
            waitUtility.waitUntilVisibilityOf(weBillingCityError);
            return weBillingCityError.getText().trim();
        }
    }

    public String getBillingFullNameError() {
        waitUtility.waitUntilVisibilityOf(weBillingFullNameError);
        return weBillingFullNameError.getText().trim();
    }

    public String getBillingWardError() {
        waitUtility.waitUntilVisibilityOf(weBillingWardError);
        return weBillingWardError.getText().trim();
    }

    public String getBillingDistrictError() {
        waitUtility.waitUntilVisibilityOf(weBillingDistrictError);
        return weBillingDistrictError.getText().trim();
    }

    public String getBillingAddressNameError() {
        waitUtility.waitUntilVisibilityOf(weBillingAddressNameError);
        return weBillingAddressNameError.getText().trim();
    }

    public String getBillingMobileNumberError() {
        waitUtility.waitUntilVisibilityOf(weBillingMobileNumberError);
        return weBillingMobileNumberError.getText().trim();
    }

    private void selectUseNewBillingAddress() {
        waitUtility.waitForPageLoad();
        waitUtility.waitUntilVisibilityOf(weBillingAddress);
        new Select(weBillingAddress).selectByVisibleText(getTitleAddNewAddress());
    }

    private String getTitleAddNewAddress() {
        switch (DataTest.getSiteCode()) {
            case TH_TH:
                return "ที่อยู่ใหม่";
            case ID_BA:
                return "Alamat baru";
            case VN_VN:
                return "Địa chỉ mới";
            default:
                return "New Address";
        }
    }

    private void setBillingFirstName(String firstName) {
        weBillingFirstName.clear();
        weBillingFirstName.sendKeys(firstName);
    }

    private void setBillingLastName(String lastName) {
        weBillingLastName.clear();
        weBillingLastName.sendKeys(lastName);
    }

    private void setBillingCompany(String company) {
        weBillingCompany.clear();
        weBillingCompany.sendKeys(company);
    }

    private void setBillingTelephone(String telephone) {
        weBillingTelephone.clear();
        weBillingTelephone.sendKeys(telephone);
    }

    private void setBillingState(String state) {
        new Select(weBillingState).selectByVisibleText(state);
    }

    private void selectCity(String city) {
        if (!city.equals("")) {
            waitUtility.waitUntilVisibilityOf(weBillingCity);
            new Select(weBillingCity).selectByVisibleText(city);
        }
    }

    private void setBillingStreet(String street) {
        weBillingStreet.clear();
        weBillingStreet.sendKeys(street);
    }

    private void setBillingZip(String zip) {
        weBillingZip.clear();
        weBillingZip.sendKeys(zip);
    }

    private void setSaveInAddressBook(boolean status) {
        if (weSaveInAddressBook.isSelected() != status) {
            weSaveInAddressBook.click();
        }
    }

    private void setBillingFullName(String fullName) {
        weBillingFullName.clear();
        weBillingFullName.sendKeys(fullName);
    }

    private void selectBillingDistrict(String district) {
        if (!district.equals("")) {
            waitUtility.waitUntilVisibilityOf(weBillingDistrict);
            new Select(weBillingDistrict).selectByVisibleText(district);
        }
    }

    private void selectBillingWard(String ward) {
        if (!ward.equals("")) {
            waitUtility.waitUntilVisibilityOf(weBillingWard);
            new Select(weBillingWard).selectByVisibleText(ward);
        }
    }

    private void selectBillingCityVN(String city) {
        waitUtility.waitUntilVisibilityOf(weBillingCityVN);
        new Select(weBillingCityVN).selectByVisibleText(city);
    }

    private void setBillingCity(String city) {
        waitUtility.waitUntilVisibilityOf(weBillingCityInput);
        weBillingCityInput.clear();
        weBillingCityInput.sendKeys(city);
    }

    private void setBillingAddressName(String addressName) {
        weBillingAddressName.clear();
        weBillingAddressName.sendKeys(addressName);
    }

    private void setBillingMobileNumber(String mobileNumber) {
        weBillingMobileNumber.clear();
        weBillingMobileNumber.sendKeys(mobileNumber);
    }

    public boolean checkInterest(int price) {
        gui = getInterest();
        data = PriceUtility.convertPriceToString(price);
        ReportUtility.getInstance().logInfo(String.format("check interest price: gui: %s - data: %s", gui, data));
        return gui.equals(data);
    }

    private String getInterest() {
        return weInterest.getText();
    }
}