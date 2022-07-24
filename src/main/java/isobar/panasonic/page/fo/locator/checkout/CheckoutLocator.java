package isobar.panasonic.page.fo.locator.checkout;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CheckoutLocator extends SeleniumFactory {
    protected static final String PRODUCT_QTY = "xpath=//strong[text()='%s']//ancestor::div[@class='product-item-details']//span[@class='value']";
    protected static final String PRODUCT_PRICE_INC_TAX = "xpath=//strong[text()='%s']//ancestor::div[@class='product-item-details']//span[@class='price-including-tax']//span[@class='price']";
    protected static final String PRODUCT_PRICE_EXC_TAX = "xpath=//strong[text()='%s']//ancestor::div[@class='product-item-details']//span[@class='price-excluding-tax']//span[@class='price']";
    protected static final String SHIPING_METHOD_TITLE = "xpath=//td[contains(.,'%s')]/ancestor::tr//input";
    protected static final String STORE_PICKUP_OPTION = "xpath=//label[contains(.,'%s')]/preceding-sibling::input";
    protected static final String ADDRESS_LIST = "xpath=//div[@class='shipping-address-items']";
    protected static final String ADDRESS_SELECTED = ADDRESS_LIST + "//div[@class='shipping-address-item selected-item']";
    protected static final String ADDRESS_BOX = ADDRESS_LIST + "//div[contains(.,'%s')]//button[contains(@class,'action-select')]";
    protected static final String SHIPPING_FEE_INC_TAX = "xpath=//td[normalize-space(text())='%s']/ancestor::tr//span[@data-bind='text: getFormattedPrice(method.price_incl_tax)']";
    protected static final String SHIPPING_FEE_EXCL_TAX = "xpath=//td[normalize-space(text())='%s']/ancestor::tr//span[@data-bind='text: getFormattedPrice(method.price_excl_tax)']";
    @FindBy(how = How.CSS, using = ".opc-review-actions>button")
    protected WebElement wePlaceOrder;
    @FindBy(how = How.CSS, using = ".button-disabled")
    protected WebElement wePlaceOrderDisabled;
    @FindBy(how = How.CSS, using = "input[name='firstname']")
    protected WebElement weName;
    @FindBy(how = How.CSS, using = "input[name='company']")
    protected WebElement weCompany;
    @FindBy(how = How.CSS, using = "select[name='region_id']")
    protected WebElement weCityVN;
    @FindBy(how = How.CSS, using = "select[name*='district_id']")
    protected WebElement weDistrict;
    @FindBy(how = How.CSS, using = "select[name*='ward_id']")
    protected WebElement weWard;
    @FindBy(how = How.CSS, using = "input[name='street[0]']")
    protected WebElement weStreet;
    @FindBy(how = How.CSS, using = "input[name='telephone']")
    protected WebElement weTelephone;
    @FindBy(how = How.ID, using = "shipping-save-in-address-book")
    protected WebElement weTickSaveAddress;
    @FindBy(how = How.CSS, using = ".block.items-in-cart>div.title")
    protected WebElement weMiniCart;
    @FindBy(how = How.CSS, using = ".action.action-show-popup")
    protected WebElement weNewAddress;
    @FindBy(how = How.CSS, using = ".action.primary.action-save-address")
    protected WebElement weSaveAddress;
    @FindBy(how = How.CSS, using = ".action.secondary.action-hide-popup")
    protected WebElement weCancelAddress;
    @FindBy(how = How.CSS, using = ".button.action.continue.primary")
    protected WebElement weNext;
    @FindBy(how = How.CSS, using = "div.opc-block-summary div.loading-mask")
    protected WebElement weOrderSummaryLoadingMask;
    @FindBy(how = How.NAME,using = "region_id")
    protected WebElement weState;
    @FindBy(how = How.CSS,using = "input[name='city']")
    protected WebElement weCityMY;
    @FindBy(how = How.NAME,using = "firstname")
    protected WebElement weFirstName;
    @FindBy(how = How.NAME,using = "lastname")
    protected WebElement weLastName;
    @FindBy(how = How.NAME,using = "postcode")
    protected WebElement weZip;
    @FindBy(how = How.NAME,using = "custom_attributes[name_address]")
    protected WebElement weAddressName;
    @FindBy(how = How.NAME,using = "custom_attributes[mobile_number]")
    protected WebElement weMobileNumber;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.custom_attributes.name_address']//div[@class='field-error']//span")
    protected WebElement weAddressNameError;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.custom_attributes.mobile_number']//div[@class='field-error']//span")
    protected WebElement weMobileNumberError;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.firstname']//div[@class='field-error']//span")
    protected WebElement weFirstNameError;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.lastname']//div[@class='field-error']//span")
    protected WebElement weLastNameError;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.region_id']//div[@class='field-error']//span")
    protected WebElement weStateError;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.postcode']//div[@class='field-error']//span")
    protected WebElement weZipError;
    @FindBy(how = How.XPATH,using = "//div[contains(@name,'city')]//div[@class='field-error']//span")
    protected WebElement weCityError;
    @FindBy(how = How.XPATH, using = "//div[@name='shippingAddress.telephone']//div[@class='field-error']//span")
    protected WebElement weTelephoneError;
    @FindBy(how = How.XPATH, using = "//div[@name='shippingAddress.street.0']//div[@class='field-error']//span")
    protected WebElement weStreetError;
    @FindBy(how = How.ID,using = "opc-new-shipping-address")
    protected WebElement weFormShippingAddress;
    @FindBy(how = How.NAME,using = "custom_attributes[city_id]")
    protected WebElement weCity;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.firstname']//div[@class='field-error']//span")
    protected WebElement weFullNameError;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.custom_attributes.district']//div[@class='field-error']//span")
    protected WebElement weDistrictError;
    @FindBy(how = How.XPATH,using = "//div[@name='shippingAddress.custom_attributes.ward']//div[@class='field-error']//span")
    protected WebElement weWardError;

    public CheckoutLocator(WebDriver driver) {
        super(driver);
    }
}
