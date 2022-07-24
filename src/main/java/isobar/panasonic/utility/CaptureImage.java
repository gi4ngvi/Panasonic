package isobar.panasonic.utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CaptureImage {

    public enum PICTURE_TYPE {
        JPEG, PNG;

        public String toString() {
            String type;
            switch (this) {
                case PNG:
                    type = "png";
                    break;
                case JPEG:
                    type = "jpeg";
                    break;
                default:
                    type = "";
                    break;
            }
            return type;
        }
    }

    private static CaptureImage instance;

    private CaptureImage() {
    }

    public static CaptureImage getInstance() {
        if (instance == null) {
            instance = new CaptureImage();
        }
        return instance;
    }

    public void captureImage(WebDriver driver, String path) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void captureImage(WebDriver driver, String path, int x, int y, int width, int height, PICTURE_TYPE type) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage fullImage = ImageIO.read(scrFile);
            BufferedImage subImage = fullImage.getSubimage(x, y, width, height);
            ImageIO.write(subImage, type.toString(), scrFile);
            FileUtils.copyFile(scrFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCapturedImage(String path) {
        FileUtility fileUtility = new FileUtility();
        File file = new File(path);
        fileUtility.deleteFolder(file);
    }
}
