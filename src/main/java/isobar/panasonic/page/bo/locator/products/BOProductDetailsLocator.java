package isobar.panasonic.page.bo.locator.products;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BOProductDetailsLocator extends SeleniumFactory {
    @FindBy(how = How.XPATH, using = "//a[text()='Inventory']")
    protected WebElement weInventoryTab;
    @FindBy(how = How.XPATH, using = "//a[@class='table_detail_link'][contains(text(),'panasonic-jp-inventory')]/ancestor::tr/td[@class='table_detail4 e s right bold']")
    protected WebElement weProductStock;

    public BOProductDetailsLocator(WebDriver driver) {
        super(driver);
    }
}
