package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RegisterLocator extends SeleniumFactory {
    @FindBy(how = How.CSS, using = ".action.submit")
    protected WebElement weSubmitBtn;
    @FindBy(how = How.ID, using = "firstname")
    protected WebElement weFirstname;
    @FindBy(how = How.ID, using = "dob")
    protected WebElement weDOB;
    @FindBy(how = How.ID, using = "email_address")
    protected WebElement weEmail;
    @FindBy(how = How.ID, using = "password")
    protected WebElement wePassword;
    @FindBy(how = How.ID, using = "password-confirmation")
    protected WebElement wePasswordconfirm;
    @FindBy(how = How.ID,using = "id_number")
    protected WebElement weIdentityCardNumber;
    @FindBy(how = How.ID, using = "firstname-error")
    protected WebElement weFirstnameError;
    @FindBy(how = How.ID, using = "email_address-error")
    protected WebElement weEmailError;
    @FindBy(how = How.ID, using = "password-error")
    protected WebElement wePasswordError;
    @FindBy(how = How.ID, using = "password-strength-meter-label")
    protected WebElement wePasswordStrength;
    @FindBy(how = How.ID, using = "password-confirmation-error")
    protected WebElement wePasswordconfirmError;
    @FindBy(how = How.ID,using = "prefix")
    protected WebElement weTitle;
    @FindBy(how = How.ID,using = "nickname")
    protected WebElement weNickname;
    @FindBy(how = How.ID,using = "type_of_id")
    protected WebElement weTypeOfIdentityCard;
    @FindBy(how = How.ID, using = "firstname")
    protected WebElement weFullName;
    @FindBy(how = How.ID, using = "lastname")
    protected WebElement weLastName;
    @FindBy(how = How.ID, using = "firstname-error")
    protected WebElement weFullnameError;
    @FindBy(how = How.ID, using = "nickname-error")
    protected WebElement weNicknameError;
    @FindBy(how = How.ID, using = "lastname-error")
    protected WebElement weLastnameError;
    @FindBy(how = How.ID, using = "type_of_id-error")
    protected WebElement weTypeOfIdentityCardError;
    @FindBy(how = How.ID,using = "id_number-error")
    protected WebElement weIdentityCardNumberError;
    @FindBy(how = How.ID, using = "company")
    protected WebElement weCompany;
    @FindBy(how = How.ID, using = "telephone")
    protected WebElement weTelephone;
    @FindBy(how = How.ID, using = "telephone-error")
    protected WebElement weTelephoneError;
    @FindBy(how = How.ID, using = "street_1")
    protected WebElement weStreet;
    @FindBy(how = How.ID, using = "street_1-error")
    protected WebElement weStreetError;
    @FindBy(how = How.ID, using = "city")
    protected WebElement weCity;
    @FindBy(how = How.ID, using = "city-error")
    protected WebElement weCityError;
    @FindBy(how = How.ID, using = "region_id")
    protected WebElement weState;
    @FindBy(how = How.ID, using = "region_id-error")
    protected WebElement weStateError;
    @FindBy(how = How.ID, using = "zip")
    protected WebElement weZip;
    @FindBy(how = How.ID, using = "zip-error")
    protected WebElement weZipError;
    @FindBy(how = How.ID, using = "address:name_address")
    protected WebElement weAddressName;
    @FindBy(how = How.ID, using = "address:name_address-error")
    protected WebElement weAddressNameError;
    @FindBy(how = How.ID, using = "address:mobile_number")
    protected WebElement weMobileNumber;
    @FindBy(how = How.ID, using = "address:mobile_number-error")
    protected WebElement weMobileNumberError;
    @FindBy(how = How.ID,using = "check_default_shipping")
    protected WebElement weDefaultShippingAddress;
    @FindBy(how = How.CSS,using = "div.messages div.message-error")
    protected WebElement weErrorMsg;
    @FindBy(how = How.ID,using = "marital_status")
    protected WebElement weMaritalStatus;
    @FindBy(how = How.ID,using = "is_subscribed")
    protected WebElement weSubscribeNewsletter;
    @FindBy(how = How.ID,using = "club_sms-check")
    protected WebElement weMessagingService;
    @FindBy(how = How.XPATH,using = "//div[@class='control customer-dob']//button[contains(@class,'ui-datepicker-trigger')]")
    protected WebElement weButtonCalendar;
    @FindBy(how = How.ID,using = "dob-error")
    protected WebElement weDateOfBirthError;
    @FindBy(how = How.ID,using = "race")
    protected WebElement weRace;
    @FindBy(how = How.ID,using = "race-error")
    protected WebElement weRaceError;
    @FindBy(how = How.ID,using = "dob")
    protected WebElement weDateOfBirth;
    @FindBy(how = How.ID,using = "ui-datepicker-div")
    protected WebElement weCalendar;
    @FindBy(how = How.ID,using = "club_newsletter")
    protected WebElement weSubscribeClubNewsletter;
    @FindBy(how = How.ID,using = "ignore_pass_code_verify_method")
    protected WebElement weIgnoreCodeVerify;
    @FindBy(how = How.ID,using = "agree_new_tc")
    protected WebElement weAcceptTermAndConditions;

    public RegisterLocator(WebDriver driver) {
        super(driver);
    }
}
