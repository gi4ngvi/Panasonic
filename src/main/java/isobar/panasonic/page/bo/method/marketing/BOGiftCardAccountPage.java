package isobar.panasonic.page.bo.method.marketing;

import isobar.panasonic.components.Table;
import isobar.panasonic.entity.data.GiftCard;
import isobar.panasonic.page.bo.locator.marketing.BOGiftCardAccountLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class BOGiftCardAccountPage extends BOGiftCardAccountLocator {
    protected String latestID;
    protected Table gift_table;

    public BOGiftCardAccountPage(WebDriver driver) {
        super(driver);
        initComponent();
    }

    private void initComponent() {
        gift_table = new Table(driver, GIFTCARD_TABLE);
        latestID = gift_table.getTableCellValueDueToHeader(HEADER_ID);
    }

    public void clickAdd() {
        weAdd.click();
    }

    public void fillInInfomation(GiftCard giftCard) {
        selectWebsite(giftCard.getWebsite());
        enterBalance(giftCard.getBalance());
    }

    private void selectWebsite(String key) {
        Select select = new Select(weWebsite);
        select.selectByVisibleText(key);
    }

    private void enterBalance(double balance) {
        weBalance.clear();
        weBalance.sendKeys(String.valueOf(balance));
    }

    private String getGiftCard() {
        String code = gift_table.getTableCellValueDueToHeader(HEADER_CODE);
        String currentID = gift_table.getTableCellValueDueToHeader(HEADER_ID);
        waitUtility.waitUntilToBeClickAble(weAdd);
        return currentID.equals(latestID) ? null : code;
    }

    private void saveGiftCard() {
        weSave.click();
    }

}
