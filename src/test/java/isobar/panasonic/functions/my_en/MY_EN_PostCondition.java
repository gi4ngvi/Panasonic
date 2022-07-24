package isobar.panasonic.functions.my_en;

import isobar.panasonic.page.bo.method.dashboard.BODashBoardPage;
import isobar.panasonic.page.bo.method.stores.ConfigurationPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.annotations.Test;

public class MY_EN_PostCondition extends SeleniumTest {
    private BODashBoardPage dashBoardPage;
    private ConfigurationPage configurationPage;

    @Test
    public void backupLiveStockStatus() {
        dashBoardPage = launchBOSite();
        configurationPage = dashBoardPage.goToConfigurationPage();
        configurationPage.goToLiveStockMenu(DataTest.getSiteCode());
        configurationPage.setLiveStockStatus(DataTest.getBackupLiveStockStatus());
    }
}
