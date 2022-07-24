package isobar.panasonic.page.fo.locator.home;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomeLocator extends SeleniumFactory {

    @FindBy(how = How.CSS, using = "div.home-slider")
    protected WebElement weSlideBanner;
    @FindBy(how = How.CSS, using = ".breadcrumbs")
    protected WebElement weBreadcrumbs;

    public HomeLocator(WebDriver driver) {
        super(driver);
    }
}
