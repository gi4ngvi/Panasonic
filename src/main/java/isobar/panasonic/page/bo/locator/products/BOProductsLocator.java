package isobar.panasonic.page.bo.locator.products;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public abstract class BOProductsLocator extends SeleniumFactory {
    protected final String EDIT_PRODUCT = "xpath=//a[contains(@href,'Edit?ProductID=')][text()='%s']";
    @FindBy(how = How.CSS, using = "input[name='WFSimpleSearch_NameOrID']")
    protected WebElement weQuickSearch;

    public BOProductsLocator(WebDriver driver) {
        super(driver);
    }
}
