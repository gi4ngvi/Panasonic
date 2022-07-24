package isobar.panasonic.page.fo.locator.account;

import isobar.panasonic.factory.SeleniumFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RewardPointsLocator extends SeleniumFactory {
    @FindBy(how = How.CSS,using = "p.reward-balance span.price")
    protected WebElement weRewardBalance;

    public RewardPointsLocator(WebDriver driver) {
        super(driver);
    }
}
