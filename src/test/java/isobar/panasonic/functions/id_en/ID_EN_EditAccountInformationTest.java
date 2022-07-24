package isobar.panasonic.functions.id_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.EditAccountPage;
import isobar.panasonic.page.fo.method.account.MyAddressPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.ReportUtility;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ID_EN_EditAccountInformationTest extends SeleniumTest {
    private CustomerInformation cus, cus_edit, cusEmpty;
    private Address shippingAddress, billingAddress;
    private StringUtility stringUtility;
    private AccountInformationPage accountInformationPage;
    private EditAccountPage editAccountPage;
    private MyAddressPage myAddressPage;
    private String currentEmail, currentPassword, emailChange1, emailChange2, passwordChange1, passwordChange2;
    private static final String MSG_ACCOUNT_SAVE_SUCCESS = "You saved the account information.";
    private static final String MSG_SAVE_ADDRESS_SUCCESS = "You saved the address.";
    private static final String MSG_REQUIRED = "This is a required field.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getCustomer2();
        cus_edit = DataTest.getCustomer2();
        cus_edit.setName(stringUtility.getRandomCharacter(5));
        cus_edit.setNickName(stringUtility.getRandomCharacter(10));
        cus_edit.setSubscribeNewsletter(false);
        cus_edit.setDateOfBirth("1990-10-10");
        cusEmpty = new CustomerInformation.Builder().title("Mr.").name("").dateOfBirth("").nickName("").typeOfIdentityCard("-- Choose --").identityCardNumber("").build();
        shippingAddress = DataTest.getDefaultShippingAddress();
        shippingAddress.setName(stringUtility.getRandomCharacter(5));
        shippingAddress.setCompany(stringUtility.getRandomCharacter(10));
        billingAddress = DataTest.getDefaultBillingAddress();
        billingAddress.setName(stringUtility.getRandomCharacter(5));
        billingAddress.setCompany(stringUtility.getRandomCharacter(10));
        currentEmail = cus.getEmail();
        currentPassword = cus.getPassword();
        emailChange1 = stringUtility.getRandomCharacter(10) + RUN_ID + "@malidator.com";
        emailChange2 = stringUtility.getRandomCharacter(10) + RUN_ID + "@malidator.com";
        passwordChange1 = "Abcd@12345";
        passwordChange2 = "Abc@123456789";
    }

    @Test
    public void changeContactInfoWithEmptyInfo(){
        headerPage.login(cus);
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.fillInAccountInformation(cusEmpty);
        editAccountPage.saveAccount();
        Assert.assertEquals(editAccountPage.getFullNameError(), MSG_REQUIRED);
        Assert.assertEquals(editAccountPage.getNicknameError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "changeContactInfoWithEmptyInfo")
    public void changeContactInfo() {
        editAccountPage.fillInAccountInformation(cus_edit);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_ACCOUNT_SAVE_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus_edit));
    }

    @Test(dependsOnMethods = "changeContactInfo")
    public void changeEmail() {
        ReportUtility.getInstance().logInfo(String.format("Email change: %s", emailChange1));
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.changeEmail(emailChange1, currentPassword);
        cus_edit.setEmail(emailChange1);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_ACCOUNT_SAVE_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus_edit));
        headerPage.logout();
        headerPage.login(cus_edit);
        Assert.assertFalse(headerPage.isGuest());
    }

    @Test(dependsOnMethods = "changeEmail")
    public void changePassword() {
        ReportUtility.getInstance().logInfo(String.format("Password change: %s", passwordChange1));
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.changePassword(currentPassword, passwordChange1, passwordChange1);
        cus_edit.setPassword(passwordChange1);
        cus_edit.setPasswordConfirm(passwordChange1);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_ACCOUNT_SAVE_SUCCESS);
        headerPage.logout();
        headerPage.login(cus_edit);
        Assert.assertFalse(headerPage.isGuest());
    }

    @Test(dependsOnMethods = "changePassword")
    public void changeBothEmailAndPassword() {
        ReportUtility.getInstance().logInfo(String.format("Email change: %s", emailChange2));
        ReportUtility.getInstance().logInfo(String.format("Password change: %s", passwordChange2));
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        cus_edit.setEmail(emailChange2);
        cus_edit.setPassword(passwordChange2);
        cus_edit.setPasswordConfirm(passwordChange2);
        editAccountPage.changeEmail(emailChange2, passwordChange1);
        editAccountPage.changePassword(passwordChange1, passwordChange2, passwordChange2);
        accountInformationPage = editAccountPage.saveAccount();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_ACCOUNT_SAVE_SUCCESS);
        headerPage.logout();
        headerPage.login(cus_edit);
        Assert.assertFalse(headerPage.isGuest());
    }

    @Test(dependsOnMethods = "changeBothEmailAndPassword")
    public void changeBillingAddress() {
        accountInformationPage = headerPage.gotoAccountInfo();
        myAddressPage = accountInformationPage.manageAddresses();
        myAddressPage.changeBillingAddress();
        myAddressPage.fillInAddress(billingAddress);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_ADDRESS_SUCCESS);
        Assert.assertTrue(myAddressPage.checkDefaultBillingAddress(billingAddress));
    }

    @Test(dependsOnMethods = "changeBillingAddress")
    public void changeShippingAddress() {
        accountInformationPage = headerPage.gotoAccountInfo();
        myAddressPage = accountInformationPage.manageAddresses();
        myAddressPage.changeShippingAddress();
        myAddressPage.fillInAddress(shippingAddress);
        myAddressPage.saveAddress();
        Assert.assertEquals(myAddressPage.getSuccessMsg(), MSG_SAVE_ADDRESS_SUCCESS);
        Assert.assertTrue(myAddressPage.checkDefaultShippingAddress(shippingAddress));
    }

    @Test(dependsOnMethods = "changeShippingAddress", alwaysRun = true)
    public void returnConfig() {
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        editAccountPage.changeEmail(currentEmail, cus_edit.getPassword());
        editAccountPage.changePassword(cus_edit.getPassword(), currentPassword, currentPassword);
        accountInformationPage = editAccountPage.saveAccount();
    }
}