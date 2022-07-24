package isobar.panasonic.utility;

import isobar.panasonic.entity.data.CardType;
import isobar.panasonic.entity.data.CreditCard;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;


public class CardAccount {
    JSONObject jsonCreditCard, localJsonObject;
    File file;
    String content;

    public CreditCard creditCard(CardType card) {

        try {
            file = new File(System.getProperty("user.dir") + "\\card.json");
            content = FileUtils.readFileToString(file, "utf-8");
            jsonCreditCard = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (card) {
            case CYBERSOURCE:
                localJsonObject = jsonCreditCard.getJSONObject("Visa");
                break;
            case ONEPAYINTERNATIONAL:
                localJsonObject = jsonCreditCard.getJSONObject("Visa1");
                break;
            case ONEPAYDOMESTIC:
                localJsonObject = jsonCreditCard.getJSONObject("VCB");
                break;
            default:
                break;
        }

        return new CreditCard.Builder().creditCardNumber(localJsonObject.getString("number"))
                .creditCardName(localJsonObject.getString("name"))
                .expMonth(localJsonObject.getString("month"))
                .expYear(localJsonObject.getString("year"))
                .securityCode(localJsonObject.getString("cvv"))
                .password3D(localJsonObject.getString("password"))
                .build();
    }
}
