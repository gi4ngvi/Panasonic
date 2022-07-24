package isobar.panasonic.page.fo.locator.checkout;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PaymentLocator extends SeleniumFactory {
    protected static final String PRODUCT_QTY = "xpath=//strong[text()='%s']//ancestor::div[@class='product-item-details']//span[@class='value']";
    protected static final String PRODUCT_PRICE_INC_TAX = "xpath=//strong[text()='%s']//ancestor::div[@class='product-item-details']//span[@class='price-including-tax']//span[@class='price']";
    protected static final String PRODUCT_PRICE_EXC_TAX = "xpath=//strong[text()='%s']//ancestor::div[@class='product-item-details']//span[@class='price-excluding-tax']//span[@class='price']";
    protected static final String ORDER_TABLE = "xpath=//table[@class='data table table-totals']";

    @FindBy(how = How.ID, using = "cybersource")
    protected WebElement weCybersource;
    @FindBy(how = How.ID, using = "cashondelivery")
    protected WebElement weCOD;
    @FindBy(how = How.ID, using = "checkmo")
    protected WebElement weCMO;
    @FindBy(how = How.ID, using = "onepay")
    protected WebElement weOnePAYDomestic;
    @FindBy(how = How.ID, using = "onepayinternational")
    protected WebElement weOnePAYInternational;
    @FindBy(how = How.CSS, using = ".payment-method._active input[name*='agreement']")
    protected WebElement weTermAgreement;
    @FindBy(how = How.CSS, using = ".payment-method._active button.action.checkout")
    protected WebElement wePlaceOrder;
    @FindBy(how = How.CSS, using = ".payment-method._active select[name='billing_address_id']")
    protected WebElement weBillingAddress;
    @FindBy(how = How.XPATH, using = "//div[contains(@class,'_active')]//div[contains(@data-bind,'afterRenderStorePickup')]//div[contains(@data-bind,'showFormForStorePickup')]")
    protected WebElement weBillingAddressDetails;
    @FindBy(how = How.CSS, using = ".payment-method._active button.action-update")
    protected WebElement weUpdateAddress;
    @FindBy(how = How.CSS, using = ".payment-method._active input[name='billing-address-same-as-shipping']")
    protected WebElement weBillingAddressSameAsShipping;
    @FindBy(how = How.CSS, using = ".block.items-in-cart>div.title")
    protected WebElement weMiniCart;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals sub']//span[@class='price']")
    protected WebElement weSubtotal;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals shipping incl']//span[@class='price']")
    protected WebElement weShippingFee;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals shipping excl']//span[@class='price']")
    protected WebElement weShippingFeeWithoutTax;
    @FindBy(how = How.XPATH, using = "//tr[@class='totals-tax']//span[@class='price']")
    protected WebElement weTax;
    @FindBy(how = How.XPATH, using = "//tr[@class='grand totals']//span[@class='price']")
    protected WebElement weGrandtotal;
    @FindBy(how = How.CSS, using = ".ship-to .shipping-information-content")
    protected WebElement weShipAddress;
    @FindBy(how = How.CSS, using = ".ship-via .shipping-information-content")
    protected WebElement weShipMethod;
    @FindBy(how = How.CSS, using = "input[name='payment[cc_number]']")
    protected WebElement weCardNumber;
    @FindBy(how = How.CSS, using = "select[name='payment[cc_exp_month]']")
    protected WebElement weCardMonth;
    @FindBy(how = How.CSS, using = "select[name='payment[cc_exp_year]']")
    protected WebElement weCardYear;
    @FindBy(how = How.CSS, using = "input[name='payment[cc_cid]']")
    protected WebElement weCardCvv;
    @FindBy(how = How.ID, using = "cybersource_cc_number-error")
    protected WebElement weCardNumberError;
    @FindBy(how = How.ID, using = "cybersource_expiration-error")
    protected WebElement weCardExpirationError;
    @FindBy(how = How.ID, using = "cybersource_expiration_yr-error")
    protected WebElement weCardExpiratioYearError;
    @FindBy(how = How.ID, using = "cybersource_cc_cid-error")
    protected WebElement weCardCvvError;
    @FindBy(how = How.CSS,using = "div.opc-block-summary")
    protected WebElement weBlockOrderSummary;
    @FindBy(how = How.XPATH,using = "//form[@id='discount-form']//button[contains(@class,'action')]")
    protected WebElement weSubmitCoupon;
    @FindBy(how = How.ID,using = "discount-code")
    protected WebElement weCouponCode;
    @FindBy(how = How.CSS,using = "div.messages div.message.message-error div")
    protected WebElement weCouponMessageError;
    @FindBy(how = How.CSS,using = "div.messages div.message.message-success div")
    protected WebElement weCouponMessageSuccess;
    @FindBy(how = How.CSS,using = "div .discount-code div.payment-option-title")
    protected WebElement weExpandCoupon;
    @FindBy(how = How.CSS,using = "tr.totals.discount span.title")
    protected WebElement weCouponTitleSummary;
    @FindBy(how = How.CSS,using = "tr.totals.discount span.price")
    protected WebElement weCouponAmountSummary;
    @FindBy(how = How.ID,using = "cashondelivery")
    protected WebElement weCashCheque;
    @FindBy(how = How.ID,using = "staff_installment")
    protected WebElement weStaffInstallment;
    @FindBy(how = How.NAME,using = "payment[installment_package]")
    protected WebElement weInstallmentPackage;
    @FindBy(how = How.CSS, using = "div.opc-block-summary div.loading-mask")
    protected WebElement weOrderSummaryLoadingMask;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='firstname']")
    protected WebElement weBillingFirstName;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'firstname')]//div[@class='field-error']//span")
    protected WebElement weBillingFirstNameError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='lastname']")
    protected WebElement weBillingLastName;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'lastname')]//div[@class='field-error']//span")
    protected WebElement weBillingLastNameError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='company']")
    protected WebElement weBillingCompany;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//select[@name='region_id']")
    protected WebElement weBillingState;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'region_id')]//div[@class='field-error']//span")
    protected WebElement weBillingStateError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='street[0]']")
    protected WebElement weBillingStreet;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'street.0')]//div[@class='field-error']//span")
    protected WebElement weBillingStreetError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='telephone']")
    protected WebElement weBillingTelephone;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'telephone')]//div[@class='field-error']//span")
    protected WebElement weBillingTelephoneError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='postcode']")
    protected WebElement weBillingZip;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'postcode')]//div[@class='field-error']//span")
    protected WebElement weBillingZipError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='firstname']")
    protected WebElement weBillingFullName;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'firstname')]//div[@class='field-error']//span")
    protected WebElement weBillingFullNameError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//select[@name='custom_attributes[city_id]']")
    protected WebElement weBillingCity;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'city')]//div[@class='field-error']//span")
    protected WebElement weBillingCityError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//input[contains(@id,'billing-save-in-address-book')]")
    protected WebElement weSaveInAddressBook;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//select[@name='region_id']")
    protected WebElement weBillingCityVN;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//select[@name='district_id']")
    protected WebElement weBillingDistrict;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//select[@name='ward_id']")
    protected WebElement weBillingWard;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'region_id')]//div[@class='field-error']//span")
    protected WebElement weBillingCityVNError;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'district')]//div[@class='field-error']//span")
    protected WebElement weBillingDistrictError;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'ward')]//div[@class='field-error']//span")
    protected WebElement weBillingWardError;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='city']")
    protected WebElement weBillingCityInput;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='custom_attributes[name_address]']")
    protected WebElement weBillingAddressName;
    @FindBy(how = How.XPATH, using = "//div[@class='billing-address-form' and @style='display: block;']//fieldset[contains(@id,'billing-new-address-form')]//input[@name='custom_attributes[mobile_number]']")
    protected WebElement weBillingMobileNumber;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'name_address')]//div[@class='field-error']//span")
    protected WebElement weBillingAddressNameError;
    @FindBy(how = How.XPATH, using = "//div[contains(@name,'mobile_number')]//div[@class='field-error']//span")
    protected WebElement weBillingMobileNumberError;
    @FindBy(how = How.XPATH, using = "//tr[contains(@class,'interest')]//span[@class='price']")
    protected WebElement weInterest;

    public PaymentLocator(WebDriver driver) {
        super(driver);
    }
}
