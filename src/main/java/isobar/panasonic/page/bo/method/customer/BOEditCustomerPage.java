package isobar.panasonic.page.bo.method.customer;

import isobar.panasonic.components.Label;
import isobar.panasonic.customer.Address;
import isobar.panasonic.page.bo.locator.customer.BOEditCustomerLocator;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.HandleLoading;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BOEditCustomerPage extends BOEditCustomerLocator {
    private HandleLoading handleLoading;
    public AddressesTab addressesTab;

    public BOEditCustomerPage(WebDriver driver) {
        super(driver);
        handleLoading = new HandleLoading(driver);
    }

    public void expandAddressTab() {
        handleLoading.handleLoadingMask(weAdminLoadingMask, 3);
        waitUtility.waitUntilToBeClickAble(weAddressTab);
        weAddressTab.click();
        addressesTab = new AddressesTab();
    }

    public BOAllCustomerPage save() {
        waitUtility.waitForPageLoad();
        waitUtility.waitUntilToBeClickAble(weSave);
        weSave.click();
        return new BOAllCustomerPage(driver);
    }

    public void saveAndContinue() {
        waitUtility.waitUntilToBeClickAble(weSaveAndContinueEdit);
        weSaveAndContinueEdit.click();
        actionUtility.sleep(500);
        waitUtility.waitForPageLoad();
        waitForLoadingSuccess();
    }

    public String getSuccessMsg() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText().trim();
    }

    public String getErrorMsg() {
        waitUtility.waitUntilVisibilityOf(weErrorMsg);
        return weErrorMsg.getText().trim();
    }

    public void waitForLoadingSuccess() {
        handleLoading.handleLoadingMask(weAdminLoadingMask, 5);
        waitUtility.waitForPageLoad();
        waitUtility.waitForAttributeExists(weCustomerFormLoading, "style");
        waitUtility.waitForValueOfAttributeContains(weCustomerFormLoading, "style", "display: none;");
    }

    public class AddressesTab {
        private Label label;

        public AddressesTab() {
            label = new Label(driver);
        }

        public void fillInAddress(Address address) {
            switch (DataTest.getCountry()) {
                case TH:
                    fillInAddressTH(address);
                    break;
                case ID:
                    fillInAddressID(address);
                    break;
                case MY:
                    fillInAddressMy(address);
                    break;
                case VN:
                    fillInAddressVN(address);
                    break;
            }
        }

        public void addNewAddresses() {
            waitUtility.waitUntilToBeClickAble(weAddNewAddress);
            actionUtility.scrollToElement(weAddNewAddress, 0, -200);
            weAddNewAddress.click();
        }

        public String getFirstNameError() {
            waitUtility.waitUntilVisibilityOf(weFirstNameError);
            return weFirstNameError.getText().trim();
        }

        public String getLastNameError() {
            waitUtility.waitUntilVisibilityOf(weLastNameError);
            return weLastNameError.getText().trim();
        }

        public String getZipError() {
            waitUtility.waitUntilVisibilityOf(weZipError);
            return weZipError.getText().trim();
        }

        public String getStateError() {
            waitUtility.waitUntilVisibilityOf(weStateError);
            return weStateError.getText().trim();
        }

        public String getStreetError() {
            waitUtility.waitUntilVisibilityOf(weStreetError);
            return weStreetError.getText().trim();
        }

        public String getMobilePhoneError() {
            waitUtility.waitUntilVisibilityOf(weMobilePhoneError);
            return weMobilePhoneError.getText().trim();
        }

        public void setDefaultBillingAddress(Address address, boolean status) {
            waitUtility.waitForPageLoad();
            actionUtility.scrollToElement(weDefaultBillingAddressLabel, 0, -300);
            focusAddress(address);
            if (weDefaultBillingAddress.isSelected() != status) {
                waitUtility.waitUntilToBeClickAble(weDefaultBillingAddressLabel);
                weDefaultBillingAddressLabel.click();
            }
        }

        public void setDefaultShippingAddress(Address address, boolean status) {
            waitUtility.waitForPageLoad();
            actionUtility.scrollToElement(weDefaultShippingAddressLabel, 0, -300);
            focusAddress(address);
            if (weDefaultShippingAddress.isSelected() != status) {
                waitUtility.waitUntilToBeClickAble(weDefaultShippingAddressLabel);
                weDefaultShippingAddressLabel.click();
            }
        }

        public boolean validateAddressExists(Address address) {
            ReportUtility.getInstance().logInfo(String.format("check address : %s", formatAddress(address)));
            int index = getPositionAddress(address);
            return index != -1;
        }

        public boolean checkDefaultBillingAddress(Address address) {
            int index = getPositionAddress(address);
            if (index != -1) {
                label.setLocator(String.format(DEFAULT_BILLING_ADDRESS, index + 1));
                waitUtility.waitUntilVisibilityOf(label.getWebElement());
                return label.isChecked();
            }
            return false;
        }

        public boolean checkDefaultShippingAddress(Address address) {
            int index = getPositionAddress(address);
            if (index != -1) {
                label.setLocator(String.format(DEFAULT_SHIPPING_ADDRESS, index + 1));
                waitUtility.waitUntilVisibilityOf(label.getWebElement());
                return label.isChecked();
            }
            return false;
        }

        private int getPositionAddress(Address address) {
            int index = 0;
            String gui, data;
            data = formatAddress(address);
            for (WebElement we : weListAddress) {
                gui = we.getText().trim();
                if (gui.equals(data)) {
                    ReportUtility.getInstance().logInfo(String.format("address position: %s", index));
                    return index;
                } else {
                    index++;
                }
            }
            return -1;
        }

        private void focusAddress(Address address) {
            label.setLocator(String.format(ADDRESS, address.getStreet()));
            waitUtility.waitUntilToBeClickAble(label.getWebElement());
            label.click();
            label.setLocator(String.format(ADDRESS_BLOCK, address.getStreet()));
            waitUtility.waitForValueOfAttributeContains(label.getWebElement(), "class", "ui-state-active");
        }

        private void fillInAddressTH(Address address) {
            setFirstName(address.getFirstName());
            setLastName(address.getLastName());
            setZipCode(address.getZip());
            selectState(address.getState());
            setStreet(address.getStreet());
            setMobileNumber(address.getMobileNumber());
            setPhoneNumber(address.getTelephone());
        }

        private void fillInAddressID(Address address) {
            setFirstName(address.getName());
            setCompany(address.getCompany());
            setZipCode(address.getZip());
            selectState(address.getState());
            selectCity(address.getCity());
            setStreet(address.getStreet());
            setMobileNumber(address.getMobileNumber());
            setPhoneNumber(address.getTelephone());
        }

        private void fillInAddressVN(Address address) {
            setFirstName(address.getName());
            setCompany(address.getCompany());
            selectState(address.getCity());
            selectDistrict(address.getDistrict());
            selectWard(address.getWard());
            setStreet(address.getStreet());
            setMobileNumber(address.getMobileNumber());
            setPhoneNumber(address.getTelephone());
        }

        private void fillInAddressMy(Address address) {
            setFirstName(address.getFirstName());
            setLastName(address.getLastName());
            setCompany(address.getCompany());
            setZipCode(address.getZip());
            selectState(address.getState());
            setCity(address.getCity());
            setStreet(address.getStreet());
            setAddressName(address.getAddressName());
            setMobileNumber(address.getMobileNumber());
        }

        private void setFirstName(String firstName) {
            waitUtility.waitUntilToBeClickAble(weFirstName);
            weFirstName.clear();
            weFirstName.sendKeys(firstName);
        }

        private void setLastName(String lastName) {
            weLastName.clear();
            weLastName.sendKeys(lastName);
        }

        private void setCompany(String company) {
            weCompany.clear();
            weCompany.sendKeys(company);
        }

        private void setZipCode(String zipCode) {
            weZip.clear();
            weZip.sendKeys(zipCode);
        }

        private void setCity(String city) {
            weCityInput.clear();
            weCityInput.sendKeys(city);
        }

        private void setStreet(String street) {
            weStreet.clear();
            weStreet.sendKeys(street);
        }

        private void setTelephone(String telephone) {
            weTelephone.clear();
            weTelephone.sendKeys(telephone);
        }

        private void selectState(String state) {
            new Select(weState).selectByVisibleText(translateStateToEN(state));
        }

        private void selectCity(String city) {
            if (!city.equals("")) {
                new Select(weCitySelect).selectByVisibleText(city);
            }
        }

        private void selectDistrict(String district) {
            if (!district.equals("")) {
                waitUtility.waitUntilVisibilityOf(weDistrict);
                new Select(weDistrict).selectByVisibleText(district);
            }
        }

        private void setAddressName(String addressName) {
            weAddressName.clear();
            weAddressName.sendKeys(addressName);
        }

        private void setMobileNumber(String mobileNumber) {
            weMobileNumber.clear();
            weMobileNumber.sendKeys(mobileNumber);
        }

        private void setPhoneNumber(String phoneNumber) {
            weTelephone.clear();
            weTelephone.sendKeys(phoneNumber);
        }

        private void selectWard(String ward) {
            if (!ward.equals("")) {
                waitUtility.waitUntilVisibilityOf(weWard);
                new Select(weWard).selectByVisibleText(ward);
            }
        }

        private String translateStateToEN(String word) {
            switch (DataTest.getSiteCode()) {
                case TH_TH: {
                    if (word.equals("กรุงเทพมหานคร")) {
                        return "Bangkok";
                    }
                }
            }
            return word;
        }

        private String formatAddress(Address address) {
            switch (DataTest.getCountry()) {
                case ID:
                    return String.format("%s\n" +
                            "%s\n" +
                            "%s\n" +
                            "%s, %s, %s\n" +
                            "%s\n" +
                            "T: %s\n" +
                            "M: %s", address.getName(), address.getCompany(), address.getStreet(), address.getCity(), address.getState(), address.getZip(), address.getCountry(), address.getTelephone(), address.getMobileNumber());
                case TH:
                    return String.format("%s %s\n" +
                            "%s\n" +
                            "%s, %s\n" +
                            "%s\n" +
                            "T: %s\n" +
                            "M: %s", address.getFirstName(), address.getLastName(), address.getStreet(), translateStateToEN(address.getState()), address.getZip(), DataTest.getSiteCode().changeToEN(), address.getTelephone(), address.getMobileNumber());
                case MY:
                    return String.format("%s %s\n" +
                            "%s\n" +
                            "%s\n" +
                            "%s\n" +
                            "%s, %s, %s\n" +
                            "%s\n" +
                            "M: %s", address.getFirstName(), address.getLastName(), address.getCompany(), address.getAddressName(), address.getStreet(), address.getCity(), address.getState(), address.getZip(), address.getCountry(), address.getMobileNumber());
                default:
                    return String.format("%s\n" +
                                    "%s\n" +
                                    "%s\n" +
                                    "%s, %s, %s\n" +
                                    "%s\n" +
                                    "T: %s\n" +
                                    "M: %s", address.getName(), address.getCompany(),
                            address.getStreet(), address.getWard(), address.getDistrict(), address.getCity(), DataTest.getSiteCode().changeToEN(), address.getTelephone(), address.getMobileNumber());
            }
        }
    }
}
