package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.entity.data.Country;
import isobar.panasonic.entity.data.Warranty;
import isobar.panasonic.page.fo.locator.account.WarrantyLocator;
import isobar.panasonic.utility.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class WarrantyPage extends WarrantyLocator {
    TextTransfer textTransfer;
    Calendar calendar;
    HandleLoading handleLoading;

    public WarrantyPage(WebDriver driver) {
        super(driver);
        textTransfer = new TextTransfer();
        calendar = new Calendar(driver);
        handleLoading = new HandleLoading(driver);
    }

    private void openFileSupportedDocument() {
        waitUtility.waitUntilToBeClickAble(weChooseFile);
        weChooseFile.click();
        actionUtility.sleep(2000);
    }

    private void enterFileSupportedDocument(String pathFileSupportedDocument) {
        weInputFileDocument.sendKeys(pathFileSupportedDocument);
        actionUtility.sleep(2000);
    }

    public AccountInformationPage submitWarranty(Warranty warranty) {
        fillInProductInfo(warranty);
        agreeTermsAndConditions(true);
        submit();
        Panasonic.checkStoreView(driver);
        return new AccountInformationPage(driver);
    }

    private void uploadFileSupportedDocument(String supportDocumentPath) {
        String fileCurrent, fileNameUpload;
        fileNameUpload = new File(supportDocumentPath).getName();
        fileCurrent = weFileUploaded.getText().trim();
        if (!supportDocumentPath.equals("") && !fileCurrent.equals(fileNameUpload)) {
            enterFileSupportedDocument(supportDocumentPath);
        }
    }

    private void setProductCategory(String productCategory) {
        waitUtility.waitUntilVisibilityOf(weProductCategory);
        new Select(weProductCategory).selectByVisibleText(productCategory);
        handleLoading.handleLoadingMask(3);
    }

    private void setModelNumber(String modelNumber) {
        new Select(weModelNumber).selectByVisibleText(modelNumber);
    }

    private void setWarrantyCardNumber(String warrantyCardNumber) {
        weWarrantyCardNumber.clear();
        weWarrantyCardNumber.sendKeys(warrantyCardNumber);
    }

    private void setSerialNumber(String serialNumber) {
        weSerialNumber.clear();
        weSerialNumber.sendKeys(serialNumber);
    }

    public void agreeTermsAndConditions(boolean status) {
        if (weTermsAndConditions.isSelected() != status) {
            weTermsAndConditions.click();
        }
    }

    private void setDateOfPurchase(String date) {
        if (date.equals("")) {
            weDateOfPurchase.clear();
        } else {
            expandCalendar(true);
            actionUtility.sleep(500);
            calendar.selectDate_Select(date);
            waitUtility.waitForValueOfAttributeContains(weCalendar, "style", "display: none;");
        }
        expandCalendar(false);
    }

    private void expandCalendar(boolean status) {
        try {
            if (weCalendar.isDisplayed() != status) {
                weButtonCalendar.click();
            }
        } catch (NoSuchElementException e) {
        }
    }

    public void fillInProductInfo(Warranty warranty) {
        if (DataTest.getCountry() == Country.MY) {
            fillInProductInfoMY(warranty);
        } else {
            fillInProductInfoTHID(warranty);
        }
    }

    private void fillInProductInfoTHID(Warranty warranty) {
        setProductCategory(warranty.getProductCategory());
        setModelNumber(warranty.getProductName());
        setWarrantyCardNumber(warranty.getWarrantyCardNumber());
        setSerialNumber(warranty.getSerialNumber());
        setDateOfPurchase(warranty.getDateOfPurchase());
        uploadFileSupportedDocument(warranty.getSupportedDocument());
    }

    private void fillInProductInfoMY(Warranty warranty) {
        setProductCategory(warranty.getProductCategory());
        setModelNumber(warranty.getProductName());
        setSerialNumber(warranty.getSerialNumber());
        setDateOfPurchase(warranty.getDateOfPurchase());
    }

    public void submit() {
        waitUtility.waitUntilToBeClickAble(weSubmit);
        weSubmit.click();
        waitUtility.waitForPageLoad();
        handleLoading.handleAjaxLoading();
    }

    public String getProductCategoryError() {
        waitUtility.waitUntilVisibilityOf(weProductCategoryError);
        return weProductCategoryError.getText().trim();
    }

    public String getModelNumberError() {
        waitUtility.waitUntilVisibilityOf(weModelNumberError);
        return weModelNumberError.getText().trim();
    }

    public String getSerialNumberError() {
        waitUtility.waitUntilVisibilityOf(weSerialNumberError);
        return weSerialNumberError.getText().trim();
    }

    public String getDateOfPurchaseError() {
        waitUtility.waitUntilVisibilityOf(weDateOfPurchaseError);
        weDateOfPurchaseError.click();
        return weDateOfPurchaseError.getText().trim();
    }

    public String getSupportedDocumentError() {
        waitUtility.waitUntilVisibilityOf(weSupportedDocumentError);
        return weSupportedDocumentError.getText().trim();
    }

    public String getSupportedDocumentTypeError() {
        waitUtility.waitUntilVisibilityOf(weSupportedDocumentTypeError);
        return weSupportedDocumentTypeError.getText().trim();
    }

    public String getCardNoError() {
        waitUtility.waitUntilVisibilityOf(weCardNoError);
        return weCardNoError.getText().trim();
    }

    public String getAgreementError() {
        waitUtility.waitUntilVisibilityOf(weAgreementError);
        return weAgreementError.getText().trim();
    }
}
