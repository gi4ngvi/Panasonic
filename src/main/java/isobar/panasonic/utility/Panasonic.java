package isobar.panasonic.utility;

import isobar.panasonic.components.Label;
import isobar.panasonic.entity.data.SiteCode;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class Panasonic {
    private static final String SHIPPINGVN = "Vận chuyển";
    private static final String SHIPPINGEN = "Shipping";
    private static final String SHIPPINGTH = "ข้อมูลการจัดส่ง";
    private static final String SHIPPINGID = "pengiriman";
    private static final String PAYMENTVN = "Đánh giá và thanh toán";
    private static final String PAYMENTEN = "Review & Payments";
    private static final String PAYMENTTH = "ยืนยันการจัดส่ง";
    private static final String PAYMENTID = "Tinjau & Pembayaran";
    private static final String LANGUAGE = "css=div.switcher-trigger";
    private static final String LABEL_SHIPPING = "xpath=(//ul[@class='opc-progress-bar']//span)[1]";
    private static final String LABEL_PAYMENT = "xpath=(//ul[@class='opc-progress-bar']//span)[2]";
    private static Map<WebDriver, WaitUtility> waitUtility = new HashMap<>();
    private static Map<WebDriver, String> siteCode = new HashMap<>();
    private static Map<WebDriver, String> langView = new HashMap<>();
    private static Map<WebDriver, Label> weLang = new HashMap<>();

    public static void assertEquals(String actual, String expected) {
        if (actual.equals(expected)) {
            ReportUtility.getInstance().logInfo(String.format("The same message: [%s] and [%s]", expected, actual));
        } else {
            ReportUtility.getInstance().logFail(String.format("expected [%s] but found [%s]", expected, actual));
        }
    }

    public static void checkStoreView(WebDriver driver) {
        SiteCode siteCode = DataTest.getSiteCode();
        if (!siteCode.equals(SiteCode.MY_EN)) {
            String currentUrl, storeView;
            currentUrl = driver.getCurrentUrl();
            setWeLang(driver, LANGUAGE);
            storeView = weLang.get(driver).getText().trim();
            ReportUtility.getInstance().logInfo(String.format("Current url: %s", currentUrl));
            boolean status = storeView.equals(DataTest.getSiteCode().toString());
            if (!status)
                ReportUtility.getInstance().logFail(String.format("Current store view: %s", storeView));
        }
    }

    public static void checkStoreViewOnShipping(WebDriver driver) {
        if (siteCode.get(driver) == null) {
            siteCode.put(driver, DataTest.getSiteCode().toString());
        }
        if (waitUtility.get(driver) == null) {
            waitUtility.put(driver,new WaitUtility(driver));
        }
        setWeLang(driver, LABEL_SHIPPING);
        waitUtility.get(driver).waitUntilVisibilityOf(weLang.get(driver).getWebElement());
        langView.put(driver, weLang.get(driver).getText().trim());
        if ((!langView.get(driver).equals(SHIPPINGEN)) && (siteCode.get(driver).equals(SiteCode.TH_EN.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
        if ((!langView.get(driver).equals(SHIPPINGTH)) && (siteCode.get(driver).equals(SiteCode.TH_TH.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
        if ((!langView.get(driver).equals(SHIPPINGID)) && (siteCode.get(driver).equals(SiteCode.ID_BA.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
        if ((!langView.get(driver).equals(SHIPPINGVN)) && (siteCode.get(driver).equals(SiteCode.VN_VN.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
    }

    public static void checkStoreViewOnPayment(WebDriver driver) {
        if (siteCode.get(driver) == null) {
            siteCode.put(driver, DataTest.getSiteCode().toString());
        }
        setWeLang(driver, LABEL_PAYMENT);
        langView.put(driver, weLang.get(driver).getText().trim());
        if ((!langView.get(driver).equals(PAYMENTEN)) && (siteCode.get(driver).equals(SiteCode.TH_EN.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
        if ((!langView.get(driver).equals(PAYMENTTH)) && (siteCode.get(driver).equals(SiteCode.TH_TH.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
        if ((!langView.get(driver).equals(PAYMENTID)) && (siteCode.get(driver).equals(SiteCode.ID_BA.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
        if ((!langView.get(driver).equals(PAYMENTVN)) && (siteCode.get(driver).equals(SiteCode.VN_VN.toString()))) {
            ReportUtility.getInstance().logFail(String.format("Display: %s but site code is: %s", langView, siteCode));
        }
    }

    private synchronized static void setWeLang(WebDriver driver, String locator) {
        Label weLangCurrent = weLang.get(driver);
        if (weLangCurrent == null) {
            weLangCurrent = new Label(driver, locator);
        } else {
            weLangCurrent.setLocator(locator);
        }
        weLang.put(driver, weLangCurrent);
    }
}
