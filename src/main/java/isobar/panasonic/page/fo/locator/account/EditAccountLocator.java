package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditAccountLocator extends SeleniumFactory {
    @FindBy(how = How.ID, using = "firstname")
    protected WebElement weFullName;
    @FindBy(how = How.ID, using = "firstname")
    protected WebElement weFirstName;
    @FindBy(how = How.ID, using = "lastname")
    protected WebElement weLastName;
    @FindBy(how = How.ID, using = "prefix")
    protected WebElement weTitle;
    @FindBy(how = How.ID, using = "change-password")
    protected WebElement weTickChangePassword;
    @FindBy(how = How.ID, using = "change-email")
    protected WebElement weTickChangeEmail;
    @FindBy(how = How.ID, using = "email")
    protected WebElement weEmail;
    @FindBy(how = How.ID, using = "current-password")
    protected WebElement weCurrPassword;
    @FindBy(how = How.ID, using = "password")
    protected WebElement wePassword;
    @FindBy(how = How.ID, using = "password-confirmation")
    protected WebElement weConfirmPassword;
    @FindBy(how = How.ID, using = "nickname")
    protected WebElement weNickname;
    @FindBy(how = How.ID, using = "type_of_id")
    protected WebElement weTypeOfIdentityCard;
    @FindBy(how = How.ID, using = "id_number")
    protected WebElement weIdentityCardNumber;
    @FindBy(how = How.CSS,using = "button.action.save.primary")
    protected WebElement weSaveAccount;
    @FindBy(how = How.ID,using = "dob")
    protected WebElement weDateOfBirth;
    @FindBy(how = How.ID,using = "ui-datepicker-div")
    protected WebElement weCalendar;
    @FindBy(how = How.ID,using = "nickname-error")
    protected WebElement weNicknameError;
    @FindBy(how = How.ID, using = "type_of_id-error")
    protected WebElement weTypeOfIdentityCardError;
    @FindBy(how = How.ID, using = "id_number-error")
    protected WebElement weIdentityCardNumberError;
    @FindBy(how = How.XPATH,using = "//div[@class='control customer-dob']//button[contains(@class,'ui-datepicker-trigger')]")
    protected WebElement weButtonCalendar;
    @FindBy(how = How.ID,using = "club_sms")
    protected WebElement weMessagingService;
    @FindBy(how = How.ID,using = "firstname-error")
    protected WebElement weFirstNameError;
    @FindBy(how = How.ID,using = "lastname-error")
    protected WebElement weLastNameError;
    @FindBy(how = How.ID,using = "dob-error")
    protected WebElement weDateOfBirthError;
    @FindBy(how = How.ID,using = "race-error")
    protected WebElement weRaceError;
    @FindBy(how = How.ID,using = "race")
    protected WebElement weRace;
    @FindBy(how = How.ID,using = "marital_status")
    protected WebElement weMaritalStatus;
    @FindBy(how = How.ID,using = "club_newsletter")
    protected WebElement weSubscribeClubNewsletter;
    @FindBy(how = How.ID,using = "firstname-error")
    protected WebElement weFullNameError;
    @FindBy(how = How.CSS,using = "h1.page-title")
    protected WebElement wePageTitle;

    public EditAccountLocator(WebDriver driver) {
        super(driver);
    }
}
