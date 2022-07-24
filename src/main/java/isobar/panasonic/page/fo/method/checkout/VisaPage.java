package isobar.panasonic.page.fo.method.checkout;

import isobar.panasonic.entity.data.CreditCard;
import isobar.panasonic.page.fo.locator.checkout.VisaLocator;
import isobar.panasonic.utility.DataTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class VisaPage extends VisaLocator {
    public VisaPage(WebDriver driver) {
        super(driver);
    }

    public void fillInVisa(CreditCard card) {
        setCardNumber(card.getCreditCardNumber());
        setExpMonth(DataTest.getMonthNumberOfCreditCard(card.getExpMonth()).trim());
        setExpYear(card.getExpYear());
        setCSC(card.getSecurityCode());
        submit();
    }

    public void submit(){
        waitUtility.waitUntilVisibilityOf(weSubmit);
        weSubmit.click();
    }

    public FailurePage cancel(){
        waitUtility.waitUntilVisibilityOf(weCancel);
        weCancel.click();
        return new FailurePage(driver);
    }

    public String getErrorMsg(){
        waitUtility.waitUntilVisibilityOf(weErrorMsg);
        return weErrorMsg.getText();
    }

    public SuccessPage submitPassword(String password){
        waitUtility.waitForPageLoad();
        setPassword(password);
        weSubmitPassword.click();
        driver.switchTo().parentFrame();
        return new SuccessPage(driver);
    }

    private void setPassword(String password){
        waitUtility.waitUntilToBeClickAble(wePassword);
        wePassword.clear();
        wePassword.sendKeys(password);
    }

    private void setCardNumber(String cardNumber){
        waitUtility.waitUntilVisibilityOf(weCardNumber);
        weCardNumber.clear();
        weCardNumber.sendKeys(cardNumber);
    }

    private void setExpMonth(String month){
        Select selectExpMonth = new Select(weExprMonth);
        selectExpMonth.selectByValue(month);
    }

    private void setExpYear(String year){
        Select selectExpYear = new Select(weExprYear);
        selectExpYear.selectByValue(year);
    }

    private void setCSC(String csc){
        weCSC.clear();
        weCSC.sendKeys(csc);
    }
}
