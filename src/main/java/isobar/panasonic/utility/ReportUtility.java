package isobar.panasonic.utility;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by hieu.nguyen on 12/19/2017.
 */

public class ReportUtility {
    ExtentReports report;
    ExtentTest logger;
    private static ThreadLocal<ReportUtility> ourInstance = new ThreadLocal<>();
    private final static String USER_DIR = System.getProperty("user.dir");

    public static void init(String suiteName) {
        ReportUtility localOurInstance = new ReportUtility(suiteName);
        ourInstance.set(localOurInstance);
    }

    public static ReportUtility getInstance() {
        return ourInstance.get();
    }

    private ReportUtility(String suiteName) {
        String reportName = "Report_" + suiteName + ".html";
        report = new ExtentReports(USER_DIR + "\\test-output\\report\\" + reportName);
        report.loadConfig(new File(USER_DIR + "\\extent-config.xml"));
    }

    public void log(LogStatus status, String content) {
        logger.log(status, encodingContent(content));
    }

    public void logInfo(String content) {
        logger.log(LogStatus.INFO, encodingContent(content));
    }

    public void logFail(String content) {
        logger.log(LogStatus.FAIL, encodingContent(content));
    }

    public void logError(String content) {
        logger.log(LogStatus.ERROR, encodingContent(content));
    }

    public void addScreenCapture(LogStatus status, String path) {
        logger.log(status, logger.addScreenCapture(path));
    }

    public void addScreenCapture(LogStatus status, String path1, String path2) {
        logger.log(status, "<td>" + logger.addScreenCapture(path1) + "</td><td>" + logger.addScreenCapture(path2) + "</td>");
    }

    public void addScreenCapture(LogStatus status, String path1, String path2, String path3) {
        logger.log(status, "<td>" + logger.addScreenCapture(path1) + "</td><td>" + logger.addScreenCapture(path2) + "</td><td>" + logger.addScreenCapture(path3) + "</td>");
    }

    public void startTest(String testName) {
        logger = report.startTest(testName);
    }

    public void flush() {
        report.endTest(logger);
        report.flush();
    }

    public void close() {
        report.close();
    }

    private String encodingContent(String content) {
        try {
            byte ptext[] = content.getBytes("UTF-8");
            return new String(ptext);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }

    public void logFullPageInfo(WebDriver driver, String fileName) {
        String content, path, logMessage;
        content = driver.getPageSource();
        try {
            path = USER_DIR + "\\test-output\\report\\PageInfo\\" + fileName + ".html";
            new FileUtility().write(path, content);
            logMessage = "HTML record: <a target='_blank' href='PageInfo\\" + fileName + ".html" + "'>" + fileName + ".html" + "</a>";
            logInfo(logMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logFullPageInfo(WebDriver driver, LogStatus status, String fileName) {
        String content, path, logMessage;
        content = driver.getPageSource();
        try {
            path = USER_DIR + "\\test-output\\report\\PageInfo\\" + fileName + ".html";
            new FileUtility().write(path, content);
            logMessage = "HTML record: <a target='_blank' href='" + fileName + ".html" + "'>" + fileName + ".html" + "</a>";
            log(status, logMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
