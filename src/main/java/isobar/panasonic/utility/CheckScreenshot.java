package isobar.panasonic.utility;

import com.relevantcodes.extentreports.LogStatus;
import isobar.panasonic.entity.data.Platform;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by hieu.nguyen on 3/2/2018.
 */

public class CheckScreenshot {
    private static CheckScreenshot ourInstance = new CheckScreenshot();
    private ImageUtility imageUtility;
    private String IMAGE_TEMP_DIR, IMAGE_BASELINE_DIR;
    private final static String USER_DIR = System.getProperty("user.dir");
    //private static final int SAISO_RGB = 1;

    private BufferedImage actualImage, expectedImage;

    public static CheckScreenshot getInstance() {
        return ourInstance;
    }

    private CheckScreenshot() {
        imageUtility = new ImageUtility();
        preCondition();
    }

    public void check(WebDriver driver, String pictureName, int x, int y, int width, int height, CaptureImage.PICTURE_TYPE type, String description, double tolerant) {
        String imagePath = pictureName + "." + type.toString();
        String imageDifferentPath = pictureName + "_tam." + type.toString();
        double diff;
        boolean statusPic = true; // check picture false

        try {
            CaptureImage.getInstance().captureImage(driver, IMAGE_TEMP_DIR + imagePath, x, y, width, height, type);
            actualImage = ImageIO.read(new File(IMAGE_BASELINE_DIR + imagePath));
            expectedImage = ImageIO.read(new File(IMAGE_TEMP_DIR + imagePath));
            diff = imageUtility.getDifferencePercent(actualImage, expectedImage);

            if (diff > tolerant) {
                ReportUtility.getInstance().log(LogStatus.FAIL, pictureName + " - " + description);
                statusPic = false;
            } else {
                ReportUtility.getInstance().log(LogStatus.PASS, pictureName + " - " + description);
            }

            ImageIO.write(imageUtility.getDifferentImage(actualImage, expectedImage), type.toString(), new File(IMAGE_TEMP_DIR + imageDifferentPath));
            FileUtils.copyFile(new File(IMAGE_BASELINE_DIR + imagePath), new File(USER_DIR + DataTest.getReportPath() + imagePath));
            FileUtils.copyFile(new File(IMAGE_TEMP_DIR + imagePath), new File(USER_DIR + DataTest.getReportPath() + "cu" + imagePath));

            if (!statusPic) {
                FileUtils.copyFile(new File(IMAGE_TEMP_DIR + imageDifferentPath), new File(USER_DIR + DataTest.getReportPath() + imageDifferentPath));
                ReportUtility.getInstance().addScreenCapture(LogStatus.FAIL, imagePath, "cu" + imagePath, imageDifferentPath);
            } else {
                ReportUtility.getInstance().addScreenCapture(LogStatus.PASS, imagePath, "cu" + imagePath);
            }

            FileUtils.forceDelete(new File(IMAGE_TEMP_DIR + imageDifferentPath));
        } catch (IOException e) {
            ReportUtility.getInstance().log(LogStatus.WARNING, "This verification have not baseline to check yet");
            e.printStackTrace();
        }
    }

    private void preCondition() {
        File file, localFile;
        String content;
        JSONObject uiConfig;
        String localPath;

        if (DataTest.getPlatform() == Platform.PC) {
            file = new File(USER_DIR + "\\ui_config_desktop.json");
        } else {
            file = new File(USER_DIR + "\\ui_config_mobile.json");
        }
        try {
            content = FileUtils.readFileToString(file, "utf-8");
            uiConfig = new JSONObject(content);
            localPath = uiConfig.getString("jenkin_path");
            IMAGE_TEMP_DIR = localPath + "\\temp\\";
            IMAGE_BASELINE_DIR = localPath + "\\baseline\\";

            localFile = new File(localPath);
            if (!localFile.exists())
                localFile.mkdirs();

            localFile = new File(IMAGE_TEMP_DIR);
            if (!localFile.exists())
                localFile.mkdir();

            localFile = new File(IMAGE_BASELINE_DIR);
            if (!localFile.exists())
                localFile.mkdir();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
