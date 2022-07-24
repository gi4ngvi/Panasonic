package isobar.panasonic.page.fo.method.account;

import isobar.panasonic.components.Label;
import isobar.panasonic.page.fo.locator.account.MyCreditCardLocator;
import org.openqa.selenium.WebDriver;

public class MyCreditCardPage extends MyCreditCardLocator {
    public MyCreditCardPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkCard(String cardNumber) {
        Label weCard = findCard(cardNumber);
        return weCard.isDisplay();
    }

    public void deleteCard(String cardNumber) {
        Label weCard = findCard(cardNumber);
        weCard.click();
    }

    private Label findCard(String cardNumber) {
        String lastThreeNumberOfCard = cardNumber.substring(cardNumber.length() - 3, cardNumber.length());
        return new Label(driver, String.format(CARD_ITEM, lastThreeNumberOfCard));
    }
}
