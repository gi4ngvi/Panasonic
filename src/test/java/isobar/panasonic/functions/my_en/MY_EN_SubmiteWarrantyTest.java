package isobar.panasonic.functions.my_en;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.Warranty;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.WarrantyPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_SubmiteWarrantyTest extends SeleniumTest {
    private CustomerInformation cus;
    private Warranty warranty;
    private WarrantyPage warrantyPage;
    private AccountInformationPage accountInformationPage;
    private String MSG_REQUIRED, MSG_AGREE_CONDITIONS_REQUIRED;

    @Override
    protected void preCondition() {
        cus = DataTest.getDefaultCustomerTest();
        warranty = DataTest.getWarranty();
        warranty.setWarrantyCardNumber(RUN_ID);
        warranty.setSerialNumber(RUN_ID);
        MSG_REQUIRED = "This is a required field.";
        MSG_AGREE_CONDITIONS_REQUIRED = "Please agree to the terms & conditions and privacy.";
    }

    @Test
    public void submitWarrantyWithNullValue(){
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        warrantyPage = accountInformationPage.gotoWarrantyPage();
        warrantyPage.submit();
        Assert.assertEquals(warrantyPage.getProductCategoryError(), MSG_REQUIRED);
        Assert.assertEquals(warrantyPage.getModelNumberError(), MSG_REQUIRED);
        Assert.assertEquals(warrantyPage.getSerialNumberError(), MSG_REQUIRED);
        Assert.assertEquals(warrantyPage.getDateOfPurchaseError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "submitWarrantyWithNullValue")
    public void submitWarrantyWithNoSelectAgreement(){
        warrantyPage.fillInProductInfo(warranty);
        warrantyPage.agreeTermsAndConditions(false);
        warrantyPage.submit();
        Assert.assertEquals(warrantyPage.getAgreementError(), MSG_AGREE_CONDITIONS_REQUIRED);
    }
}
