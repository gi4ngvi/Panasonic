package isobar.panasonic.utility;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by hieu.nguyen on 3/15/2018.
 */

public class JenkinsFTP {
    private File configFile;
    private JSONObject uiConfig;
    private FTPUtility ftpUtils;
    private String localPath, remotePath;

    public JenkinsFTP(File configFile) {
        this.configFile = configFile;
        initComponents();
    }

    private void initComponents() {
        File localFile;
        String content;
        JSONObject ftp;
        String server, user, pass;
        int port;

        try {
            content = FileUtils.readFileToString(configFile, "utf-8");
            uiConfig = new JSONObject(content);
            ftp = uiConfig.getJSONObject("ftp");
            server = ftp.getString("ip");
            port = ftp.getInt("port");
            user = ftp.getString("user");
            pass = ftp.getString("password");
            localPath = uiConfig.getString("jenkin_path");
            remotePath = uiConfig.getString("remote_path");

            localFile = new File(localPath);
            if (!localFile.exists())
                localFile.mkdirs();

            ftpUtils = new FTPUtility(server, port, user, pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void dowloadBaselineScreenshot() {
        ftpUtils.connect();
        new File(localPath + "\\baseline").mkdirs();
        ftpUtils.dowloadDirectory(localPath + "\\baseline", remotePath + "/baseline");
        ftpUtils.disconnect();
    }

    public void uploadTempScreenshot() {
        ftpUtils.connect();
        ftpUtils.uploadDirectory(localPath + "\\temp", remotePath + "/temp");
        ftpUtils.disconnect();
    }

}
