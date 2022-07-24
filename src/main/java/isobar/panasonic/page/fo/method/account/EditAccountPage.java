package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.locator.account.EditAccountLocator;
import isobar.panasonic.utility.Calendar;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.DateUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class EditAccountPage extends EditAccountLocator {
    Calendar calendar;

    public EditAccountPage(WebDriver driver) {
        super(driver);
        calendar = new Calendar(driver);
    }

    public void fillInAccountInformation(CustomerInformation cus) {
        switch (DataTest.getCountry()) {
            case TH:
                fillInAccountInformationTH(cus);
                break;
            case ID:
                fillInAccountInformationID(cus);
                break;
            case VN:
                fillInAccountInformationVN(cus);
                break;
            case MY:
                fillInAccountInformationMY(cus);
            default:
                return;
        }
    }

    public AccountInformationPage saveAccount() {
        waitUtility.waitUntilToBeClickAble(weSaveAccount);
        weSaveAccount.click();
        waitUtility.waitForPageLoad();
        return new AccountInformationPage(driver);
    }

    private void fillInAccountInformationID(CustomerInformation cus) {
        setTitle(cus.getTitle());
        setFullName(cus.getName());
        setDateOfBirth(cus.getDateOfBirth());
        setNickname(cus.getNickName());
        setTypeOfIdentityCard(cus.getTypeOfIdentityCard());
        setIdentityCardNumber(cus.getIdentityCardNumber());
        selectSubscribeClubNewsletter(cus.isSubscribeNewsletter());
    }

    private void fillInAccountInformationTH(CustomerInformation cus) {
        setTitle(cus.getTitle());
        setFirstName(cus.getFirstName());
        setLastName(cus.getLastName());
        setNickname(cus.getNickName());
        setDateOfBirth(cus.getDateOfBirth());
    }

    private void fillInAccountInformationMY(CustomerInformation cus) {
        setFirstName(cus.getFirstName());
        setLastName(cus.getLastName());
        setNickname(cus.getNickName());
        setTypeOfIdentityCard(cus.getTypeOfIdentityCard());
        setIdentityCardNumber(cus.getIdentityCardNumber());
        setMaritalStatus(cus.getMaritalStatus());
        setDateOfBirth(cus.getDateOfBirth());
        setRace(cus.getRace());
    }

    private void setMaritalStatus(String key) {
        waitUtility.waitUntilVisibilityOf(weMaritalStatus);
        new Select(weMaritalStatus).selectByVisibleText(key);
    }


    private void fillInAccountInformationVN(CustomerInformation cus) {
        setFullName(cus.getName());
    }

    private void setRace(String key){
        waitUtility.waitUntilVisibilityOf(weRace);
        new Select(weRace).selectByVisibleText(key);
    }

    private void setTitle(String title) {
        waitUtility.waitUntilToBeClickAble(weTitle);
        new Select(weTitle).selectByVisibleText(title);
    }

    private void setFullName(String fullName) {
        weFullName.clear();
        weFullName.sendKeys(fullName);
    }

    private void setFirstName(String firstName) {
        weFirstName.clear();
        weFirstName.sendKeys(firstName);
    }

    private void setLastName(String lastName) {
        weLastName.clear();
        weLastName.sendKeys(lastName);
    }

    private void setChangeEmail(boolean status) {
        waitUtility.waitUntilVisibilityOf(weTickChangeEmail);
        if (weTickChangeEmail.isSelected() != status) {
            weTickChangeEmail.click();
            waitUtility.waitUntilVisibilityOf(weEmail);
        }
    }

    private void setChangePassword(boolean status) {
        waitUtility.waitUntilVisibilityOf(weTickChangePassword);
        if (weTickChangePassword.isSelected() != status) {
            weTickChangePassword.click();
            waitUtility.waitUntilVisibilityOf(wePassword);
        }
    }

    private void enterEmailChange(String email) {
        weEmail.clear();
        waitUtility.waitUntilVisibilityOf(weEmail);
        weEmail.sendKeys(email);
    }

    public void changeEmail(String email, String currentPassword) {
        setChangeEmail(true);
        enterEmailChange(email);
        enterCurrentPassword(currentPassword);
    }

    public void changePassword(String currentPass, String newPass, String confirmPass) {
        setChangePassword(true);
        enterCurrentPassword(currentPass);
        enterNewPassword(newPass);
        enterConfirmPassword(confirmPass);
    }

    private void enterCurrentPassword(String password) {
        waitUtility.waitUntilVisibilityOf(weCurrPassword);
        weCurrPassword.clear();
        weCurrPassword.sendKeys(password);
    }

    private void enterNewPassword(String password) {
        waitUtility.waitUntilVisibilityOf(wePassword);
        wePassword.sendKeys(password);
    }

    private void enterConfirmPassword(String password) {
        waitUtility.waitUntilVisibilityOf(weConfirmPassword);
        weConfirmPassword.sendKeys(password);
    }

    private void setNickname(String nickname) {
        waitUtility.waitUntilToBeClickAble(weNickname);
        weNickname.clear();
        weNickname.sendKeys(nickname);
    }

    private void setTypeOfIdentityCard(String typeOfIdentityCard) {
        waitUtility.waitUntilVisibilityOf(weTypeOfIdentityCard);
        new Select(weTypeOfIdentityCard).selectByVisibleText(typeOfIdentityCard);
    }

    private void setIdentityCardNumber(String identityCard) {
        waitUtility.waitUntilVisibilityOf(weIdentityCardNumber);
        weIdentityCardNumber.clear();
        weIdentityCardNumber.sendKeys(identityCard);
    }

    private void setDateOfBirth(String dateOfBirth) {
        if (!new DateUtility().checkFormatDate(dateOfBirth, "yyyy-mm-dd")) {
            setWrongFormatBirth(dateOfBirth);
        } else {
            weButtonCalendar.click();
            calendar.selectDate_Select(dateOfBirth);
            waitUtility.waitForValueOfAttributeContains(weCalendar, "style","display: none;");
        }
    }

    private void setWrongFormatBirth(String dateOfBirth) {
        weDateOfBirth.clear();
        weDateOfBirth.sendKeys(dateOfBirth);
        wePageTitle.click();
        waitUtility.waitForValueOfAttributeContains(weCalendar, "style","display: none;");
    }

    private void selectSubscribeClubNewsletter(boolean option) {
        waitUtility.waitUntilVisibilityOf(weSubscribeClubNewsletter);
        new Select(weSubscribeClubNewsletter).selectByVisibleText(DataTest.translateYesNoOption(option));
    }

    public String getNicknameError() {
        waitUtility.waitUntilVisibilityOf(weNicknameError);
        return weNicknameError.getText().trim();
    }

    public String getTypeOfIdentityCardError() {
        waitUtility.waitUntilVisibilityOf(weTypeOfIdentityCardError);
        return weTypeOfIdentityCardError.getText().trim();
    }

    public String getIdentityCardNumberError() {
        waitUtility.waitUntilVisibilityOf(weIdentityCardNumberError);
        return weIdentityCardNumberError.getText().trim();
    }

    public String getMessagingService() {
        waitUtility.waitUntilVisibilityOf(weMessagingService);
        return new Select(weMessagingService).getFirstSelectedOption().getText().trim();
    }

    public String getFirstNameError(){
        waitUtility.waitUntilVisibilityOf(weFirstNameError);
        return weFirstNameError.getText().trim();
    }

    public String getLastNameError(){
        waitUtility.waitUntilVisibilityOf(weLastNameError);
        return weLastNameError.getText().trim();
    }

    public String getRaceError(){
        waitUtility.waitUntilVisibilityOf(weRaceError);
        return weRaceError.getText().trim();
    }

    public String getDateOfBirthError(){
        waitUtility.waitUntilVisibilityOf(weDateOfBirthError);
        return weDateOfBirthError.getText().trim();
    }

    public String getFullNameError(){
        waitUtility.waitUntilVisibilityOf(weFullNameError);
        return weFullNameError.getText().trim();
    }
}
