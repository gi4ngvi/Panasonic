package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.EditAccountPage;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditAccountInfomationTest extends SeleniumTest {
    private CustomerInformation cus, cusEdit, cusEmpty;
    private AccountInformationPage accountInformationPage;
    private LoginPopupPage loginPage;
    private EditAccountPage editAccountPage;
    private StringUtility stringUtility;
    private String passwordChange, passwordCurrent;
    private static final String ERROR_REQUIRED_FIELD = "Đây là trường bắt buộc.";
    private static final String EDIT_SUCCESS_MSG = "Bạn đã lưu thông tin tài khoản.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getCustomer4();
        cusEdit = cus.setName(stringUtility.getRandomCharacter(5));
        cusEmpty = new CustomerInformation.Builder()
                .name("")
                .email("")
                .password("")
                .passwordConfirm("")
                .build();
        passwordCurrent = cus.getPassword();
        passwordChange = "Abc@12345";
    }

    @Test
    public void editNullInformation() {
        accountInformationPage = headerPage.login(cus);
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.fillInAccountInformation(cusEmpty);
        editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getNameError(), ERROR_REQUIRED_FIELD);
        editAccountPage.changeEmail("", cus.getPassword());
        editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getEmailError(), ERROR_REQUIRED_FIELD);
        editAccountPage.changePassword("", "", "");
        editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getCurrPasswordError(), ERROR_REQUIRED_FIELD);
        Assert.assertEquals(accountInformationPage.getPasswordError(), ERROR_REQUIRED_FIELD);
        Assert.assertEquals(accountInformationPage.getPasswordConfirmError(), ERROR_REQUIRED_FIELD);
    }

    @Test(dependsOnMethods = "editNullInformation")
    public void editWithValidInfo() {
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.fillInAccountInformation(cusEdit);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), EDIT_SUCCESS_MSG);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cusEdit));
    }

    @Test(dependsOnMethods = "editWithValidInfo")
    public void editNewPassword() {
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.changePassword(passwordCurrent, passwordChange, passwordChange);
        cusEdit.setPassword(passwordChange);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), EDIT_SUCCESS_MSG);
        headerPage.logout();
        headerPage.login(cusEdit);
        Assert.assertFalse(headerPage.isGuest());
    }

    @Test(dependsOnMethods = "editNewPassword", alwaysRun = true)
    public void returnAddress() {
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.fillInAccountInformation(cus);
        editAccountPage.changePassword(passwordChange, passwordCurrent, passwordCurrent);
        accountInformationPage = editAccountPage.saveAccount();
    }
}
