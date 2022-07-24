package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class MyAddressLocator extends SeleniumFactory {
    protected static final String EDIT_ADDRESS = "xpath=//address[contains(.,'%s')]/..//a[@class='action edit']";
    protected static final String DELETE_ADDRESS = "xpath=//address[contains(.,'%s')]/..//a[@class='action delete']";
    protected static final String DELETE_ADDRESS_BUTTON = ".items.addresses>li>div>a.delete";
    @FindBy(how = How.CSS, using = ".items.addresses>li>address")
    protected List<WebElement> weListAddress;
    @FindBy(how = How.CSS, using = ".action-primary.action-accept")
    protected WebElement weButtonAccept;
    @FindBy(how = How.CSS, using = "input[name='telephone']")
    protected WebElement weTelephone;
    @FindBy(how = How.ID, using = "street_1")
    protected WebElement weStreet;
    @FindBy(how = How.ID, using = "region_id")
    protected WebElement weCityVN;
    @FindBy(how = How.ID, using = "company")
    protected WebElement weCompany;
    @FindBy(how = How.ID, using = "district_id")
    protected WebElement weDistrict;
    @FindBy(how = How.ID, using = "ward_id")
    protected WebElement weWard;
    @FindBy(how = How.ID, using = "zip")
    protected WebElement weZip;
    @FindBy(how = How.ID, using = "city_id")
    protected WebElement weCity;
    @FindBy(how = How.ID, using = "region_id")
    protected WebElement weState;
    @FindBy(how = How.ID, using = "firstname")
    protected WebElement weName;
    @FindBy(how = How.ID, using = "firstname")
    protected WebElement weFirstName;
    @FindBy(how = How.ID, using = "lastname")
    protected WebElement weLastName;
    @FindBy(how = How.CSS,using = "button.action.submit.primary")
    protected WebElement weSaveAddress;
    @FindBy(how = How.CSS,using = "div.box.box-address-billing address")
    protected WebElement weDefaultBillingAddress;
    @FindBy(how = How.CSS,using = "div.box.box-address-shipping address")
    protected WebElement weDefaultShippingAddress;
    @FindBy(how = How.CSS, using = ".message-success.success.message>div")
    protected WebElement weSuccessMsg;
    @FindBy(how = How.CSS,using = "div.box-address-billing a.action.edit")
    protected WebElement weChangeBillingAddress;
    @FindBy(how = How.CSS,using = "div.box-address-shipping a.action.edit")
    protected WebElement weChangeShippingAddress;
    @FindBy(how = How.CSS, using = "button.action.primary.add")
    protected WebElement weAddAddress;
    @FindBy(how = How.ID,using = "firstname-error")
    protected WebElement weFirstNameError;
    @FindBy(how = How.ID,using = "lastname-error")
    protected WebElement weLastNameError;
    @FindBy(how = How.ID,using = "region_id-error")
    protected WebElement weStateError;
    @FindBy(how = How.ID,using = "city-error")
    protected WebElement weCityError;
    @FindBy(how = How.ID, using = "street_1-error")
    protected WebElement weStreetError;
    @FindBy(how = How.ID, using = "telephone-error")
    protected WebElement weTelephoneError;
    @FindBy(how = How.CSS, using = ".action-primary.action-accept")
    protected WebElement weAcceptPopup;
    @FindBy(how = How.ID,using = "city")
    protected WebElement weCityEnter;
    @FindBy(how = How.ID, using = "primary_billing")
    protected WebElement wePrimaryBilling;
    @FindBy(how = How.ID, using = "primary_shipping")
    protected WebElement wePrimaryShipping;
    @FindBy(how = How.ID, using = "name_address")
    protected WebElement weAddressName;
    @FindBy(how = How.ID, using = "name_address-error")
    protected WebElement weAddressNameError;
    @FindBy(how = How.ID, using = "mobile_number")
    protected WebElement weMobileNumber;
    @FindBy(how = How.ID, using = "mobile_number-error")
    protected WebElement weMobileNumberError;
    @FindBy(how = How.ID, using = "zip-error")
    protected WebElement weZipError;
    @FindBy(how = How.ID, using = "firstname-error")
    protected WebElement weFullNameError;
    @FindBy(how = How.ID, using = "district-error")
    protected WebElement weDistrictError;
    @FindBy(how = How.ID, using = "ward-error")
    protected WebElement weWardError;
    @FindBy(how = How.ID, using = "region_id-error")
    protected WebElement weCityVNError;

    public MyAddressLocator(WebDriver driver) {
        super(driver);
    }
}
