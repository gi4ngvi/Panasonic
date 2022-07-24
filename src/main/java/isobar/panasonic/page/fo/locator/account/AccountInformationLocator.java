package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class AccountInformationLocator extends SeleniumFactory {
    protected static String EDIT_ADDRESS = "//address[contains(.,'%s')]/..//a[@class='action edit']";
    protected static String DELETE_ADDRESS = "//address[contains(.,'%s')]/..//a[@class='action delete']";
    protected static String REORDER = "//td[text()='%s']/../td[@data-th='Thao tác']//a[@class='action order']";
    protected static String VIEW_ORDER = "xpath=//td[text()= '%s']/ancestor::tr//a[contains(@href,'/sales/order/view/order_id')]";
    @FindBy(how = How.CSS, using = "a[href*='/sales/order/history/']")
    protected WebElement weMyOrder;
    @FindBy(how = How.CSS, using = "#block-collapsible-nav a[href*='/customer/address/']")
    protected WebElement weAddressBook;
    @FindBy(how = How.CSS, using = "a[href*='/customer/account/edit/']")
    protected WebElement weAccountInfomation;
    @FindBy(how = How.CSS, using = "a[href*='/newsletter/manage/']")
    protected WebElement weNewLetterTab;
    @FindBy(how = How.CSS, using = "a[href*='/reward/customer/info/']")
    protected WebElement weRewardPoints;
    @FindBy(how = How.CSS, using = "div.box-information div.box-content p")
    protected WebElement weContactInfo;
    @FindBy(how = How.ID, using = "firstname")
    protected WebElement weFirstname;
    @FindBy(how = How.ID, using = "email")
    protected WebElement weEmail;
    @FindBy(how = How.ID, using = "birth_month")
    protected WebElement weBirthmonth;
    @FindBy(how = How.ID, using = "birth_year")
    protected WebElement weBirthyear;
    @FindBy(how = How.ID, using = "change-password")
    protected WebElement weTickChangePassword;
    @FindBy(how = How.ID, using = "change-email")
    protected WebElement weTickChangeEmail;
    @FindBy(how = How.ID, using = "current-password")
    protected WebElement weCurrPassword;
    @FindBy(how = How.ID, using = "password")
    protected WebElement wePassword;
    @FindBy(how = How.ID, using = "password-confirmation")
    protected WebElement weConfirmPassword;
    @FindBy(how = How.CSS, using = "button[title='Lưu']")
    protected WebElement weSave;
    @FindBy(how = How.CSS, using = "button[title='Lưu địa chỉ']")
    protected WebElement weSaveAddress;
    @FindBy(how = How.CSS, using = ".error-msg li")
    protected WebElement weErrorMsg;
    @FindBy(how = How.CSS, using = ".message-success.success.message>div")
    protected WebElement weSuccessMsg;
    @FindBy(how = How.ID, using = "firstname-error")
    protected WebElement weNameError;
    @FindBy(how = How.ID, using = "email-error")
    protected WebElement weEmailError;
    @FindBy(how = How.ID, using = "current-password-error")
    protected WebElement weCurrPassError;
    @FindBy(how = How.ID, using = "password-error")
    protected WebElement wePassError;
    @FindBy(how = How.ID, using = "password-confirmation-error")
    protected WebElement wePassConfirmError;
    @FindBy(how = How.CSS, using = "button[title='Thêm địa chỉ mới']")
    protected WebElement weAddAddress;
    @FindBy(how = How.CSS, using = "input[name='telephone']")
    protected WebElement weTelephone;
    @FindBy(how = How.ID, using = "street_1")
    protected WebElement weStreet;
    @FindBy(how = How.ID, using = "region_id")
    protected WebElement weCity;
    @FindBy(how = How.ID, using = "company")
    protected WebElement weCompany;
    @FindBy(how = How.ID, using = "district_id")
    protected WebElement weDistrict;
    @FindBy(how = How.ID, using = "ward_id")
    protected WebElement weWard;
    @FindBy(how = How.ID, using = "telephone-error")
    protected WebElement weTelephoneError;
    @FindBy(how = How.ID, using = "region_id-error")
    protected WebElement weCityError;
    @FindBy(how = How.ID, using = "district-error")
    protected WebElement weDistrictError;
    @FindBy(how = How.ID, using = "ward-error")
    protected WebElement weWardError;
    @FindBy(how = How.ID, using = "street_1-error")
    protected WebElement weStreetError;
    @FindBy(how = How.CSS, using = ".action-primary.action-accept")
    protected WebElement weAcceptPopup;
    @FindBy(how = How.CSS, using = ".box.box-address-shipping address")
    protected WebElement weDefaultShipAddress;
    @FindBy(how = How.CSS, using = ".box.box-address-billing address")
    protected WebElement weDefaultBillAddress;
    @FindBy(how = How.CSS, using = ".items.addresses li")
    protected List<WebElement> weAddressList;
    @FindBy(how = How.ID, using = "subscription")
    protected WebElement weSubcribe;
    @FindBy(how = How.CSS,using = "a[href*='/ewarranty/index/history']")
    protected WebElement weWarrantyHistory;
    @FindBy(how = How.CSS,using = "div#block-collapsible-nav a[href*='/ewarranty/index/index']")
    protected WebElement weWarranty;

    public AccountInformationLocator(WebDriver driver) {
        super(driver);
    }
}
