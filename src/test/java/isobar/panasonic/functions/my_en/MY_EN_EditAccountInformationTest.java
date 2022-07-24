package isobar.panasonic.functions.my_en;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.EditAccountPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_EditAccountInformationTest extends SeleniumTest {
    private CustomerInformation cusEdit, cusEmpty, cusInvalid;
    private AccountInformationPage accountInformationPage;
    private EditAccountPage editAccountPage;
    private StringUtility stringUtility;
    private String MSG_REQUIRED, MSG_ACCOUNT_SAVE_SUCCESS, MSG_DATE_INVALID;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cusEdit = DataTest.getCustomer2();
        cusEdit.setNickName(stringUtility.getRandomCharacter(10));
        cusEdit.setFirstName(stringUtility.getRandomCharacter(5));
        cusEdit.setLastName(stringUtility.getRandomCharacter(5));
        cusEdit.setIdentityCardNumber(RUN_ID);
        cusEdit.setDateOfBirth("1990-01-01");
        cusEmpty = DataTest.getCustomer2();
        cusEmpty.setTypeOfIdentityCard("-- Choose --");
        cusEmpty.setIdentityCardNumber("");
        cusEmpty.setNickName("");
        cusEmpty.setDateOfBirth("");
        cusEmpty.setFirstName("");
        cusEmpty.setLastName("");
        cusEmpty.setRace("");
        cusEmpty.setMaritalStatus("");
        cusInvalid = DataTest.getCustomer2();
        cusInvalid.setDateOfBirth("111111111");
        MSG_REQUIRED = "This is a required field.";
        MSG_ACCOUNT_SAVE_SUCCESS = "You saved the account information.";
        MSG_DATE_INVALID = "Invalid date";
    }

    @Test
    public void editAccountWithRequiredFieldsNullInfo(){
        headerPage.login(cusEdit);
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.fillInAccountInformation(cusEmpty);
        editAccountPage.saveAccount();
        Assert.assertEquals(editAccountPage.getFirstNameError(), MSG_REQUIRED);
        Assert.assertEquals(editAccountPage.getLastNameError(), MSG_REQUIRED);
        Assert.assertEquals(editAccountPage.getDateOfBirthError(), MSG_REQUIRED);
        Assert.assertEquals(editAccountPage.getTypeOfIdentityCardError(), MSG_REQUIRED);
        Assert.assertEquals(editAccountPage.getIdentityCardNumberError(), MSG_REQUIRED);
        Assert.assertEquals(editAccountPage.getNicknameError(), MSG_REQUIRED);
        Assert.assertEquals(editAccountPage.getRaceError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "editAccountWithRequiredFieldsNullInfo")
    public void editAccountWithInvalidInfo(){
        editAccountPage.fillInAccountInformation(cusInvalid);
        editAccountPage.saveAccount();
        Assert.assertEquals(headerPage.getErrorMessage(), MSG_DATE_INVALID);
    }

    @Test(dependsOnMethods = "editAccountWithInvalidInfo")
    public void editAccountWithValidInfo(){
        editAccountPage.fillInAccountInformation(cusEdit);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_ACCOUNT_SAVE_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cusEdit));
    }
}
