package isobar.panasonic.page.bo.locator.marketing;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BOGiftCardAccountLocator extends SeleniumFactory {

    protected static final String GIFTCARD_TABLE = "xpath=//table[@id='giftcardaccountGrid_table']";
    protected static final String HEADER_CODE = "Code";
    protected static final String HEADER_ID = "ID";
    protected static final String HEADER_STATUS = "Status";
    @FindBy(how = How.CSS, using = "#add")
    protected WebElement weAdd;
    @FindBy(how = How.CSS, using = "#_infowebsite_id")
    protected WebElement weWebsite;
    @FindBy(how = How.CSS, using = "#_infobalance")
    protected WebElement weBalance;
    @FindBy(how = How.CSS, using = "#save")
    protected WebElement weSave;

    public BOGiftCardAccountLocator(WebDriver driver) {
        super(driver);
    }
}
