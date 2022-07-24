package isobar.panasonic.functions.my_en;

import isobar.panasonic.page.bo.method.dashboard.BODashBoardPage;
import isobar.panasonic.page.bo.method.stores.ConfigurationPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.SeleniumTest;
import org.testng.annotations.Test;

public class MY_EN_PreCondition extends SeleniumTest {
    private BODashBoardPage dashBoardPage;
    private ConfigurationPage configurationPage;

    @Test
    public void turnOffLiveStock() {
        dashBoardPage = launchBOSite();
        configurationPage = dashBoardPage.goToConfigurationPage();
        configurationPage.goToLiveStockMenu(DataTest.getSiteCode());
        configurationPage.setBackupLiveStockStatus();
        configurationPage.setLiveStockStatus(false);
    }
}
