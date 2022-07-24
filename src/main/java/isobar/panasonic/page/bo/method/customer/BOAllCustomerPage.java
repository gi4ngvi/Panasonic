package isobar.panasonic.page.bo.method.customer;

import isobar.panasonic.components.Link;
import isobar.panasonic.customer.CustomerInformation;
import isobar.panasonic.page.bo.locator.customer.BOAllCustomerLocator;
import isobar.panasonic.page.bo.method.dashboard.QuickSearchPage;
import isobar.panasonic.utility.DataTest;
import isobar.panasonic.utility.ReportUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class BOAllCustomerPage extends BOAllCustomerLocator {
    QuickSearchPage quickSearch;
    Link lnkEdit, lnkTick;

    public BOAllCustomerPage(WebDriver driver) {
        super(driver);
        lnkEdit = new Link(driver);
        lnkTick = new Link(driver);
        quickSearch = new QuickSearchPage(driver);
    }

    public BOEditCustomerPage editCustomer(CustomerInformation cus) {
        BOEditCustomerPage boEditCustomerPage = new BOEditCustomerPage(driver);
        String url = driver.getCurrentUrl();
        lnkEdit.setLocator(String.format(CUSTOMER_EDIT, cus.getEmail()));
        if (!lnkEdit.isDisplay()) {
            quickSearch.clearAll();
            quickSearch.expandFilter();
            filterWebsite();
            filterEmail(cus.getEmail());
            quickSearch.apply();
        }
        lnkEdit.click();
        waitUtility.waitForURLChange(url);
        waitUtility.waitForPageLoad();
        boEditCustomerPage.waitForLoadingSuccess();
        return boEditCustomerPage;
    }

    public void deleteCustomer(CustomerInformation cus) {
        lnkTick.setLocator(String.format(CUSTOMER_TICK, cus.getEmail()));
        quickSearch.clearAll();
        quickSearch.expandFilter();
        filterWebsite();
        filterEmail(cus.getEmail());
        quickSearch.apply();
        if (!checkFilterActive(cus)) {
            return;
        }
        if (!lnkTick.isDisplay()) return;
        lnkTick.click();
        if (!isOneRecordSelected()) {
            return;
        }
        waitUtility.waitUntilToBeClickAble(weStickyAction);
        weStickyAction.click();
        weStickyDelete.click();
        waitUtility.waitUntilToBeClickAble(weConfirmDel);
        weConfirmDel.click();
    }

    private void filterEmail(String email) {
        weFilterEmail.clear();
        weFilterEmail.sendKeys(email);
        waitUtility.waitForValueOfAttribute(weFilterEmail, "value", email);
    }

    private void filterWebsite() {
        String webSiteStr = getWebsiteName();
        Select selectWebSite = new Select(weFilterWebSite);
        selectWebSite.selectByVisibleText(webSiteStr);
        waitUtility.waitForText(selectWebSite.getFirstSelectedOption(), webSiteStr);
    }

    private boolean checkFilterActive(CustomerInformation cus) {
        waitUtility.waitUntilVisibilityOf(weFilterActive);
        String filterActiveValue = weFilterActive.getText();
        boolean status = filterActiveValue.indexOf(String.format("Email: %s", cus.getEmail())) != -1;
        status &= filterActiveValue.indexOf(String.format("Web Site: %s", getWebsiteName())) != -1;
        return status;
    }

    private String getWebsiteName() {
        return String.format("Panasonic %s Website", DataTest.getCountry().getCode().toUpperCase());
    }

    private boolean isOneRecordSelected() {
        try {
            String recordSelectedStr = weCountRecordSelected.getText().trim().split("found")[1];
            int recordSelectedNumber = Integer.parseInt(recordSelectedStr.replaceAll("[^0-9]", ""));
            if (recordSelectedNumber > 1) {
                ReportUtility.getInstance().logInfo("more 1 record customer selected");
                return false;
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            ReportUtility.getInstance().logInfo("unable selected record customer");
            return false;
        }
    }
}