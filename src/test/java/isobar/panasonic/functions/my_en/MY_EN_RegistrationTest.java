package isobar.panasonic.functions.my_en;

import isobar.panasonic.customer.Address;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.*;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MY_EN_RegistrationTest extends SeleniumTest {
    CustomerInformation cus, cusInvalid, cusAlready;
    Address addressInvalid, address;
    RegisterPage registerPage;
    MyAddressPage myAddressPage;
    EditAccountPage editAccountPage;
    NewsletterSubscriptionPage newsletterSubscriptionPage;
    AccountInformationPage accountInformationPage;
    String MSG_REQUIRED, MSG_PASSWORD_NOT_MATCH, MSG_REGISTER_SUCCESS, MSG_EMAIL_INVALID, MSG_PASSWORD_INVALID,
            MSG_EMAIL_ALREADY, MSG_STATE_REQUIRED, MSG_NICKNAME_MAXLENGTH, MSG_MOBILE_NUMBER_INVALID ;
    StringUtility stringUtility;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getRandomCustomerTest();
        cus.setDateOfBirth("1990-10-10");
        cus.setMessagingService(true);
        cus.setSubscribeNewsletter(true);
        address = DataTest.getDefaultBillingAddress();
        address.setFirstName(cus.getFirstName());
        address.setLastName(cus.getLastName());
        cusInvalid = DataTest.getRandomCustomerTest();
        cusInvalid.setDateOfBirth("1990-10-10");
        cusInvalid.setEmail(stringUtility.getRandomCharacter(10)).setPassword(stringUtility.getRandomCharacter(10).toLowerCase()).
                setPasswordConfirm(RUN_ID).setNickName(stringUtility.getRandomCharacter(35));
        addressInvalid = DataTest.getDefaultBillingAddress();
        addressInvalid.setMobileNumber("%%^$%$abcde");
        cusAlready = DataTest.getCustomer2();
        cusAlready.setDateOfBirth("1990-10-10");
        MSG_EMAIL_INVALID = "Please enter a valid email address (Ex: johndoe@domain.com).";
        MSG_PASSWORD_NOT_MATCH = "Please enter the same value again.";
        MSG_PASSWORD_INVALID = "Minimum 8 characters (Letters and Numbers)";
        MSG_REQUIRED = "This is a required field.";
        MSG_REGISTER_SUCCESS = "Thank you for registering with Panasonic Malaysia Online Store.";
        MSG_EMAIL_ALREADY = "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.";
        MSG_STATE_REQUIRED = "Please select an option.";
        MSG_NICKNAME_MAXLENGTH = "Please enter less or equal than 30 symbols.";
        MSG_MOBILE_NUMBER_INVALID = "Please enter a valid number in this field.";
    }

    @Test
    public void registerWithoutInfo(){
        headerPage.logout();
        registerPage = headerPage.register();
        registerPage.submit();
        Assert.assertEquals(registerPage.getFirstnameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getLastnameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getNickNameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getDateOfBirthError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getTypeOfIdentityCardError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getIdentityCardNumberError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getRaceError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getEmailError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getPasswordError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getStreetError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getCityError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getStateError(), MSG_STATE_REQUIRED);
        Assert.assertEquals(registerPage.getZipError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getMobileNumberError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithoutInfo")
    public void registerWithInvalidInfo(){
        registerPage.fillInNewCustomerAccount(cusInvalid);
        registerPage.fillInAddressInformation(addressInvalid);
        registerPage.submit();
        Assert.assertEquals(registerPage.getEmailError(), MSG_EMAIL_INVALID);
        Assert.assertEquals(registerPage.getPasswordError(), MSG_PASSWORD_INVALID);
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_PASSWORD_NOT_MATCH);
        Assert.assertEquals(registerPage.getNickNameError(), MSG_NICKNAME_MAXLENGTH);
        Assert.assertEquals(registerPage.getMobileNumberError(), MSG_MOBILE_NUMBER_INVALID);
    }

    @Test(dependsOnMethods = "registerWithInvalidInfo")
    public void registerWithEmailAlready(){
        registerPage.fillInNewCustomerAccount(cusAlready);
        registerPage.fillInAddressInformation(address);
        registerPage.submit();
        Assert.assertEquals(registerPage.getErrorMsg(), MSG_EMAIL_ALREADY);
    }

    @Test(dependsOnMethods = "registerWithInvalidInfo")
    public void registerWithValidInfo(){
        registerPage.fillInNewCustomerAccount(cus);
        registerPage.fillInAddressInformation(address);
        registerPage.setDefaultShippingAddress(true);
        accountInformationPage = registerPage.submit();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_REGISTER_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus));
        myAddressPage = accountInformationPage.manageAddresses();
        Assert.assertTrue(myAddressPage.checkDefaultBillingAddress(address));
        Assert.assertTrue(myAddressPage.checkDefaultShippingAddress(address));
        accountInformationPage = headerPage.gotoAccountInfo();
        newsletterSubscriptionPage = accountInformationPage.openNewLetterTab();
        Assert.assertTrue(newsletterSubscriptionPage.isSubscribed());
        accountInformationPage = headerPage.gotoAccountInfo();
        editAccountPage = accountInformationPage.openAccountInfomationTab();
        Assert.assertEquals(editAccountPage.getMessagingService(), "Yes");
    }
}
