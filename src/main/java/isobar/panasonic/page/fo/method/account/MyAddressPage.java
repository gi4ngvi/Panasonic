package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.components.Label;
import isobar.panasonic.customer.Address;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.page.fo.locator.account.MyAddressLocator;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class MyAddressPage extends MyAddressLocator {
    public MyAddressPage(WebDriver driver) {
        super(driver);
    }

    public void emptyAddress() {
        WebElement delete;
        List<WebElement> listEle = driver.findElements(By.cssSelector(DELETE_ADDRESS_BUTTON));
        while (!listEle.isEmpty()) {
            delete = listEle.get(0);
            waitUtility.waitUntilToBeClickAble(delete);
            delete.click();
            waitUtility.waitUntilToBeClickAble(weButtonAccept);
            weButtonAccept.click();
            waitUtility.waitForPageLoad();
            listEle = driver.findElements(By.cssSelector(DELETE_ADDRESS_BUTTON));
        }
    }

    public void changeBillingAddress() {
        waitUtility.waitUntilToBeClickAble(weChangeBillingAddress);
        weChangeBillingAddress.click();
    }

    public void changeShippingAddress() {
        waitUtility.waitUntilToBeClickAble(weChangeShippingAddress);
        weChangeShippingAddress.click();
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
                fillInAddressMY(address);
                break;
            default:
                fillInAddressVN(address);
                break;
        }
    }

    public void saveAddress() {
        waitUtility.waitUntilToBeClickAble(weSaveAddress);
        weSaveAddress.click();
    }

    public boolean checkDefaultBillingAddress(Address address) {
        String gui, data;
        gui = getDefaultBillingAddress();
        data = formatAddress(address);
        ReportUtility.getInstance().logInfo(String.format("check Default Billing Address - gui:%s - data:%s ", gui, data));
        return gui.equals(data);
    }

    public boolean checkDefaultShippingAddress(Address address) {
        String gui, data;
        gui = getDefaultShippingAddress();
        data = formatAddress(address);
        ReportUtility.getInstance().logInfo(String.format("check Default Shipping  Address - gui:%s - data:%s ", gui, data));
        return gui.equals(data);
    }

    public String getSuccessMsg() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText();
    }

    private String formatAddress(Address address) {
        switch (DataTest.getCountry()) {
            case ID:
                return String.format("%s\n" +
                        "%s\n" +
                        "%s,\n" +
                        "%s, %s %s\n" +
                        "%s\n" +
                        "T: %s", address.getName(), address.getCompany(), address.getStreet(), address.getCity(), address.getState(), address.getZip(), address.getCountry(), address.getTelephone());
            case TH:
                return String.format("%s %s\n" +
                        "%s,\n" +
                        "%s %s\n" +
                        "%s\n" +
                        "T: %s", address.getFirstName(), address.getLastName(), address.getStreet(), address.getState(), address.getZip(), address.getCountry(), address.getTelephone());
            case MY:
                return String.format("%s %s\n" +
                                "%s\n" +
                                "%s\n" +
                                "%s,\n" +
                                "%s, %s %s\n" +
                                "%s\n" +
                                "M: %s", address.getFirstName(), address.getLastName(), address.getAddressName(), address.getCompany(), address.getStreet(), address.getCity(), address.getState(), address.getZip(),
                        address.getCountry(), address.getMobileNumber());
            default:
                return String.format("%s\n" +
                                "%s\n" +
                                "%s, %s, %s, %s\n" +
                                "%s\n" +
                                "T: %s", address.getName(), address.getCompany(),
                        address.getStreet(), address.getWard(), address.getDistrict(), address.getCity(), address.getCountry(), address.getTelephone());
        }
    }

    private String getDefaultBillingAddress() {
        waitUtility.waitUntilVisibilityOf(weDefaultBillingAddress);
        return weDefaultBillingAddress.getText().trim();
    }

    private String getDefaultShippingAddress() {
        waitUtility.waitUntilVisibilityOf(weDefaultShippingAddress);
        return weDefaultShippingAddress.getText().trim();
    }


    private void fillInAddressVN(Address address) {
        setFullName(address.getName());
        setCompany(address.getCompany());
        setTelephone(address.getTelephone());
        setCityVN(address.getCity());
        setDistrict(address.getDistrict());
        setWard(address.getWard());
        setStreet(address.getStreet());
    }

    private void fillInAddressID(Address address) {
        setFullName(address.getName());
        setCompany(address.getCompany());
        setTelephone(address.getTelephone());
        setState(address.getState());
        selectCity(address.getCity());
        setZip(address.getZip());
        setStreet(address.getStreet());
    }

    private void fillInAddressTH(Address address) {
        setFirstName(address.getFirstName());
        setLastName(address.getLastName());
        setTelephone(address.getTelephone());
        setState(address.getState());
        setZip(address.getZip());
        setStreet(address.getStreet());
    }

    private void fillInAddressMY(Address address) {
        setFirstName(address.getFirstName());
        setLastName(address.getLastName());
        setCompany(address.getCompany());
        setState(address.getState());
        enterCity(address.getCity());
        setZip(address.getZip());
        setStreet(address.getStreet());
        setAddressName(address.getAddressName());
        setMobileNumber(address.getMobileNumber());
    }

    private void setMobileNumber(String key) {
        weMobileNumber.clear();
        weMobileNumber.sendKeys(key);
    }

    public void setAddressName(String key) {
        waitUtility.waitUntilVisibilityOf(weAddressName);
        weAddressName.clear();
        weAddressName.sendKeys(key);
    }

    private void setFullName(String fullName) {
        weName.clear();
        weName.sendKeys(fullName);
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

    private void setTelephone(String key) {
        weTelephone.clear();
        weTelephone.sendKeys(key);
    }

    private void setStreet(String key) {
        weStreet.clear();
        weStreet.sendKeys(key);
    }

    private void selectCity(String key) {
        if (!key.equals("")) {
            waitUtility.waitUntilToBeClickAble(weCity);
            new Select(weCity).selectByVisibleText(key);
        }
    }

    private void enterCity(String key) {
        waitUtility.waitUntilToBeClickAble(weCityEnter);
        weCityEnter.clear();
        weCityEnter.sendKeys(key);
    }

    private void setCompany(String key) {
        weCompany.clear();
        weCompany.sendKeys(key);
    }

    private void setDistrict(String key) {
        if (!key.equals("")) {
            new Select(weDistrict).selectByVisibleText(key);
        }
    }

    private void setWard(String key) {
        if (!key.equals("")) {
            new Select(weWard).selectByVisibleText(key);
        }
    }

    private void setState(String state) {
        new Select(weState).selectByVisibleText(state);
    }

    private void setCityVN(String cityVN) {
        new Select(weCityVN).selectByVisibleText(cityVN);
    }

    private void setZip(String zip) {
        weZip.clear();
        weZip.sendKeys(zip);
    }

    public void addNewAddress() {
        waitUtility.waitUntilToBeClickAble(weAddAddress);
        weAddAddress.click();
        waitUtility.waitForPageLoad();
    }

    public void setDefaultBillingAddress(boolean status) {
        waitUtility.waitUntilToBeClickAble(wePrimaryBilling);
        if (wePrimaryBilling.isSelected() != status) {
            wePrimaryBilling.click();
        }
    }

    public void setDefaultShippingAddress(boolean status) {
        waitUtility.waitUntilToBeClickAble(wePrimaryShipping);
        if (wePrimaryShipping.isSelected() != status) {
            wePrimaryShipping.click();
        }
    }

    public String getFirstNameError() {
        waitUtility.waitUntilVisibilityOf(weFirstNameError);
        return weFirstNameError.getText().trim();
    }

    public String getLastNameError() {
        waitUtility.waitUntilVisibilityOf(weLastNameError);
        return weLastNameError.getText().trim();
    }

    public String getFullNameError() {
        waitUtility.waitUntilVisibilityOf(weFullNameError);
        return weFullNameError.getText().trim();
    }

    public String getTelephoneError() {
        waitUtility.waitUntilVisibilityOf(weTelephoneError);
        return weTelephoneError.getText().trim();
    }

    public String getStateError() {
        waitUtility.waitUntilVisibilityOf(weStateError);
        return weStateError.getText().trim();
    }

    public String getCityError() {
        if (DataTest.getCountry() == Country.VN) {
            waitUtility.waitUntilVisibilityOf(weCityVNError);
            return weCityVNError.getText().trim();
        } else {
            waitUtility.waitUntilVisibilityOf(weCityError);
            return weCityError.getText().trim();
        }
    }

    public String getAddressNameError() {
        waitUtility.waitUntilVisibilityOf(weAddressNameError);
        return weAddressNameError.getText().trim();
    }

    public String getMobileNumberError() {
        waitUtility.waitUntilVisibilityOf(weMobileNumberError);
        return weMobileNumberError.getText().trim();
    }

    public String getStreetError() {
        waitUtility.waitUntilVisibilityOf(weStreetError);
        return weStreetError.getText().trim();
    }

    public String getWardError() {
        waitUtility.waitUntilVisibilityOf(weWardError);
        return weWardError.getText().trim();
    }

    public String getDistrictError() {
        waitUtility.waitUntilVisibilityOf(weDistrictError);
        return weDistrictError.getText().trim();
    }

    public String getZipError() {
        waitUtility.waitUntilVisibilityOf(weZipError);
        return weZipError.getText().trim();
    }

    public boolean validateAddressExisted(Address address) {
        for (WebElement add : weListAddress) {
            if (add.getText().equals(formatAddress(address))) return true;
        }
        return false;
    }

    public void editAddress(Address address) {
        Label weEdit = new Label(driver, String.format(EDIT_ADDRESS, address.getStreet()));
        waitUtility.waitUntilVisibilityOf(weEdit.getWebElement());
        weEdit.click();
        waitUtility.waitForPageLoad();
    }

    public void deleteAddress(Address address) {
        Label weDelete = new Label(driver, String.format(DELETE_ADDRESS, address.getStreet()));
        weDelete.click();
        actionUtility.sleep(1000);
        acceptPopup();
    }

    private void acceptPopup() {
        waitUtility.waitUntilToBeClickAble(weAcceptPopup);
        weAcceptPopup.click();
    }
}
