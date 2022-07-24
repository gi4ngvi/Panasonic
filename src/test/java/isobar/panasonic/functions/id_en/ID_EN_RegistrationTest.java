package isobar.panasonic.functions.id_en;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.RegisterPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import isobar.panasonic.utility.StringUtility;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ID_EN_RegistrationTest extends SeleniumTest {
    private CustomerInformation cus, cusMissFullName, cusMissNickName, cusMissEmail, cusMissPass, cusMissConfirmPass, cusWithPassAndConfirmPassNotMatch, cusEmpty, cusAlready, cusInvalid;
    private RegisterPage registerPage;
    private AccountInformationPage accountInformationPage;
    private StringUtility stringUtility;
    private static final String MSG_REQUIRED = "This is a required field.";
    private static final String MSG_PASSWORD_NOT_MATCH = "Please enter the same value again.";
    private static final String MSG_REGISTER_SUCCESS = "Thank you for registering with Panasonic Online Store.";
    private static final String MSG_NICKNAME_MAXLENGTH = "Please enter less or equal than 30 symbols.";
    private static final String MSG_EMAIL_INVALID = "Please enter a valid email address (Ex: johndoe@domain.com).";
    private static final String MSG_EMAIL_ALREADY = "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.";

    @Override
    protected void preCondition() {
        stringUtility = new StringUtility();
        cus = DataTest.getRandomCustomerTest();
        cusMissNickName = DataTest.getRandomCustomerTest();
        cusMissNickName.setNickName("");
        cusMissFullName = DataTest.getRandomCustomerTest();
        cusMissFullName.setName("");
        cusMissEmail = DataTest.getRandomCustomerTest();
        cusMissEmail.setEmail("");
        cusMissConfirmPass = DataTest.getRandomCustomerTest();
        cusMissConfirmPass.setPasswordConfirm("");
        cusMissPass = DataTest.getRandomCustomerTest();
        cusMissPass.setPassword("");
        cusEmpty = new CustomerInformation.Builder().email("").password("").passwordConfirm("").name("").nickName("").subscribeNewsletter(false).dateOfBirth("").identityCardNumber("").title("Mr.").typeOfIdentityCard("-- Choose --").build();
        cusWithPassAndConfirmPassNotMatch = DataTest.getRandomCustomerTest();
        cusWithPassAndConfirmPassNotMatch.setPasswordConfirm("ABcd@" + stringUtility.getRandomCharacter(5));
        cusInvalid = DataTest.getRandomCustomerTest();
        cusInvalid.setNickName(stringUtility.getRandomCharacter(35));
        cusInvalid.setEmail(stringUtility.getRandomCharacter(10));
        cusAlready = DataTest.getDefaultCustomerTest();
        cusAlready.setSubscribeNewsletter(false);
    }

    @Test
    public void registerWithFullNameEmpty() {
        headerPage.logout();
        registerPage = headerPage.register();
        registerPage.fillInNewCustomerAccount(cusMissFullName);
        registerPage.submit();
        Assert.assertEquals(registerPage.getFullNameError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithFullNameEmpty")
    public void registerWithNicknameEmpty() {
        registerPage.fillInNewCustomerAccount(cusMissNickName);
        registerPage.submit();
        Assert.assertEquals(registerPage.getNickNameError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithNicknameEmpty")
    public void registerWithEmailEmpty() {
        registerPage.fillInNewCustomerAccount(cusMissEmail);
        registerPage.submit();
        Assert.assertEquals(registerPage.getEmailError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithEmailEmpty")
    public void registerWithPassEmpty() {
        registerPage.fillInNewCustomerAccount(cusMissPass);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithEmailEmpty")
    public void registerWithConfirmPassEmpty() {
        registerPage.fillInNewCustomerAccount(cusMissConfirmPass);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithEmailEmpty")
    public void registerWithPasswordAndPasswordConfirmNotMatch() {
        registerPage.fillInNewCustomerAccount(cusWithPassAndConfirmPassNotMatch);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_PASSWORD_NOT_MATCH);
    }

    @Test(dependsOnMethods = "registerWithPasswordAndPasswordConfirmNotMatch")
    public void registerWithAllFieldEmpty() {
        registerPage.fillInNewCustomerAccount(cusEmpty);
        registerPage.submit();
        Assert.assertEquals(registerPage.getFullNameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getNickNameError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getPasswordconfirmError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getPasswordError(), MSG_REQUIRED);
        Assert.assertEquals(registerPage.getEmailError(), MSG_REQUIRED);
    }

    @Test(dependsOnMethods = "registerWithAllFieldEmpty")
    public void registerWithInfoInvalid() {
        registerPage.fillInNewCustomerAccount(cusInvalid);
        registerPage.submit();
        Assert.assertEquals(registerPage.getNickNameError(), MSG_NICKNAME_MAXLENGTH);
        Assert.assertEquals(registerPage.getEmailError(), MSG_EMAIL_INVALID);
    }

    @Test(dependsOnMethods = "registerWithInfoInvalid")
    public void registerWithEmailAlready() {
        registerPage.fillInNewCustomerAccount(cusAlready);
        registerPage.submit();
        Assert.assertEquals(registerPage.getErrorMsg(), MSG_EMAIL_ALREADY);
    }

    @Test(dependsOnMethods = "registerWithEmailAlready")
    public void registerWithInfoValid() {
        registerPage.fillInNewCustomerAccount(cus);
        accountInformationPage = registerPage.submit();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), MSG_REGISTER_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus));
    }
}
