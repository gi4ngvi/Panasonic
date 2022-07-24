package isobar.panasonic.components;


import isobar.panasonic.utility.ActionUtility;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Table extends Component {
    private String tableXpath, tableXpathColumnHeader, tableXpathColumn, tableXpathCell;
    private List<WebElement> webElements;
    private Label cell;
    private int column;
    private boolean status = false;
    private String str;
    private ActionUtility actionUtility;

    public Table(WebDriver driver, String locator) {
        super(driver, locator);
        initComponent();
    }

    private void initComponent() {
        tableXpath = this.getLocator();
        tableXpathColumnHeader = tableXpath + "//th";
        tableXpathColumn = tableXpath + "/tbody//td[%1$d]";
        tableXpathCell = tableXpath + "/tbody/tr[%1$d]/td[%2$d]";
        cell = new Label(driver);
        webElements = new ArrayList<>();
        actionUtility = new ActionUtility(driver);
    }

    public int getColumnDueToHeader(String headerLabel) {
        column = 1;
        status = false;
        cell.setLocator(tableXpathColumnHeader);
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getText();
            if (str.equals(headerLabel)) {
                status = true;
                break;
            }
            column += 1;
        }
        if (status == false)
            return -1;
        return column;
    }

    public boolean checkTableCellValueDueToHeader(String headerLabel, String data) {
        column = getColumnDueToHeader(headerLabel);
        if (column == -1)
            return false;
        cell.setLocator(String.format(tableXpathColumn, column));
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getText();
            if (str.equals(data))
                return true;
        }
        return false;
    }

    public int getColumnDueToClass(String headerClass) {
        column = 1;
        status = false;
        cell.setLocator(tableXpathColumnHeader);
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getAttribute("class");
            if (str.equals(headerClass)) {
                status = true;
                break;
            }
            column += 1;
        }
        if (status == false)
            return -1;
        return column;
    }

    public boolean checkTableCellValueDueToClass(String headerClass, String data) {
        column = getColumnDueToClass(headerClass);
        if (column == -1)
            return false;
        cell.setLocator(String.format(tableXpathColumn, column));
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getText();
            if (str.equals(data))
                return true;
        }
        return false;
    }

    public boolean checkTableCellValueDueToHeader(String headerLabel, String data, int row) {
        column = getColumnDueToHeader(headerLabel);
        if (column == -1)
            return false;
        cell.setLocator(String.format(tableXpathCell, row, column));
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getText().replaceAll("\n", "");
            if (str.equals(data))
                return true;
        }
        return false;
    }

    public boolean checkTableCellValue(int row, int column, String data) {
        cell.setLocator(String.format(tableXpathCell, row, column));
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getText().replaceAll("\n", "");
            if (str.equals(data))
                return true;
        }
        return false;
    }

    public boolean checkTableCellValueDueToHeader(String headerLabel, int data) {
        return checkTableCellValueDueToHeader(headerLabel, String.valueOf(data));
    }

    public boolean checkTableCellContainValueDueToHeader(String headerLabel, String data) {
        column = getColumnDueToHeader(headerLabel);
        if (column == -1)
            return false;
        cell.setLocator(String.format(tableXpathColumn, column));
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getText();
            if (str.contains(data))
                return true;
        }
        return false;
    }

    public String getTableCellValueDueToHeader(String headerLabel) {
        column = getColumnDueToHeader(headerLabel);
        if (column == -1)
            return null;
        cell.setLocator(String.format(tableXpathCell, 1, column));
        webElements = cell.getWebElements();
        return webElements.get(0).getText();
    }

    public int[] getTableCellContainingDueToHeader(String headerLabel, String data) throws StaleElementReferenceException {
        column = getColumnDueToHeader(headerLabel);
        int[] position = new int[2];
        int count = 0;
        if (column == -1)
            return position;
        position[1] = column;

        cell.setLocator(String.format(tableXpathColumn, column));
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            count++;
            str = ele.getText();
            if (str.equals(data))
                break;
        }
        position[0] = count;
        return position;
    }

    public void clickTableCell(int row, int column) {
        cell.setLocator(String.format(tableXpathCell, row, column));
        cell.click();
    }

    public void clickTableCell_Input(int row, int column) {
        cell.setLocator(String.format(tableXpathCell, row, column) + "//input");
        //cell.click();
        actionUtility.mouseClick(cell.getWebElement());
    }

    public void clickTableCell_Button(int row, int column) {
        cell.setLocator(String.format(tableXpathCell, row, column) + "/button");
        cell.click();
    }

    public void setTableCellValue(int row, int column, int data) {
        cell.setLocator(String.format(tableXpathCell, row, column) + "//input");
        cell.enter(data);
    }

    public void setTableCellValue(int row, int column, String data) {
        cell.setLocator(String.format(tableXpathCell, row, column) + "//input");
        cell.enter(data);
    }

    public List<String> getAllDataColumnDueToHeader(String headerLabel) {
        List<String> data = new ArrayList<>();
        column = getColumnDueToHeader(headerLabel);
        if (column == -1)
            return null;
        cell.setLocator(String.format(tableXpathColumn, column));
        webElements = cell.getWebElements();
        for (WebElement ele : webElements) {
            str = ele.getText();
            data.add(str);
        }
        return data;
    }
}
