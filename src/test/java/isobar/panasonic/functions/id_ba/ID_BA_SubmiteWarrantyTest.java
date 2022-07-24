package isobar.panasonic.functions.id_ba;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.Warranty;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.RewardPointsPage;
import isobar.panasonic.page.fo.method.account.WarrantyHistoryPage;
import isobar.panasonic.page.fo.method.account.WarrantyPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import isobar.panasonic.utility.PriceUtility;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ID_BA_SubmiteWarrantyTest extends SeleniumTest {
    CustomerInformation cus;
    Warranty warranty, warrantyMissProductCategory, warrantyMissModelNumber, warrantyMissSerialNumber, warrantyMissDateOfPurchase, warrantyMissSupportedDocument, warrantyMissSupportedDocumentInvalidFormat;
    WarrantyPage warrantyPage;
    AccountInformationPage accountInformationPage;
    WarrantyHistoryPage warrantyHistoryPage;
    RewardPointsPage rewardPointsPage;
    String currentPointBalanceStr;
    int currentPointBalanceNumber;
    String MSG_REQUIRED, MSG_SUCCESS, MSG_INVALID_FILE_TYPE;

    @Override
    protected void preCondition() {
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
        MSG_REQUIRED = "Ini adalah kolom yang harus diisi.";
        MSG_SUCCESS = "Registrasi garansi berhasil.";
        MSG_INVALID_FILE_TYPE = "Garansi produk sudah terdaftar.";
    }

    @Test
    public void prepare(){
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        rewardPointsPage = accountInformationPage.openRewardPointsPage();
        currentPointBalanceStr = rewardPointsPage.getRewardPointsBalance();
    }

    @Test(dependsOnMethods = "prepare")
    public void submitWarrantyWithEmptySupportedDocument() {
        accountInformationPage = headerPage.gotoAccountInfo();
        warrantyPage = accountInformationPage.gotoWarrantyPage();
        warrantyPage.submitWarranty(warrantyMissSupportedDocument);
        Assert.assertEquals(warrantyPage.getSupportedDocumentError(),MSG_REQUIRED );
    }

    @Test(dependsOnMethods = "prepare")
    public void submitWarrantyWithInvalidSupportedDocument() {
        warrantyPage = accountInformationPage.gotoWarrantyPage();
        warrantyPage.submitWarranty(warrantyMissSupportedDocumentInvalidFormat);
        Panasonic.assertEquals(warrantyPage.getSupportedDocumentTypeError(),MSG_INVALID_FILE_TYPE);
    }

    @Test(dependsOnMethods = "submitWarrantyWithInvalidSupportedDocument")
    public void submitWarrantyWithEmptyProductCategory () {
        warrantyPage.submitWarranty(warrantyMissProductCategory);
        Assert.assertEquals(warrantyPage.getProductCategoryError(),MSG_REQUIRED );
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptyProductCategory")
    public void submitWarrantyWithEmptyModelNumber() {
        warrantyPage.submitWarranty(warrantyMissModelNumber);
        Assert.assertEquals(warrantyPage.getModelNumberError(),MSG_REQUIRED );
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptyModelNumber")
    public void submitWarrantyWithEmptySerialNumber () {
        warrantyPage.submitWarranty(warrantyMissSerialNumber);
        Assert.assertEquals(warrantyPage.getSerialNumberError(),MSG_REQUIRED );
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptySerialNumber")
    public void submitWarrantyWithEmptyDateOfPurchase() {
        warrantyPage.submitWarranty(warrantyMissDateOfPurchase);
        Assert.assertEquals(warrantyPage.getDateOfPurchaseError(),MSG_REQUIRED );
    }

    @Test(dependsOnMethods = "submitWarrantyWithEmptyDateOfPurchase")
    public void submitWarrantyWithValidInfo(){
        accountInformationPage = warrantyPage.submitWarranty(warranty);
        Assert.assertEquals(headerPage.getSuccessMessage(), MSG_SUCCESS);
        warrantyHistoryPage = accountInformationPage.gotoWarrantyHistoryPage();
        warrantyHistoryPage.viewWarranty(warranty);
        Assert.assertEquals(warrantyHistoryPage.getProductCategory(), warranty.getProductCategory());
        Assert.assertEquals(warrantyHistoryPage.getProductSKU(), warranty.getProductSKU());
        Assert.assertEquals(warrantyHistoryPage.getSerialNo(), warranty.getSerialNumber());
        rewardPointsPage = accountInformationPage.openRewardPointsPage();
        currentPointBalanceNumber = PriceUtility.convertPriceStringToNumber(currentPointBalanceStr) + warranty.getRewardPointBonus();
        Assert.assertEquals(rewardPointsPage.getRewardPointsBalance(), PriceUtility.convertPriceToString(currentPointBalanceNumber));
    }
}
