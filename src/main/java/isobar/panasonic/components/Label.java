package isobar.panasonic.components;


import org.openqa.selenium.WebDriver;

public class Label extends Component {

    public Label(WebDriver driver, String locator) {
        super(driver, locator);
    }

    public Label(WebDriver driver) {
        super(driver);
    }
}
