package isobar.panasonic.page.bo.locator.customer;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class BOEditCustomerLocator extends SeleniumFactory {
    @FindBy(how = How.CSS, using = "a#tab_address")
    protected WebElement weAddressTab;
    @FindBy(how = How.CSS, using = "#save")
    protected WebElement weSave;
    @FindBy(how = How.CSS, using = "div.loading-mask")
    protected WebElement weAdminLoadingMask;
    @FindBy(how = How.CSS, using = "div[data-component='customer_form.areas'].admin__data-grid-loading-mask")
    protected WebElement weCustomerFormLoading;
    @FindBy(how = How.ID, using = "save_and_continue")
    protected WebElement weSaveAndContinueEdit;
    @FindBy(how = How.CSS, using = "#messages div.message-success div")
    protected WebElement weSuccessMsg;
    @FindBy(how = How.CSS, using = "#messages div.message-error div")
    protected WebElement weErrorMsg;

    /*
     * Address Tab
     */
    protected static final String ADDRESS = "xpath=//address//span[normalize-space(text())='%s']";
    protected static final String ADDRESS_BLOCK = ADDRESS + "/ancestor::li[contains(@class,'address-list-item')]";
    protected static final String DEFAULT_BILLING_ADDRESS = "xpath=(//div[@data-index='default_billing'])[%s]//input";
    protected static final String DEFAULT_SHIPPING_ADDRESS = "xpath=(//div[@data-index='default_shipping'])[%s]//input";
    @FindBy(how = How.CSS, using = ".address-list.ui-tabs-nav .scalable.add")
    protected WebElement weAddNewAddress;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='firstname'] input")
    protected WebElement weFirstName;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='lastname'] input")
    protected WebElement weLastName;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='company'] input")
    protected WebElement weCompany;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='country_id'] select")
    protected WebElement weCountry;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='region_id'] select")
    protected WebElement weState;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='district_id'] select")
    protected WebElement weDistrict;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='ward_id'] select")
    protected WebElement weWard;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='city_id_input'] input")
    protected WebElement weCityInput;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='postcode']  input")
    protected WebElement weZip;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='telephone'] input")
    protected WebElement weTelephone;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='city_id'] select")
    protected WebElement weCitySelect;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='street_0'] input")
    protected WebElement weStreet;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='firstname'] label.admin__field-error")
    protected WebElement weFirstNameError;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='lastname'] label.admin__field-error")
    protected WebElement weLastNameError;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='country_id'] label.admin__field-error")
    protected WebElement weCountryError;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='region_id'] label.admin__field-error")
    protected WebElement weStateError;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='district_id'] label.admin__field-error")
    protected WebElement weDistrictError;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='city_id'] label.admin__field-error")
    protected WebElement weCityError;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='postcode'] label.admin__field-error")
    protected WebElement weZipError;
    @FindBy(how = How.XPATH, using = "//div[@class='address-item-edit' and not(@style='display: none;')]//input[contains(@name,'mobile_number')]/following-sibling::label[@class='admin__field-error']")
    protected WebElement weMobilePhoneError;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='street_0'] label.admin__field-error")
    protected WebElement weStreetError;
    @FindBy(how = How.XPATH, using = "//li[@class='address-list-item ui-state-active']//input[contains(@name,'[default_billing]')]")
    protected WebElement weDefaultBillingAddress;
    @FindBy(how = How.XPATH, using = "//li[@class='address-list-item ui-state-active']//input[contains(@name,'[default_billing]')]/following-sibling::label")
    protected WebElement weDefaultBillingAddressLabel;
    @FindBy(how = How.XPATH, using = "//li[@class='address-list-item ui-state-active']//input[contains(@name,'[default_shipping]')]")
    protected WebElement weDefaultShippingAddress;
    @FindBy(how = How.XPATH, using = "//li[@class='address-list-item ui-state-active']//input[contains(@name,'[default_shipping]')]/following-sibling::label")
    protected WebElement weDefaultShippingAddressLabel;
    @FindBy(how = How.CSS, using = ".address-list>li>address")
    protected List<WebElement> weListAddress;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='name_address'] input")
    protected WebElement weAddressName;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='mobile_number'] input")
    protected WebElement weMobileNumber;
    @FindBy(how = How.CSS, using = ".address-item-edit:not([style*='display']) div[data-index='mobile_number'] label.admin__field-error")
    protected WebElement weMobileNumberError;

    public BOEditCustomerLocator(WebDriver driver) {
        super(driver);
    }
}
