package isobar.panasonic.entity.data;

/**
 * Created by hieu nguyen on 12/7/2017.
 */

public enum CardType {
    CYBERSOURCE,
    ONEPAYINTERNATIONAL,
    ONEPAYDOMESTIC;

    public String getPaymentMethod() {
        switch (this) {
            case CYBERSOURCE:
                return "";
            default:
                return null;
        }
    }
}
