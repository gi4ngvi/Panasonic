package isobar.panasonic.functions.vn_vn;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.method.account.AccountInformationPage;
import isobar.panasonic.page.fo.method.account.RegisterPage;
import isobar.panasonic.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTest extends SeleniumTest {
    CustomerInformation cus, cusInvalid;
    RegisterPage registerPage;
    AccountInformationPage accountInformationPage;
    String ERROR_REQUIRED_FILED, ERROR_INVALID_EMAIL_FORMAT,
            ERROR_MINIMUM_PASSWORD_LENGTH, ERROR_MINIMUM_PASSWORD_INCLUDED,
            PASS_YEU, PASS_TB, ERROR_NOT_SAME_PASS, REGISTER_SUCCESS;

    @Override
    protected void preCondition() {
        cus = DataTest.getRandomCustomerTest();
        cusInvalid = new CustomerInformation.Builder()
                .name("invalid")
                .email("abc")
                .password("abc")
                .passwordConfirm("abc")
                .build();
        ERROR_REQUIRED_FILED = "Đây là trường bắt buộc.";
        ERROR_INVALID_EMAIL_FORMAT = "Vui lòng nhập một địa chỉ email hợp lệ (ví dụ: johndoe@domain.com).";
        ERROR_MINIMUM_PASSWORD_LENGTH = "Chiều dài tối thiểu của trường này phải bằng hoặc lớn hơn 8 ký tự. Khoảng trống dẫn và dấu cuối sẽ bị bỏ qua.";
        ERROR_MINIMUM_PASSWORD_INCLUDED = "Tối thiểu các ký tự trong mật khẩu khác nhau là 4. Các trường hợp của ký tự: Chữ thường, Chữ hoa, Số, Ký tự đặc biệt.";
        ERROR_NOT_SAME_PASS = "Vui lòng nhập cùng giá trị một lần nữa.";
        PASS_YEU = "Yếu kém";
        PASS_TB = "Trung bình";
        REGISTER_SUCCESS = "Cảm ơn bạn đã đăng ký với Panasonic Online Store.";
    }

    @Test()
    public void registryNullAccount() {
        headerPage.logout();
        registerPage = headerPage.register();
        registerPage.submit();
        Assert.assertEquals(registerPage.getFirstnameError(), ERROR_REQUIRED_FILED);
        Assert.assertEquals(registerPage.getEmailError(), ERROR_REQUIRED_FILED);
        Assert.assertEquals(registerPage.getPasswordError(), ERROR_REQUIRED_FILED);
        Assert.assertEquals(registerPage.getPasswordconfirmError(), ERROR_REQUIRED_FILED);
    }

    @Test(dependsOnMethods = "registryNullAccount", alwaysRun = true)
    public void registryInvalidEmail() {
        registerPage.fillInNewCustomerAccount(cusInvalid);
        registerPage.submit();
        Assert.assertEquals(registerPage.getEmailError(), ERROR_INVALID_EMAIL_FORMAT);
    }

    @Test(dependsOnMethods = "registryInvalidEmail", alwaysRun = true)
    public void registryInvalidPassword() {
        cusInvalid.setEmail(cus.getEmail());
        cusInvalid.setPassword("abc");
        cusInvalid.setPasswordConfirm("abc");
        registerPage.fillInNewCustomerAccount(cusInvalid);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordError(), ERROR_MINIMUM_PASSWORD_LENGTH);
        cusInvalid.setPassword("abc12345");
        registerPage.fillInNewCustomerAccount(cusInvalid);
        Assert.assertEquals(registerPage.getPasswordStrength(), PASS_YEU);
        cusInvalid.setPassword(cus.getPassword());
        registerPage.fillInNewCustomerAccount(cusInvalid);
        registerPage.submit();
        Assert.assertEquals(registerPage.getPasswordStrength(), PASS_TB);
        Assert.assertEquals(registerPage.getPasswordconfirmError(), ERROR_NOT_SAME_PASS);
    }

    @Test(dependsOnMethods = "registryInvalidPassword", alwaysRun = true)
    public void registryValidCustomer() {
        registerPage.fillInNewCustomerAccount(cus);
        accountInformationPage = registerPage.submit();
        Assert.assertEquals(accountInformationPage.getSuccessMsg(), REGISTER_SUCCESS);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus));
        headerPage.selectStore();
        headerPage.logout();
        headerPage.login(cus);
        Assert.assertTrue(accountInformationPage.checkContactInfo(cus));
    }
}
