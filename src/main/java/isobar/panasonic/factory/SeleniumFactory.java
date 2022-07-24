package isobar.panasonic.factory;

import isobar.panasonic.utility.ActionUtility;
import isobar.panasonic.utility.WaitUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SeleniumFactory {
    public WaitUtility waitUtility;
    public ActionUtility actionUtility;
    public WebDriver driver;

    public SeleniumFactory(WebDriver driver) {
        this.driver = driver;
        initComponents();
    }

    private void initComponents() {
        waitUtility = new WaitUtility(driver);
        actionUtility = new ActionUtility(driver);
        PageFactory.initElements(driver, this);
    }
}