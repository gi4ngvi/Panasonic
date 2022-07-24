package isobar.panasonic.page.fo.method.account;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.components.Label;
import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.locator.account.AccountInformationLocator;
import isobar.panasonic.page.fo.method.cart.ShoppingCartPage;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class AccountInformationPage extends AccountInformationLocator {
    public AccountInformationPage(WebDriver driver) {
        super(driver);
    }

    public MyOrderPage openMyOrderTab() {
        waitUtility.waitUntilToBeClickAble(weMyOrder);
        weMyOrder.click();
        return new MyOrderPage(driver);
    }

    public AccountInformationPage openAddressBookTab() {
        waitUtility.waitUntilToBeClickAble(weAddressBook);
        weAddressBook.click();
        return new AccountInformationPage(driver);
    }

    public EditAccountPage openAccountInfomationTab() {
        waitUtility.waitUntilToBeClickAble(weAccountInfomation);
        weAccountInfomation.click();
        return new EditAccountPage(driver);
    }

    public NewsletterSubscriptionPage openNewLetterTab() {
        waitUtility.waitUntilToBeClickAble(weNewLetterTab);
        weNewLetterTab.click();
        return new NewsletterSubscriptionPage(driver);
    }

    public MyAddressPage manageAddresses() {
        waitUtility.waitUntilToBeClickAble(weAddressBook);
        weAddressBook.click();
        return new MyAddressPage(driver);
    }

    public boolean checkContactInfo(CustomerInformation cus) {
        String gui, data;
        gui = weContactInfo.getText().trim();
        switch (DataTest.getCountry()) {
            case TH:
                data = cus.getTitle() + " " + cus.getFirstName() + " " + cus.getLastName() + "\n" + cus.getEmail();
                break;
            case ID:
                data = cus.getTitle() + " " + cus.getName() + "\n" + cus.getEmail();
                break;
            case MY:
                data = cus.getFirstName() + " " + cus.getLastName() + "\n" + cus.getEmail();
                break;
            default:
                data = cus.getName() + "\n" + cus.getEmail();
                break;
        }
        ReportUtility.getInstance().logInfo(String.format("check contact info: GUI: %s - DATA: %s", gui, data));
        return gui.equals(data);
    }

    public void fillInEmptyInfo(CustomerInformation cus) {
        enterFirstname(cus.getName());
        tickChangePassword(true);
        tickChangeEmail(true);
        enterEmail(cus.getEmail());
        enterCurrPassword(cus.getPassword());
        enterPassword(cus.getPassword());
        enterConfirmPassword(cus.getPasswordConfirm());
        save();
    }

    public LoginPopupPage editAccountInfo(CustomerInformation oldCus, CustomerInformation newCus) {
        enterFirstname(newCus.getName());
        tickChangePassword(true);
        tickChangeEmail(true);
        enterEmail(newCus.getEmail());
        enterCurrPassword(oldCus.getPassword());
        enterPassword(newCus.getPassword());
        enterConfirmPassword(newCus.getPasswordConfirm());
        save();
        return new LoginPopupPage(driver);
    }

    public void fillInAddress(Address address) {
        enterFirstname(address.getName());
        enterCompany(address.getCompany());
        enterTelephone(address.getTelephone());
        enterCity(address.getCity());
        enterDistrict(address.getDistrict());
        enterWard(address.getWard());
        enterStreet(address.getStreet());
    }

    public void enterFirstname(String key) {
        weFirstname.clear();
        weFirstname.sendKeys(key);
    }

    public void enterEmail(String key) {
        weEmail.clear();
        weEmail.sendKeys(key);
    }

    public void enterBirthmonth(String key) {
        new Select(weBirthmonth).selectByVisibleText(key);
    }

    public void enterBirthyear(String key) {
        new Select(weBirthyear).selectByVisibleText(key);
    }

    public String getBirthmonth() {
        return new Select(weBirthmonth).getFirstSelectedOption().getText();
    }

    public String getBirthyear() {
        return new Select(weBirthyear).getFirstSelectedOption().getText();
    }

    public void tickChangePassword(boolean key) {
        if (!weTickChangePassword.isSelected() && key == true) {
            weTickChangePassword.click();
        }

    }

    public void tickChangeEmail(boolean key) {
        if (!weTickChangeEmail.isSelected() && key == true) {
            weTickChangeEmail.click();
        }

    }

    public void enterCurrPassword(String key) {
        weCurrPassword.clear();
        weCurrPassword.sendKeys(key);
    }

    public void enterPassword(String key) {
        wePassword.clear();
        wePassword.sendKeys(key);
    }

    public void enterConfirmPassword(String key) {
        weConfirmPassword.clear();
        weConfirmPassword.sendKeys(key);
    }

    public void enterTelephone(String key) {
        weTelephone.clear();
        weTelephone.sendKeys(key);
    }

    public void enterStreet(String key) {
        weStreet.clear();
        weStreet.sendKeys(key);
    }

    public void enterCity(String key) {
        waitUtility.waitUntilToBeClickAble(weCity);
        new Select(weCity).selectByVisibleText(key);
    }

    public void enterCompany(String key) {
        weCompany.clear();
        weCompany.sendKeys(key);
    }

    public void enterDistrict(String key) {
        new Select(weDistrict).selectByVisibleText(key);
    }

    public void enterWard(String key) {
        new Select(weWard).selectByVisibleText(key);
    }

    public void save() {
        waitUtility.waitUntilToBeClickAble(weSave);
        actionUtility.scrollToElement(weSave, 0, -100);
        weSave.click();
    }

    public void saveAddress() {
        waitUtility.waitUntilToBeClickAble(weSaveAddress);
        actionUtility.scrollToElement(weSaveAddress, 0, -100);
        weSaveAddress.click();
    }

    public String getErrorMsg() {
        waitUtility.waitUntilVisibilityOf(weErrorMsg);
        return weErrorMsg.getText();
    }

    public String getSuccessMsg() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText();
    }

    public String getNameError() {
        return weNameError.getText();
    }

    public String getEmailError() {
        return weEmailError.getText();
    }

    public String getCurrPasswordError() {
        return weCurrPassError.getText();
    }

    public String getPasswordError() {
        return wePassError.getText();
    }

    public String getPasswordConfirmError() {
        return wePassConfirmError.getText();
    }

    public void clickAddAddress() {
        waitUtility.waitUntilToBeClickAble(weAddAddress);
        weAddAddress.click();
    }

    public void createAddress(Address address) {
        clickAddAddress();
        fillInAddress(address);
        saveAddress();
    }

    public boolean validateAddressExisted(Address address) {
        for (WebElement add : weAddressList) {
            ReportUtility.getInstance().log(LogStatus.INFO, "GUI : " + add.getText());
            ReportUtility.getInstance().log(LogStatus.INFO, "Data: " + formatAddress(address));
            if (add.getText().equals(formatAddress(address))) return true;
        }
        return false;
    }

    public boolean validateAddressDefaultShippingAddress(Address address) {
        waitUtility.waitUntilVisibilityOf(weDefaultShipAddress);
        ReportUtility.getInstance().log(LogStatus.INFO, "GUI : " + weDefaultShipAddress.getText());
        ReportUtility.getInstance().log(LogStatus.INFO, "Data: " + formatDefaultAddress(address));
        if (weDefaultShipAddress.getText().equals(formatDefaultAddress(address))) return true;
        return false;
    }

    public boolean validateAddressDefaultBillingAddress(Address address) {
        waitUtility.waitUntilVisibilityOf(weDefaultBillAddress);
        ReportUtility.getInstance().log(LogStatus.INFO, "GUI : " + weDefaultBillAddress.getText());
        ReportUtility.getInstance().log(LogStatus.INFO, "Data: " + formatDefaultAddress(address));
        if (weDefaultBillAddress.getText().equals(formatDefaultAddress(address))) return true;
        return false;
    }

    public void updateAddress(Address oldAdd, Address newAdd) {
        selectAddressUpdate(oldAdd);
        fillInAddress(newAdd);
        saveAddress();
    }

    public void selectAddressUpdate(Address oldAdd) {
        WebElement edit = driver.findElement(By.xpath(String.format(EDIT_ADDRESS, oldAdd.getStreet())));
        edit.click();
    }

    public void deleteAddress(Address address) {
        WebElement delete = driver.findElement(By.xpath(String.format(DELETE_ADDRESS, address.getStreet())));
        delete.click();
        actionUtility.sleep(1000);
        acceptPopup();
    }

    private void acceptPopup() {
        waitUtility.waitUntilToBeClickAble(weAcceptPopup);
        weAcceptPopup.click();
    }

    public String formatAddress(Address address) {
        return String.format("%s\n" +
                        "%s\n" +
                        "%s, %s, %s, %s\n" +
                        "%s\n" +
                        "T: %s\n" +
                        "Sửa địa chỉ Xóa địa chỉ", address.getName(), address.getCompany(),
                address.getStreet(), address.getWard(), address.getDistrict(), address.getCity(), address.getCountry(), address.getTelephone());
    }

    public String formatDefaultAddress(Address address) {
        return String.format("%s\n" +
                        "%s\n" +
                        "%s, %s, %s, %s\n" +
                        "%s\n" +
                        "T: %s", address.getName(), address.getCompany(),
                address.getStreet(), address.getWard(), address.getDistrict(), address.getCity(), address.getCountry(), address.getTelephone());
    }

    public ShoppingCartPage reOrder(String orderID) {
        WebElement element = driver.findElement(By.xpath(String.format(REORDER, orderID)));
        element.click();
        return new ShoppingCartPage(driver);
    }

    public String getTelephoneError() {
        return weTelephoneError.getText();
    }

    public String getCityError() {
        return weCityError.getText();
    }

    public String getDistrictError() {
        return weDistrictError.getText();
    }

    public String getWardError() {
        return weWardError.getText();
    }

    public String getStreetError() {
        return weStreetError.getText();
    }

    public void clickSubcribe() {
        waitUtility.waitUntilToBeClickAble(weSubcribe);
        weSubcribe.click();
    }

    public void untickSubcribe() {
        waitUtility.waitUntilToBeClickAble(weSubcribe);
        if (weSubcribe.isSelected()) {
            weSubcribe.click();
            save();
        }
    }

    public OrderDetailsPage viewOrder(String orderID) {
        Label labView = new Label(driver, String.format(VIEW_ORDER, orderID));
        waitUtility.waitUntilToBeClickAble(labView.getWebElement());
        labView.click();
        return new OrderDetailsPage(driver);
    }

    public RewardPointsPage openRewardPointsPage() {
        waitUtility.waitUntilToBeClickAble(weRewardPoints);
        weRewardPoints.click();
        waitUtility.waitForPageLoad();
        return new RewardPointsPage(driver);
    }

    public WarrantyHistoryPage gotoWarrantyHistoryPage(){
        waitUtility.waitUntilToBeClickAble(weWarrantyHistory);
        weWarrantyHistory.click();
        waitUtility.waitForPageLoad();
        return new WarrantyHistoryPage(driver);
    }

    public WarrantyPage gotoWarrantyPage(){
        waitUtility.waitUntilToBeClickAble(weWarranty);
        weWarranty.click();
        waitUtility.waitForPageLoad();
        return new WarrantyPage(driver);
    }
}
