package isobar.panasonic.page.fo.locator.checkout;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OnePayPaymentLocator extends SeleniumFactory {

    @FindBy(how = How.XPATH,using = "(//a[starts-with(@href,'bank.op')])[1]")
    protected WebElement weVCBank;
    @FindBy(how = How.XPATH,using = "(//a[starts-with(@href,'bank.op')])[14]")
    protected WebElement weABBank;
    @FindBy(how = How.ID,using = "CardNumber")
    protected WebElement weCardNumber;
    @FindBy(how = How.ID,using = "CardMonth")
    protected WebElement weCardMonth;
    @FindBy(how = How.ID,using = "CardYear")
    protected WebElement weCardYear;
    @FindBy(how = How.ID,using = "CardHolderName")
    protected WebElement weCardHolderName;
    @FindBy(how = How.NAME,using = "cmd_CheckOut")
    protected WebElement weContinueCheckOut;
    @FindBy(how = How.CSS,using = "a.cancel_but")
    protected WebElement weCancel;
    @FindBy(how = How.XPATH,using = "(//div[@class='container sub_container'])[4]")
    protected WebElement weVerifyCardMsg;
    @FindBy(how = How.XPATH,using = "(//input)[4]")
    protected WebElement weOTP;
    @FindBy(how = How.XPATH,using = "//button[text()='Thanh toán']")
    protected WebElement wePayment;
    @FindBy(how = How.XPATH,using = "//span[contains(text(),'Mã giao dịch đã được gửi đến số điện thoại')]")
    protected WebElement weSendOTPMsg;
    public OnePayPaymentLocator(WebDriver driver) {
        super(driver);
    }
}
