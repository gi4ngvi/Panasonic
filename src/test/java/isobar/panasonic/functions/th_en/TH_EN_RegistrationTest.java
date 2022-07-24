package isobar.panasonic.functions.th_en;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.RegisterPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.ReportUtility;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TH_EN_RegistrationTest extends SeleniumTest {
    CustomerInformation cus,cusMissFirstName,cusMissLastName, cusMissNickName, cusMissEmail, cusMissPass, cusMissConfirmPass,
            cusWithPassAndConfirmPassNotMatch, cusEmpty,cusAlready, cusInvalid;
    RegisterPage registerPage;
    AccountInformationPage accountInformationPage;
    String MSG_REQUIRED, MSG_PASSWORD_NOT_MATCH, MSG_REGISTER_SUCCESS,MSG_NICKNAME_MAXLENGTH, MSG_EMAIL_INVALID, MSG_EMAIL_ALREADY;
    StringUtility stringUtility;

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getRandomCustomerTest();
        cusInvalid = DataTest.getRandomCustomerTest();
        cusInvalid.setNickName(stringUtility.getRandomCharacter(35));
        cusInvalid.setEmail(stringUtility.getRandomCharacter(10));
        cusAlready = DataTest.getDefaultCustomerTest();
        cusMissNickName = DataTest.getRandomCustomerTest();
        cusMissNickName.setNickName("");
        cusMissFirstName = DataTest.getRandomCustomerTest();
        cusMissFirstName.setFirstName("");
        cusMissLastName = DataTest.getRandomCustomerTest();
        cusMissLastName.setLastName("");
        cusMissEmail = DataTest.getRandomCustomerTest();
        cusMissEmail.setEmail("");
        cusMissConfirmPass = DataTest.getRandomCustomerTest();
        cusMissConfirmPass.setPasswordConfirm("");
        cusMissPass = DataTest.getRandomCustomerTest();
        cusMissPass.setPassword("");
        cusEmpty = new CustomerInformation.Builder().email("").password("").passwordConfirm("").firstName("").lastName("").nickName("").dateOfBirth("").
                identityCardNumber("").typeOfIdentityCard("-- Choose --").build();
        cusWithPassAndConfirmPassNotMatch = DataTest.getRandomCustomerTest();
        cusWithPassAndConfirmPassNotMatch.setPasswordConfirm("ABcd@"+stringUtility.getRandomCharacter(5));
        MSG_PASSWORD_NOT_MATCH = "Please enter the same value again.";
        MSG_REQUIRED = "This is a required field.";
        MSG_REGISTER_SUCCESS = "Thank you for registering with Panasonic Online Store.";
        MSG_NICKNAME_MAXLENGTH = "Please enter less or equal than 30 symbols.";
        MSG_EMAIL_INVALID = "Please enter a valid email address (Ex: johndoe@domain.com).";
        MSG_EMAIL_ALREADY = "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.";
    }

    @Test
    public void registerWithFirstNameEmpty(){
        headerPage.logout();
        registerPage = headerPage.register();
        registerPage.fillInNewCustomerAccount(cusMissFirstName);
        registerPage.submit();
        Assert.assertEquals(registerPage.getFirstnameError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithFirstNameEmpty")
    public void registerWithLastNameEmpty(){
        headerPage.logout();
        registerPage = headerPage.register();
        registerPage.fillInNewCustomerAccount(cusMissLastName);
        registerPage.submit();
        Assert.assertEquals(registerPage.getLastnameError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithLastNameEmpty")
    public void registerWithNicknameEmpty(){
        registerPage.fillInNewCustomerAccount(cusMissNickName);
        registerPage.submit();
        Assert.assertEquals(registerPage.getNickNameError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithNicknameEmpty")
    public void registerWithEmailEmpty(){
        registerPage.fillInNewCustomerAccount(cusMissEmail);
        registerPage.submit();
        Assert.assertEquals(registerPage.getEmailError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithEmailEmpty")
    public void registerWithPassEmpty(){
        registerPage.fillInNewCustomerAccount(cusMissPass);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithEmailEmpty")
    public void registerWithConfirmPassEmpty(){
        registerPage.fillInNewCustomerAccount(cusMissConfirmPass);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithEmailEmpty")
    public void registerWithPasswordAndPasswordConfirmNotMatch(){
        registerPage.fillInNewCustomerAccount(cusWithPassAndConfirmPassNotMatch);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_PASSWORD_NOT_MATCH);
    }

    @Test(dependsOnMethods = "registerWithPasswordAndPasswordConfirmNotMatch")
    public void registerWithAllFieldEmpty(){
        registerPage.fillInNewCustomerAccount(cusEmpty);
        registerPage.submit();
        Assert.assertEquals(registerPage.getFirstnameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getLastnameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getNickNameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getPasswordError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getEmailError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithAllFieldEmpty")
    public void registerWithInfoInvalid(){
        registerPage.fillInNewCustomerAccount(cusInvalid);
        registerPage.submit();
        Assert.assertEquals(registerPage.getNickNameError(), MSG_NICKNAME_MAXLENGTH);
        Assert.assertEquals(registerPage.getEmailError(), MSG_EMAIL_INVALID);
    }

    @Test(dependsOnMethods = "registerWithInfoInvalid")
    public void registerWithEmailAlready(){
        registerPage.fillInNewCustomerAccount(cusAlready);
        ReportUtility.getInstance().log(LogStatus.WARNING, "Temporary accept term and condition to workaround for ticket PANAMAINTE-332. Waiting to update testcase!");
        registerPage.setAcceptTermAndConditions(true);
        registerPage.submit();
        Assert.assertEquals(registerPage.getErrorMsg(), MSG_EMAIL_ALREADY);
    }

    @Test(dependsOnMethods = "registerWithEmailAlready")
    public void registerWithInfoValid(){
        registerPage.fillInNewCustomerAccount(cus);
        ReportUtility.getInstance().log(LogStatus.WARNING, "Temporary accept term and condition to workaround for ticket PANAMAINTE-332. Waiting to update testcase!");
        registerPage.setAcceptTermAndConditions(true);
        accountInformationPage = registerPage.submit();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_REGISTER_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus));
    }
}

