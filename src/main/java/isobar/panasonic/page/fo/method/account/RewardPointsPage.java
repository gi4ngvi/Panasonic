package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.page.fo.locator.account.RewardPointsLocator;
import org.openqa.selenium.WebDriver;

public class RewardPointsPage extends RewardPointsLocator {
    public RewardPointsPage(WebDriver driver) {
        super(driver);
    }

    public String getRewardPointsBalance(){
        waitUtility.waitUntilVisibilityOf(weRewardBalance);
        return weRewardBalance.getText().trim();
    }
}
