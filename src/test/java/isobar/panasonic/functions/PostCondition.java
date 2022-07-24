package isobar.panasonic.functions;

import isobar.panasonic.utility.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostCondition {
    private SendMail mail;
    private Zip zip;
    private FileUtility fileUtility;
    private List<String> suiteNameLists;

    @Test
    public synchronized void checkReport(ITestContext iTestContext) {
        fileUtility = new FileUtility();
        String filePath = System.getProperty("user.dir") + "\\test-output\\lstSite.txt";
        suiteNameLists = fileUtility.readFile(filePath);
        if (suiteNameLists.size() == DataTest.getThreadCount() - 1) {
            sendResultFail();
        }
         addSuiteName(iTestContext.getSuite().getName());
    }

    private synchronized void addSuiteName(String suiteName) {
        suiteNameLists.add(suiteName);
        ReportUtility.getInstance().logInfo("Suite name is: " + suiteName);
        fileUtility.writeFile(System.getProperty("user.dir") + "\\test-output\\lstSite.txt", String.join("\n", suiteNameLists));
    }

    private synchronized void sendResultFail() {
        String from, to, cc, pass, reportDate, pathAttachedFile;
        reportDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String title = "Panasonic Automation Result " + reportDate;
        String content = "Send report of fail test";
        String attachFile = System.getProperty("user.dir") + "\\test-output\\report";
        File folder = new File(attachFile);
        File[] files = folder.listFiles();
        pathAttachedFile = attachFile + reportDate;
        if (files.length > 0) {
            mail = new SendMail();
            zip = new Zip();
            File file = new File(System.getProperty("user.dir") + "//environment.json");
            String contentT = null;
            try {
                contentT = FileUtils.readFileToString(file, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject(contentT);
            from = jsonObject.getString("email_from");
            pass = jsonObject.getString("email_pass");
            to = jsonObject.getString("email_to");
            cc = jsonObject.getString("email_cc");
            if (cc.equals("null"))
                cc = null;
            try {
                zip.zipFolder(attachFile, pathAttachedFile + ".zip");
            } catch (Exception e) {
                e.printStackTrace();
            }
            mail.sendGMail(from, pass, to, cc, title, content, pathAttachedFile + ".zip");
            try {
                FileUtils.forceDelete(new File(pathAttachedFile + ".zip"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
