package isobar.panasonic.functions.vn_en;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.EditAccountPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VN_EN_EditAccountInformationTest extends SeleniumTest {
    private CustomerInformation cus, cus_edit, cusEmpty;
    StringUtility stringUtility;
    private AccountInformationPage accountInformationPage;
    private EditAccountPage editAccountPage;
    private static final String MSG_ACCOUNT_SAVE_SUCCESS = "You saved the account information.";
    private static final String MSG_REQUIRED = "This is a required field.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getCustomer2();
        cus_edit = DataTest.getCustomer2();
        cus_edit.setName(stringUtility.getRandomCharacter(15));
        cusEmpty = new CustomerInformation.Builder().name("").build();
    }

    @Test
    public void changeContactInfoWithEmptyInfo(){
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.fillInAccountInformation(cusEmpty);
        editAccountPage.saveAccount();
        Assert.assertEquals(editAccountPage.getFullNameError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "changeContactInfoWithEmptyInfo")
    public void changeContactInfo() {
        editAccountPage.fillInAccountInformation(cus_edit);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_ACCOUNT_SAVE_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus_edit));
    }
}
