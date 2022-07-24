package isobar.panasonic.utility;

public class PriceUtility {

    public static String convertPriceToString(int price) {
        switch (DataTest.getSiteCode()) {
            case VN_VN:
                return convertPriceToStringWithFormat(price, "₫");
            case VN_EN:
                return convertPriceToStringVNEN(price, "₫");
            case ID_EN:
            case TH_EN:
                return convertPriceToString(price, "Points");
            case ID_BA:
                return convertPriceToStringWithFormat(price, "Poin");
            case TH_TH:
                return convertPriceToString(price, "คะแนน");
            case MY_EN:
                return convertPriceToStringMY(price, "MYR");
            default:
                return null;
        }
    }

    private static String convertPriceToStringVN(int price, String currency) {
        //work around format currency
        if (price < 10) return String.format("0%d,00", price) + currency;
        String priceFormat = String.format("%,d.00", price) + currency;
        int position = priceFormat.indexOf(".");
        if (position != -1) {
            return priceFormat.substring(0, position).replace(",", ".") +
                    priceFormat.substring(position).replace(".", ",");
        }
        return priceFormat.replaceAll(",", ".");
    }

    private static String convertPriceToString(double price, String currency) {
        return String.format("%,.0f", price) + " " + currency;
    }

    private static String convertPriceToStringWithFormat(double price, String currency) {
        String priceFormat = String.format("%,.0f", price);
        return priceFormat.replace(",", ".") + " " + currency;
    }


    public static int convertPriceStringToNumber(String pointStr) {
        String pointNumber = pointStr.replaceAll("[^0-9]", "");
        return Integer.parseInt(pointNumber);
    }

    private static String convertPriceToStringMY(double price, String currency) {
        return currency + String.format("%,.2f", price);
    }

    private static String convertPriceToStringVNEN(double price, String currency) {
        return String.format("%,.0f", price) + " " + currency;
    }
}