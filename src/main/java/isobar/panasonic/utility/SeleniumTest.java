package isobar.panasonic.utility;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.entity.data.Country;
import isobar.panasonic.page.bo.method.dashboard.BODashBoardPage;
import isobar.panasonic.page.bo.method.dashboard.BOLoginPage;
import isobar.panasonic.page.fo.method.account.CreatePasswordPage;
import isobar.panasonic.page.fo.method.account.RegisterPage;
import isobar.panasonic.page.fo.method.utility.HeaderPage;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class SeleniumTest {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public HeaderPage headerPage;
    public String RUN_ID;
    public static Map<WebDriver, String> className = new HashMap<>();
    public static Map<WebDriver, Integer> localCount = new HashMap<>();
    public static Map<WebDriver, Integer> globalCount = new HashMap<>();
    public static Map<WebDriver, String> homeURL = new HashMap<>();
    private boolean statusResult;
    private final static String USER_DIR = System.getProperty("user.dir");

    public synchronized static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeTest
    @Parameters({"country", "siteCode"})
    public void setUp(@Optional("vn") String country, @Optional("vn_vn") String siteCode, ITestContext ctx) {
        ReportUtility.init(ctx.getCurrentXmlTest().getSuite().getName());
        DataTest.init();
        DataTest.setSiteCode(siteCode);
        DataTest.setCountry(country);
        initBrowser();
        globalCount.put(getDriver(), 0);
        //  aShot = new AShot();
    }

    @BeforeClass
    public void beforeClass() {
        try {
            RUN_ID = String.valueOf(System.currentTimeMillis());
            headerPage = new HeaderPage(getDriver());
            localCount.put(getDriver(), 0);
            statusResult = true;
            className.put(getDriver(), getClass().getSimpleName());
            ReportUtility.getInstance().startTest(className.get(getDriver()));
            preCondition();
            headerPage.selectStore();
            if (DataTest.getCountry() != Country.MY) {
                headerPage.changeSite(DataTest.getSiteCode());
            }
            headerPage.closeCookiePopup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setUpBeforeMethod(Method method) {
        ReportUtility.getInstance().log(LogStatus.INFO, "<b>Start method: " + method.getName() + "</b>");
    }

    @AfterMethod
    public void cleanOrKeepTrack(ITestResult result, Method method) {
        if (result.getStatus() == ITestResult.FAILURE) {
            statusResult = false;
            try {
                ReportUtility.getInstance().log(LogStatus.ERROR, result.getThrowable().toString());
                for (StackTraceElement trace : result.getThrowable().getStackTrace()) {
                    if (!ignoreTrackTrace(trace.toString()))
                        ReportUtility.getInstance().log(LogStatus.ERROR, trace.toString());
                }
                extractJSLogs();
                getscreenshot();
                ReportUtility.getInstance().addScreenCapture(LogStatus.ERROR, "Screenshot\\" + DataTest.getSiteCode().getCode() + "_" + className.get(getDriver()) + localCount.get(getDriver()) + ".png");
                ReportUtility.getInstance().logFullPageInfo(getDriver(), DataTest.getSiteCode().getCode() + "_" + className.get(getDriver()) + "-" + method.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ReportUtility.getInstance().log(LogStatus.PASS, "<i>End method: " + result.getMethod().getMethodName() + "</i>");
        }
    }

    @AfterClass
    public void afterClass() throws TimeoutException {
        ReportUtility.getInstance().flush();
        if (statusResult) {
            File file = new File(USER_DIR + "\\test-output\\report\\" + className.get(getDriver()) + ".html");
            if (file.exists()) {
                file.delete();
            }
            if (getDriver().getCurrentUrl().contains("admin")) {
                getDriver().navigate().to(homeURL.get(getDriver()));
            }
        } else {
            getDriver().navigate().to(homeURL.get(getDriver()));
        }
        globalCount.put(getDriver(), globalCount.get(getDriver()) + 1);
        if (globalCount.get(getDriver()) == 5) {
            killWebDriver();
            initBrowser();
            globalCount.put(getDriver(), 0);
        }
    }

    @AfterTest
    public void cleanUp() {
        ReportUtility.getInstance().close();
        killWebDriver();
    }

    protected void preCondition() {
    }

    /*
     * Check picture
     */

    private void getscreenshot() throws Exception {
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        localCount.put(getDriver(), localCount.get(getDriver()) + 1);
        String screenshotPath = USER_DIR + "\\test-output\\report\\Screenshot\\" + DataTest.getSiteCode().getCode() + "_" + className.get(getDriver()) + localCount.get(getDriver()) + ".png";
        FileUtils.copyFile(scrFile, new File(screenshotPath));
    }

    /*
     *  kill driver instance
     */

    private void killWebDriver() {
        if (getDriver() != null)
            getDriver().quit();
    }

    /*
     *  init browser instance
     */

    private void initBrowser() throws TimeoutException {
        String browserType, driverExePath;
        WaitUtility waitU;
        WebDriver localDriver = null;
        File file = new File(USER_DIR + "//environment.json");
        try {
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONObject jsonObject = new JSONObject(content);
            browserType = jsonObject.getString("browser");
            if (browserType.equals("chrome")) {
                driverExePath = "C:\\test-driver\\chromedriver.exe";
                System.setProperty("webdriver.chrome.driver", driverExePath);

                ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--headless");
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                LoggingPreferences loggingprefs = new LoggingPreferences();
                loggingprefs.enable(LogType.BROWSER, Level.ALL);
                capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
                chromeOptions.merge(capabilities);
//                chromeOptions.addExtensions(new File(System.getProperty("user.dir") + "\\Unlimited Free VPN - Hola.crx"));
                localDriver = new ChromeDriver(chromeOptions);
            } else if (browserType.equals("firefox")) {
                localDriver = new FirefoxDriver();
            } else if (browserType.equals("ie")) {
                driverExePath = "C:\\test-driver\\IEDriverServer.exe";
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                System.setProperty("webdriver.ie.driver", driverExePath);
                localDriver = new InternetExplorerDriver(capabilities);
            }
            driver.set(localDriver);
            getDriver().manage().window().maximize();
            getDriver().manage().timeouts().implicitlyWait(jsonObject.getInt("object_wait"), TimeUnit.SECONDS);
            getHomeURL(jsonObject);
            waitU = new WaitUtility(getDriver());
            try {
                getDriver().get(homeURL.get(getDriver()));
            } catch (TimeoutException ex) {
                waitU.stopPageLoad();
            }
            waitU.sleep(2000);

            // handle how many threads
            DataTest.setThreadCount(jsonObject.getInt("thread_count"));

        } catch (IOException e) {
            ReportUtility.getInstance().log(LogStatus.ERROR, e.toString());
            ReportUtility.getInstance().log(LogStatus.ERROR, e.getMessage());
            e.printStackTrace();
        }
    }

    private void getHomeURL(JSONObject content) {
        homeURL.put(getDriver(), content.getJSONObject(DataTest.getEVN()).getString(DataTest.getSiteCode().getCode()));
    }

    public void extractJSLogs() {
        LogEntries logEntries = getDriver().manage().logs().get(LogType.BROWSER);
        String str;
        for (LogEntry entry : logEntries) {
            str = entry.getMessage();
            if (entry.getLevel() != Level.INFO && entry.getLevel() != Level.FINE
                    && !str.contains("jquery-migrate") && !str.contains("For more information")
                    && !str.contains("Expected to start loader but did not find one in the dom")
                    && !str.contains("moment.js 290")
                    && !str.contains("maps.googleapis")
                    && !str.contains("favicon.ico")
                    && !str.contains("This is discouraged and will be removed in upcoming major release"))
                ReportUtility.getInstance().log(LogStatus.WARNING, new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
    }

    private boolean ignoreTrackTrace(String log) {
        if (log.contains("org.testng")) {
            return true;
        } else if (log.contains("org.openqa.selenium.remote")) {
            return true;
        } else if (log.contains("sun.reflect")) {
            return true;
        } else if (log.contains("java.lang.reflect")) {
            return true;
        } else if (log.contains("org.apache.maven.surefire")) {
            return true;
        } else {
            return false;
        }
    }

    public HeaderPage launchFOSite() {
        getDriver().get(homeURL.get(getDriver()));
        return new HeaderPage(getDriver());
    }

    public BODashBoardPage launchBOSite() {
        File file = new File(System.getProperty("user.dir") + "//environment.json");
        WaitUtility waitU;
        try {
            String content = FileUtils.readFileToString(file, "utf-8");
            String url = getDriver().getCurrentUrl();
            waitU = new WaitUtility(getDriver());
            JSONObject jsonObject = new JSONObject(content);
            getDriver().navigate().to(DataTest.getAdminURL());
            waitU.waitForURLChange(url);
            waitU.waitForPageLoad();
            new BOLoginPage(getDriver()).login(jsonObject.getJSONObject("admin").getString("user"), jsonObject.getJSONObject("admin").getString("pass"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BODashBoardPage(getDriver());
    }

    public RegisterPage gotoRegisterPage(String url) {
        getDriver().get(url);
        return new RegisterPage(getDriver());
    }

    public CreatePasswordPage gotoCreatePasswordPage(String url) {
        getDriver().get(url);
        return new CreatePasswordPage(getDriver());
    }
}
