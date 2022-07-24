package isobar.panasonic.page.fo.method.checkout;

import isobar.panasonic.entity.data.CreditCard;
import isobar.panasonic.page.fo.locator.checkout.OnePayPaymentLocator;
import isobar.panasonic.utility.DataTest;
import org.openqa.selenium.WebDriver;

public class OnePayPaymentPage extends OnePayPaymentLocator {
    public OnePayPaymentPage(WebDriver driver) {
        super(driver);
    }

    public void selectVCBank(){
        waitUtility.waitUntilToBeClickAble(weVCBank);
        weVCBank.click();
    }

    public void selectABBank(){
        waitUtility.waitUntilToBeClickAble(weABBank);
        weABBank.click();
    }

    public void fillInOnePayDomestic(CreditCard card){
        setCardNumber(card.getCreditCardNumber());
        setCardMonth(DataTest.getMonthNumberOfCreditCard(card.getExpMonth()));
        setCardYear(card.getExpYear());
        setCardHolderName(card.getCreditCardName());
    }

    public void submit(){
        waitUtility.waitUntilToBeClickAble(weContinueCheckOut);
        weContinueCheckOut.click();
        waitUtility.waitForPageLoad();
    }

    public FailurePage cancel(){
        waitUtility.waitUntilToBeClickAble(weCancel);
        weCancel.click();
        return new FailurePage(driver);
    }

    public String getVerifyCardMsg(){
        waitUtility.waitUntilVisibilityOf(weVerifyCardMsg);
        return weVerifyCardMsg.getText().trim();
    }

    public void setOTP(String otp){
        waitUtility.waitUntilToBeClickAble(weOTP);
        weOTP.clear();
        weOTP.sendKeys(otp);
    }

    public SuccessPage payment(){
        waitUtility.waitUntilToBeClickAble(wePayment);
        wePayment.click();
        waitUtility.waitUntilVisibilityOf(weSendOTPMsg);
        waitUtility.waitUntilToBeClickAble(wePayment);
        wePayment.click();
        return new SuccessPage(driver);
    }

    private void setCardNumber(String key){
        waitUtility.waitUntilToBeClickAble(weCardNumber);
        weCardNumber.clear();
        weCardNumber.sendKeys(key);
    }

    private void setCardMonth(String key){
        waitUtility.waitUntilToBeClickAble(weCardMonth);
        weCardMonth.clear();
        weCardMonth.sendKeys(key);
    }

    private void setCardYear(String key){
        waitUtility.waitUntilToBeClickAble(weCardYear);
        weCardYear.clear();
        weCardYear.sendKeys(key);
    }

    private void setCardHolderName(String key){
        weCardHolderName.clear();
        weCardHolderName.sendKeys(key);
    }

    public boolean checkUrlRedirect(String url){
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.indexOf(url)!=-1;
    }
}
