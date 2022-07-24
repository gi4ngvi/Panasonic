package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WarrantyLocator extends SeleniumFactory{
    @FindBy(how = How.ID,using = "categories")
    protected WebElement weProductCategory;
    @FindBy(how = How.CSS,using = "label.label-name-choose-file")
    protected WebElement weChooseFile;
    @FindBy(how = How.ID,using = "model-product")
    protected WebElement weModelNumber;
    @FindBy(how = How.ID,using = "warranty-card-number")
    protected WebElement weWarrantyCardNumber;
    @FindBy(how = How.ID,using = "serial")
    protected WebElement weSerialNumber ;
    @FindBy(how = How.ID,using = "purchase-date")
    protected WebElement weDateOfPurchase;
    @FindBy(how = How.ID,using = "agreement")
    protected WebElement weTermsAndConditions;
    @FindBy(how = How.XPATH,using = "//form[@id='form-e-warranty']//button[@id='submitFormWarranty']")
    protected WebElement weSubmit;
    @FindBy(how = How.ID,using = "purchase-date-error")
    protected WebElement weDateOfPurchaseError;
    @FindBy(how = How.ID,using = "file-document-error")
    protected WebElement weSupportedDocumentError;
    @FindBy(how = How.ID,using = "categories-error")
    protected WebElement weProductCategoryError;
    @FindBy(how = How.ID,using = "model-product-error")
    protected WebElement weModelNumberError;
    @FindBy(how = How.ID,using = "serial-error")
    protected WebElement weSerialNumberError;
    @FindBy(how = How.ID,using = "ui-datepicker-div")
    protected WebElement weCalendar;
    @FindBy(how = How.XPATH,using = "//form[@id='form-e-warranty']//button[contains(@class,'ui-datepicker-trigger')]")
    protected WebElement weButtonCalendar;
    @FindBy(how = How.CSS,using = "label.label-choose-file")
    protected WebElement weFileUploaded;
    @FindBy(how = How.ID,using = "warranty-card-number-error")
    protected WebElement weCardNoError;
    @FindBy(how = How.ID,using = "agreement-error")
    protected WebElement weAgreementError;
    @FindBy(how = How.CSS, using = "#file-document")
    protected WebElement weInputFileDocument;
    @FindBy(how = How.CSS, using = "div.message-error div")
    protected WebElement weSupportedDocumentTypeError;

    public WarrantyLocator(WebDriver driver) {
        super(driver);
    }
}
