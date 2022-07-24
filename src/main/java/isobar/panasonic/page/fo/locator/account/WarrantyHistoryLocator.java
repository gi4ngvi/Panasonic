package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WarrantyHistoryLocator extends SeleniumFactory {
    protected static final String VIEW_WARRANTY = "xpath=(//table[@id='my-orders-table']//tbody//td[normalize-space(text())='%s']/ancestor::tr//a[@class='action view'])[1]";
    @FindBy(how = How.XPATH,using = "//div[@class='warranty-detail']//tr[1]//td[2]")
    protected WebElement weProductCategory;
    @FindBy(how = How.XPATH,using = "//div[@class='warranty-detail']//tr[2]//td[2]")
    protected WebElement weProductSKU;
    @FindBy(how = How.XPATH,using = "//div[@class='warranty-detail']//tr[3]//td[2]")
    protected WebElement weSerialNo;
    @FindBy(how = How.XPATH,using = "//div[@class='warranty-detail']//tr[4]//td[2]")
    protected WebElement wePurchaseDate;

    public WarrantyHistoryLocator(WebDriver driver) {
        super(driver);
    }
}
