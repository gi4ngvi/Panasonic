package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.fo.locator.account.CreatePasswordLocator;
import isobar.panasonic.page.fo.method.utility.LoginPopupPage;
import org.openqa.selenium.WebDriver;

public class CreatePasswordPage extends CreatePasswordLocator {

    public CreatePasswordPage(WebDriver driver) {
        super(driver);
    }
    private void enterPassword(String key) {
        wePassword.clear();
        wePassword.sendKeys(key);
    }

    private void enterPasswordConfirm(String key) {
        wePasswordConfirm.clear();
        wePasswordConfirm.sendKeys(key);
    }

    private void submit() {
        weSubmit.click();
    }

    public LoginPopupPage createNewPassword(CustomerInformation cus) {
        enterPassword(cus.getPassword());
        enterPasswordConfirm(cus.getPasswordConfirm());
        submit();
        return new LoginPopupPage(driver);
    }
}
