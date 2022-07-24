package isobar.panasonic.page.fo.locator.checkout;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class VisaLocator extends SeleniumFactory {
    @FindBy(how = How.ID, using = "cardno")
    protected WebElement weCardNumber;
    @FindBy(how = How.ID, using = "slMonth")
    protected WebElement weExprMonth;
    @FindBy(how = How.NAME, using = "yy")
    protected WebElement weExprYear;
    @FindBy(how = How.CSS, using = "#cardHolder2")
    protected WebElement weCardHolder;
    @FindBy(how = How.ID, using = "csc")
    protected WebElement weCSC;
    @FindBy(how = How.ID, using = "but_thanh_toan")
    protected WebElement weSubmit;
    @FindBy(how = How.CSS, using = "a.cancel_but")
    protected WebElement weCancel;
    @FindBy(how = How.CSS,using = "div.text_error")
    protected WebElement weErrorMsg;
    @FindBy(how = How.ID, using = "password")
    protected WebElement wePassword;
    @FindBy(how = How.NAME, using = "UsernamePasswordEntry")
    protected WebElement weSubmitPassword;
    @FindBy(how = How.ID,using = "authWindow")
    protected WebElement weAuthWindowIframe;

    @FindBy(how = How.XPATH, using = "//b[text()=' 商戶參考編號 : ']/ancestor::tr[@valign='middle']/td[@class='zh_TW_text']")
    protected WebElement weOrderID;
    @FindBy(how = How.CSS, using = "form[name='failForm'] u")
    protected WebElement weTransactionFailed;

    public VisaLocator(WebDriver driver) {
        super(driver);
    }
}
