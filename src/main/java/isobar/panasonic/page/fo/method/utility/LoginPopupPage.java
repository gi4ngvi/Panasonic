package isobar.panasonic.page.fo.method.utility;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.entity.data.SiteCode;
import isobar.panasonic.page.fo.locator.utility.LoginPopupLocator;
import isobar.panasonic.page.fo.method.account.RegisterPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.Panasonic;
import org.openqa.selenium.WebDriver;


public class LoginPopupPage extends LoginPopupLocator {

    public LoginPopupPage(WebDriver driver) {
        super(driver);
    }

    private void enterLoginEmail(String key) {
        weLoginEmail.clear();
        weLoginEmail.sendKeys(key);
    }

    private void enterLoginPass(String key) {
        weLoginPassword.clear();
        weLoginPassword.sendKeys(key);
    }

    public void submitLogin() {
        waitUtility.waitUntilToBeClickAble(weLoginSubmit);
        weLoginSubmit.click();
        waitUtility.waitForPageLoad();
    }

    public void fillInLoginAndSubmit(CustomerInformation cus) {
        if (DataTest.getSiteCode() == SiteCode.MY_EN) {
            loginByLink(cus);
        } else {
            waitUtility.waitForPageLoad();
            waitUtility.waitUntilToBeClickAble(weLoginSubmit);
            enterLoginEmail(cus.getEmail());
            enterLoginPass(cus.getPassword());
            submitLogin();
        }
    }

    public void clickForgotPassword() {
        waitUtility.waitUntilToBeClickAble(weForgotPassLink);
        weForgotPassLink.click();
    }

    private void enterForgotEmail(String key) {
        weForgotEmail.clear();
        weForgotEmail.sendKeys(key);
    }

    private void submit() {
        weSubmit.click();
    }

    public void forgotPassword(String key) {
        enterForgotEmail(key);
        submit();
        Panasonic.checkStoreView(driver);
    }

    public String getForgotEmailError() {
        return weForgotEmailError.getText();
    }

    public boolean checkSuccessMsg(String email) {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText().equals(String.format("Nếu có một tài khoản liên kết với %s, bạn sẽ nhận được một email với một liên kết để đặt lại mật khẩu của bạn.", email));
    }

    public String getSuccessMsg() {
        waitUtility.waitUntilVisibilityOf(weSuccessMsg);
        return weSuccessMsg.getText();
    }

    public String getErrorMsg() {
        waitUtility.waitUntilVisibilityOf(weErrorMsg);
        return weErrorMsg.getText();
    }

    public String getEmailError() {
        return weErrorEmail.getText();
    }

    public String getPasswordError() {
        return weErrorPass.getText();
    }

    private void loginByLink(CustomerInformation cus) {
        driver.get("https://panasonic.dev.bluecomvn.com/my_en/customer/account/login");
        waitUtility.waitUntilToBeClickAble(weEmail);
        weEmail.sendKeys(cus.getEmail());
        waitUtility.waitUntilToBeClickAble(wePass);
        wePass.sendKeys(cus.getPassword());
        waitUtility.waitUntilToBeClickAble(weLogin);
        weLogin.click();
        waitUtility.waitForPageLoad();
    }

}