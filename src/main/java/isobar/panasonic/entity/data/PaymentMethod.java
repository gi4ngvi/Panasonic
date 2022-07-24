package isobar.panasonic.entity.data;


public enum PaymentMethod {
    CREDIT_CARD, COD, CMO, ONEPAY_DOMESTIC,ONEPAY_INTERNATIONAL, STAFF_INSTALLMENT, CASH_CHEQUE;

    @Override
    public String toString() {
        switch (this) {
            case CREDIT_CARD:
                return "Credit Card (Cybersource)";
            case COD:
                return "Cash On Delivery";
            case CMO:
                return "Check / Money order";
            case ONEPAY_DOMESTIC:
                return "OnePAY Domestic";
            case ONEPAY_INTERNATIONAL:
                return "OnePAY International";
            case STAFF_INSTALLMENT:
                return "Staff Installment";
            case CASH_CHEQUE:
                return "Cash/ Cheque";
            default:
                return "Credit Card (Cybersource)";
        }
    }
}
