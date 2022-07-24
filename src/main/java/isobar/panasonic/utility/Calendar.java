package isobar.panasonic.utility;

import isobar.panasonic.components.Button;
import isobar.panasonic.components.Label;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Calendar {
    private final static String LOCATOR_NEXT_BUTTON = "css=a.ui-datepicker-next.ui-corner-all";
    private final static String DAY = "xpath=//table[@class='ui-datepicker-calendar']//a[text()='%1$s']";
    private final static String MONTH = "css=span.ui-datepicker-month";
    private final static String YEAR = "css=span.ui-datepicker-year";
    private final static String MONTH_SELECT = "css=select.ui-datepicker-month";
    private final static String YEAR_SELECT = "css=select.ui-datepicker-year";
    private WebDriver driver;
    private Button btnNext;
    private Label lbDay;
    private Label lbGeneral;
    private DateUtility dateUtility;

    public Calendar(WebDriver driver) {
        this.driver = driver;
        initComponents();
    }

    private void initComponents() {
        dateUtility = new DateUtility();
        btnNext = new Button(driver, LOCATOR_NEXT_BUTTON);
        lbGeneral = new Label(driver);
    }

	/*
	 *  Format date: yyyy-mm-dd
	 */

    public void selectDate_Select(String date) {
        int day = dateUtility.getDate(date);
        int month = dateUtility.getMonth(date);
        int year = dateUtility.getYear(date);
        lbGeneral.setLocator(YEAR_SELECT);
        new Select(lbGeneral.getWebElement()).selectByVisibleText(String.valueOf(year));
        lbGeneral.setLocator(MONTH_SELECT);
        new Select(lbGeneral.getWebElement()).selectByVisibleText(convertMonthFromIntToShortString(month));
        lbDay = new Label(driver, String.format(DAY, day));
        lbDay.click();
    }

    public void selectDate(String date) {
        int day = dateUtility.getDate(date);
        int month = dateUtility.getMonth(date);
        int year = dateUtility.getYear(date);
        lbGeneral.setLocator(YEAR);
        int currentYear = Integer.parseInt(lbGeneral.getText());
        lbGeneral.setLocator(MONTH);
        int currentMonth = convertMonthFromStringToInt(lbGeneral.getText());
        currentMonth += (currentYear - year) * 12;
        selectMonth(month, currentMonth);
        lbDay = new Label(driver, String.format(DAY, day));
        lbDay.click();
    }

    private void selectMonth(int expectedMonth, int currentMonth) {
        if (currentMonth < expectedMonth) {
            for (int i = 0; i < expectedMonth - currentMonth; i++) {
                btnNext.click();
                btnNext.sleep(200);
            }
        }
    }

    private String convertMonthFromIntToShortString(int month){
        switch (month){
            case 1: return "Jan";
            case 2: return "Feb";
            case 3: return "Mar";
            case 4: return "Apr";
            case 5: return "May";
            case 6: return "Jun";
            case 7: return "Jul";
            case 8: return "Aug";
            case 9: return "Sep";
            case 10: return "Oct";
            case 11: return "Nov";
            case 12: return "Dec";
            default:return "";
        }
    }

    private int convertMonthFromStringToInt(String month) {
        int localMmonth;
        switch (month) {
            case "January":
            case "Jan":
                localMmonth = 1;
                break;
            case "Febuary":
            case "Feb":
                localMmonth = 2;
                break;
            case "March":
            case "Mar":
                localMmonth = 3;
                break;
            case "April":
            case "Apr":
                localMmonth = 4;
                break;
            case "May":
                localMmonth = 5;
                break;
            case "June":
            case " Jun":
                localMmonth = 6;
                break;
            case "July":
            case "Jul":
                localMmonth = 7;
                break;
            case "August":
            case "Aug":
                localMmonth = 8;
                break;
            case "September":
            case "Sep":
                localMmonth = 9;
                break;
            case "October":
            case "Oct":
                localMmonth = 10;
                break;
            case "November":
            case "Nov":
                localMmonth = 11;
                break;
            default:
                localMmonth = 12;
                break;
        }
        return localMmonth;
    }
}
