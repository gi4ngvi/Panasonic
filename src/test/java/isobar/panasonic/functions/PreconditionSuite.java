package isobar.panasonic.functions;

import isobar.panasonic.utility.FileUtility;
import org.testng.annotations.Test;

import java.io.File;

public class PreconditionSuite {
    private FileUtility fileUtility;

    @Test
    public void deleteResultFolder() {
        fileUtility = new FileUtility();
        fileUtility.deleteFolder(new File(System.getProperty("user.dir") + "\\test-output"));
    }
}