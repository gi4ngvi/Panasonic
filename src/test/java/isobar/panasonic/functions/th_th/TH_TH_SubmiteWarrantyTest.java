package isobar.panasonic.functions.th_th;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.Warranty;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.WarrantyHistoryPage;
import isobar.panasonic.page.fo.method.account.WarrantyPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TH_TH_SubmiteWarrantyTest extends SeleniumTest {
    CustomerInformation cus;
    Warranty warranty, warrantyMissProductCategory, warrantyMissModelNumber, warrantyMissSerialNumber, warrantyMissDateOfPurchase, warrantyMissSupportedDocument,
            warrantyMissSupportedDocumentInvalidFormat, warrantyMissCardNo, warrantyInvalidCardNo;
    WarrantyPage warrantyPage;
    AccountInformationPage accountInformationPage;
    WarrantyHistoryPage warrantyHistoryPage;
    String MSG_REQUIRED, MSG_SUCCESS, MSG_CARDNO_INVALID;
    StringUtility stringUtility;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getDefaultCustomerTest();
        warranty = DataTest.getWarranty();
        warranty.setWarrantyCardNumber(RUN_ID);
        warranty.setSerialNumber(RUN_ID);
        warrantyMissProductCategory = DataTest.getWarranty();
        warrantyMissProductCategory.setProductCategory("----Select-----");
        warrantyMissProductCategory.setProductName("----Select-----");
        warrantyMissModelNumber = DataTest.getWarranty();
        warrantyMissModelNumber.setProductName("----Select-----");
        warrantyMissSerialNumber = DataTest.getWarranty();
        warrantyMissSerialNumber.setSerialNumber("");
        warrantyMissDateOfPurchase = DataTest.getWarranty();
        warrantyMissDateOfPurchase.setDateOfPurchase("");
        warrantyMissSupportedDocument = DataTest.getWarranty();
        warrantyMissSupportedDocument.setSupportedDocument("");
        warrantyMissSupportedDocumentInvalidFormat = DataTest.getWarranty();
        warrantyMissSupportedDocumentInvalidFormat.setSupportedDocument(System.getProperty("user.dir") + "\\documentInvalid.txt");
        warrantyMissCardNo = DataTest.getWarranty2();
        warrantyMissCardNo.setWarrantyCardNumber("");
        warrantyInvalidCardNo = DataTest.getWarranty2();
        warrantyInvalidCardNo.setWarrantyCardNumber(RUN_ID + stringUtility.getRandomCharacter(10));
        warrantyInvalidCardNo.setSerialNumber(RUN_ID + stringUtility.getRandomCharacter(10));
        MSG_REQUIRED = "นี่คือข้อมูลที่จำเป็น.";
        MSG_SUCCESS = "ลงทะเบียนการรับประกันสำเร็จแล้ว";
        MSG_CARDNO_INVALID ="ผลิตภัณฑ์รุ่นนี้อยู่ในแคมเปญขยายระยะเวลาประกัน แต่ไม่พบข้อมูลในระบบ";
    }

    @Test
    public void submitWarrantyWithEmptySupportedDocument() {
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        warrantyPage = accountInformationPage.gotoWarrantyPage();
        warrantyPage.submitWarranty(warrantyMissSupportedDocument);
        Assert.assertEquals(warrantyPage.getSupportedDocumentError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptySupportedDocument")
    public void submitWarrantyWithInvalidSupportedDocument() {
        warrantyPage.submitWarranty(warrantyMissSupportedDocumentInvalidFormat);
        Assert.assertEquals(warrantyPage.getSupportedDocumentError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithInvalidSupportedDocument")
    public void submitWarrantyWithEmptyProductCategory() {
        warrantyPage.submitWarranty(warrantyMissProductCategory);
        Assert.assertEquals(warrantyPage.getProductCategoryError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptyProductCategory")
    public void submitWarrantyWithEmptyModelNumber() {
        warrantyPage.submitWarranty(warrantyMissModelNumber);
        Assert.assertEquals(warrantyPage.getModelNumberError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptyModelNumber")
    public void submitWarrantyWithEmptyCardNo(){
        warrantyPage.submitWarranty(warrantyMissCardNo);
        Assert.assertEquals(warrantyPage.getCardNoError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptyCardNo")
    public void submitWarrantyWithInvalidCardNo(){
        warrantyPage.submitWarranty(warrantyInvalidCardNo);
        Panasonic.assertEquals(headerPage.getErrorMessage(), MSG_CARDNO_INVALID);
    }

    @Test(dependsOnMethods = "submitWarrantyWithInvalidCardNo")
    public void submitWarrantyWithEmptySerialNumber() {
        warrantyPage.submitWarranty(warrantyMissSerialNumber);
        Assert.assertEquals(warrantyPage.getSerialNumberError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptySerialNumber")
    public void submitWarrantyWithEmptyDateOfPurchase() {
        warrantyPage.submitWarranty(warrantyMissDateOfPurchase);
        Assert.assertEquals(warrantyPage.getDateOfPurchaseError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptyDateOfPurchase")
    public void submitWarrantyWithValidInfo() {
        accountInformationPage = warrantyPage.submitWarranty(warranty);
        Assert.assertEquals(headerPage.getSuccessMessage(), MSG_SUCCESS);
        warrantyHistoryPage = accountInformationPage.gotoWarrantyHistoryPage();
        warrantyHistoryPage.viewWarranty(warranty);
        Assert.assertEquals(warrantyHistoryPage.getProductCategory(), warranty.getProductCategory());
        Assert.assertEquals(warrantyHistoryPage.getProductSKU(), warranty.getProductSKU());
        Assert.assertEquals(warrantyHistoryPage.getSerialNo(), warranty.getSerialNumber());
    }
}