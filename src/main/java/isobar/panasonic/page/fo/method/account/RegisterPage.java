package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.page.fo.locator.account.RegisterLocator;
import isobar.panasonic.page.fo.method.utility.HeaderPage;
import isobar.panasonic.utility.Calendar;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.DateUtility;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends RegisterLocator {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private void setFirstname(String key) {
        weFirstname.clear();
        weFirstname.sendKeys(key);
    }

    private void setEmail(String key) {
        weEmail.clear();
        weEmail.sendKeys(key);
    }

    private void setPassword(String key) {
        wePassword.clear();
        wePassword.sendKeys(key);
        wePassword.sendKeys(Keys.TAB);
    }

    private void setPasswordconfirm(String key) {
        wePasswordconfirm.clear();
        wePasswordconfirm.sendKeys(key);
    }

    private void setTitle(String title) {
        waitUtility.waitUntilVisibilityOf(weTitle);
        new Select(weTitle).selectByVisibleText(title);
    }

    private void setFullName(String fullName) {
        weFullName.clear();
        weFullName.sendKeys(fullName);
    }

    private void setNickname(String nickname) {
        weNickname.clear();
        weNickname.sendKeys(nickname);
    }

    private void setTypeOfIdentityCard(String typeOfIdentityCard) {
        new Select(weTypeOfIdentityCard).selectByVisibleText(typeOfIdentityCard);
    }

    private void setIdentityCardNumber(String identityCardNumber) {
        weIdentityCardNumber.clear();
        weIdentityCardNumber.sendKeys(identityCardNumber);
    }

    private void setLastName(String lastName) {
        weLastName.clear();
        weLastName.sendKeys(lastName);
    }

    public String getFirstnameError() {
        waitUtility.waitUntilVisibilityOf(weFirstnameError, 2);
        return weFirstnameError.getText();
    }

    public String getEmailError() {
        return weEmailError.getText();
    }

    public String getPasswordError() {
        return wePasswordError.getText();
    }

    public String getPasswordStrength() {
        waitUtility.waitUntilVisibilityOf(wePasswordStrength);
        return wePasswordStrength.getText();
    }

    public String getPasswordconfirmError() {
        return wePasswordconfirmError.getText();
    }

    public String getFullNameError() {
        waitUtility.waitUntilVisibilityOf(weFullnameError);
        return weFullnameError.getText();
    }

    public String getNickNameError() {
        waitUtility.waitUntilVisibilityOf(weNicknameError);
        return weNicknameError.getText();
    }

    public String getLastnameError() {
        waitUtility.waitUntilVisibilityOf(weLastnameError);
        return weLastnameError.getText();
    }

    public String getTypeOfIdentityCardError() {
        waitUtility.waitUntilVisibilityOf(weTypeOfIdentityCardError);
        return weTypeOfIdentityCardError.getText().trim();
    }

    public String getIdentityCardNumberError() {
        waitUtility.waitUntilVisibilityOf(weIdentityCardNumberError);
        return weIdentityCardNumberError.getText().trim();
    }

    public AccountInformationPage submit() {
        actionUtility.sleep(1000);
        waitUtility.waitUntilToBeClickAble(weSubmitBtn);
        actionUtility.mouseClick(weSubmitBtn);
        return new AccountInformationPage(driver);
    }

    public void clearEmail() {
        weEmail.clear();
    }

    public AccountInformationPage fillInRegistrationAndSubmit(CustomerInformation cus) {
        fillInNewCustomerAccount(cus);
        submit();
        new HeaderPage(driver).selectStore();
        return new AccountInformationPage(driver);
    }

    public void fillInNewCustomerAccount(CustomerInformation cus) {
        switch (DataTest.getCountry()) {
            case ID:
                fillInPersonalInformationID(cus);
                break;
            case TH:
                fillInPersonalInformationTH(cus);
                break;
            case MY:
                fillInPersonalInformationMY(cus);
                break;
            default:
                fillInPersonalInformationVN(cus);
        }
        fillInSignInInformation(cus);
    }

    public void fillInAddressInformation(Address address) {
        setCompany(address.getCompany());
        setStreet(address.getStreet());
        setCity(address.getCity());
        setState(address.getState());
        setZip(address.getZip());
        setAddressName(address.getAddressName());
        setMobileNumber(address.getMobileNumber());
        if(DataTest.getCountry() != Country.MY) {
            setTelephone(address.getTelephone());
        }
    }

    public void setDefaultShippingAddress(boolean status) {
        waitUtility.waitUntilVisibilityOf(weDefaultShippingAddress);
        if (weDefaultShippingAddress.isSelected() != status) {
            weDefaultShippingAddress.click();
        }
    }

    public String getErrorMsg() {
        waitUtility.waitUntilVisibilityOf(weErrorMsg);
        return weErrorMsg.getText();
    }

    private void fillInPersonalInformationID(CustomerInformation cus) {
        setTitle(cus.getTitle());
        setFullName(cus.getName());
        setNickname(cus.getNickName());
        setDateOfBirth(cus.getDateOfBirth());
        setTypeOfIdentityCard(cus.getTypeOfIdentityCard());
        setIdentityCardNumber(cus.getIdentityCardNumber());
        selectSubscribeClubNewsletter(cus.isSubscribeNewsletter());
        setIgnoreVerifyCode(true);
    }

    private void fillInPersonalInformationVN(CustomerInformation cus) {
        setFullName(cus.getName());
        setIgnoreVerifyCode(true);
    }

    private void fillInPersonalInformationTH(CustomerInformation cus) {
        setFirstname(cus.getFirstName());
        setLastName(cus.getLastName());
        setNickname(cus.getNickName());
        setDateOfBirth(cus.getDateOfBirth());
        setIgnoreVerifyCode(true);
    }

    private void fillInPersonalInformationMY(CustomerInformation cus) {
        setFirstname(cus.getFirstName());
        setLastName(cus.getLastName());
        setNickname(cus.getNickName());
        setDateOfBirth(cus.getDateOfBirth());
        setMaritalStatus(cus.getMaritalStatus());
        setTypeOfIdentityCard(cus.getTypeOfIdentityCard());
        setRace(cus.getRace());
        setIdentityCardNumber(cus.getIdentityCardNumber());
        setSubscribeNewsletter(cus.isSubscribeNewsletter());
        setMessagingService(cus.isMessagingService());
        setIgnoreVerifyCode(true);
    }

    private void fillInSignInInformation(CustomerInformation cus) {
        setEmail(cus.getEmail());
        setPassword(cus.getPassword());
        setPasswordconfirm(cus.getPasswordConfirm());
    }

    public String getTelephoneError() {
        waitUtility.waitUntilVisibilityOf(weTelephoneError);
        return weTelephoneError.getText().trim();
    }

    public String getStreetError() {
        waitUtility.waitUntilVisibilityOf(weStreetError);
        return weStreetError.getText().trim();
    }

    public String getCityError() {
        waitUtility.waitUntilVisibilityOf(weCityError);
        return weCityError.getText().trim();
    }

    public String getStateError() {
        waitUtility.waitUntilVisibilityOf(weStateError);
        return weStateError.getText().trim();
    }

    public String getAddressNameError() {
        waitUtility.waitUntilVisibilityOf(weAddressNameError);
        return weAddressNameError.getText().trim();
    }

    public void setAddressName(String key) {
        waitUtility.waitUntilVisibilityOf(weAddressName);
        weAddressName.clear();
        weAddressName.sendKeys(key);
    }

    public String getMobileNumberError() {
        waitUtility.waitUntilVisibilityOf(weMobileNumberError);
        return weMobileNumberError.getText().trim();
    }

    public String getDateOfBirthError() {
        waitUtility.waitUntilVisibilityOf(weDateOfBirthError);
        return weDateOfBirthError.getText().trim();
    }

    private void setRace(String key) {
        waitUtility.waitUntilVisibilityOf(weRace);
        new Select(weRace).selectByVisibleText(key);
    }

    public String getRaceError() {
        waitUtility.waitUntilVisibilityOf(weRaceError);
        return weRaceError.getText().trim();
    }

    private void setCompany(String key) {
        weCompany.clear();
        weCompany.sendKeys(key);
    }

    private void setTelephone(String key) {
        weTelephone.clear();
        weTelephone.sendKeys(key);
    }

    private void setStreet(String key) {
        weStreet.clear();
        weStreet.sendKeys(key);
    }

    private void setCity(String key) {
        waitUtility.waitUntilToBeClickAble(weCity);
        weCity.clear();
        weCity.sendKeys(key);
    }

    private void setState(String state) {
        new Select(weState).selectByVisibleText(state);
    }

    private void setZip(String zip) {
        weZip.clear();
        weZip.sendKeys(zip);
    }

    private void setMaritalStatus(String key) {
        waitUtility.waitUntilVisibilityOf(weMaritalStatus);
        new Select(weMaritalStatus).selectByVisibleText(key);
    }

    private void setMobileNumber(String key) {
        weMobileNumber.clear();
        weMobileNumber.sendKeys(key);
    }

    private void setSubscribeNewsletter(boolean status) {
        waitUtility.waitUntilVisibilityOf(weSubscribeNewsletter);
        if (weSubscribeNewsletter.isSelected() != status) {
            weSubscribeNewsletter.click();
        }
    }

    private void setDateOfBirth(String dateOfBirth) {
        if (dateOfBirth.equals("")) {
            weDateOfBirth.clear();
            expandCalendar(false);
        } else {
            expandCalendar(true);
            new Calendar(driver).selectDate_Select(dateOfBirth);
            waitUtility.waitForValueOfAttributeContains(weCalendar, "style", "display: none;");
        }
    }

    private void expandCalendar(boolean status) {
        try {
            if (weCalendar.isDisplayed() != status) {
                weButtonCalendar.click();
            }
        } catch (NoSuchElementException e) {
        }
    }

    private void setMessagingService(boolean status) {
        waitUtility.waitUntilVisibilityOf(weMessagingService);
        if (weMessagingService.isSelected() != status) {
            weMessagingService.click();
        }
    }

    public String getZipError() {
        waitUtility.waitUntilVisibilityOf(weZipError);
        return weZipError.getText().trim();
    }

    private void selectSubscribeClubNewsletter(boolean option) {
        waitUtility.waitUntilVisibilityOf(weSubscribeClubNewsletter);
        new Select(weSubscribeClubNewsletter).selectByVisibleText(DataTest.translateYesNoOption(option));
    }

    private void setIgnoreVerifyCode(boolean isSelect) {
        if(weIgnoreCodeVerify.isSelected() != isSelect) {
            weIgnoreCodeVerify.click();
        }
    }

    public void setAcceptTermAndConditions(boolean isSelect) {
        if (weAcceptTermAndConditions.isSelected() != isSelect) {
            weAcceptTermAndConditions.click();
        }
    }
}
