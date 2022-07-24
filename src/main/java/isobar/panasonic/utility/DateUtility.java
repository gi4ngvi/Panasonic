package isobar.panasonic.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtility {
    SimpleDateFormat dateFormat;

    public DateUtility() {
    }

    public int getDate(String date) {
        String str;
        if (date.contains("/"))
            str = date.split("/")[2];
        else
            str = date.split("-")[2];
        if (str.startsWith("0"))
            return Integer.valueOf(str.substring(1, 2));
        return Integer.valueOf(str);
    }

    public int getMonth(String date) {
        String str;
        if (date.contains("/"))
            str = date.split("/")[1];
        else
            str = date.split("-")[1];
        if (str.startsWith("0"))
            return Integer.valueOf(str.substring(1, 2));
        return Integer.valueOf(str);
    }

    public int getYear(String date) {
        String str;
        if (date.contains("/"))
            str = date.split("/")[0];
        else
            str = date.split("-")[0];
        return Integer.valueOf(str);
    }

    public int getCurrentYear() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear();
    }

    public int getCurrentMonth() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue();
    }

    public int getCurrentHour() {
        return LocalDateTime.now().getHour();
    }

    public int getDayOfMonth() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfMonth();
    }

    //    public static String getCurrentDate(String formatDate) {
//        String str;
//        DateFormat dateFormat;
//        Date date1 = new Date();
//        dateFormat = new SimpleDateFormat(formatDate);
//        str = dateFormat.format(date1);
//        return str;
//    }
    public static String getCurrentDate() {
        String formatDate = "dd/M/yyyy";
        Date date = new Date();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        LocalDateTime localDate;
        localDate = date.toInstant().atZone(ZoneId.of("UTC+7")).toLocalDateTime();
        String today = localDate.format(formatter);
        String d = today.split("/")[0];
        String m = today.split("/")[1];
        String y = today.split("/")[2];
        return String.format("Ngày %s tháng %s năm %s", d, m, y);
    }

    public int convertMonthFromStringToNumber(String month) {
        try {
            return Integer.parseInt(month);
        } catch (NumberFormatException ex) {
        }

        int localMonth;
        switch (month) {
            case "January":
            case "Jan":
                localMonth = 1;
                break;
            case "February":
            case "Feb":
                localMonth = 2;
                break;
            case "March":
            case "Mar":
                localMonth = 3;
                break;
            case "April":
            case "Apr":
                localMonth = 4;
                break;
            case "May":
                localMonth = 5;
                break;
            case "June":
            case "Jun":
                localMonth = 6;
                break;
            case "July":
            case "Jul":
                localMonth = 7;
                break;
            case "August":
            case "Aug":
                localMonth = 8;
                break;
            case "September":
            case "Sep":
                localMonth = 9;
                break;
            case "October":
            case "Oct":
                localMonth = 10;
                break;
            case "November":
            case "Nov":
                localMonth = 11;
                break;
            default:
                localMonth = 12;
                break;
        }
        return localMonth;
    }

    public String formatJapaneseDate(String date) {
        String[] str;
        String localDate;
        str = date.split("-");
        localDate = str[0] + "年";

        if (str[1].startsWith("0"))
            localDate += str[1].substring(1) + "月";
        else
            localDate += str[1] + "月";

        if (str[2].startsWith("0"))
            localDate += str[2].substring(1) + "日";
        else
            localDate += str[2] + "日";
        return localDate;
    }

    public String nextDate(String currentDate, String currentformat, String cycle) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        boolean flat = false;
        String str = null;
        if (!currentformat.equals("yyyy-MM-dd")) {
            currentDate = changeFormatDate(currentDate, currentformat, "yyyy-MM-dd");
            flat = true;
        }

        try {
            date = dateFormat.parse(currentDate);
        } catch (ParseException ex) {
        }

        if (cycle.equals("1 week"))
            str = LocalDate.parse(dateFormat.format(date)).plusDays(7).toString();
        else if (cycle.equals("2 weeks"))
            str = LocalDate.parse(dateFormat.format(date)).plusDays(14).toString();
        else if (cycle.equals("3 weeks"))
            str = LocalDate.parse(dateFormat.format(date)).plusDays(21).toString();
        else if (cycle.equals("1 month"))
            str = LocalDate.parse(dateFormat.format(date)).plusMonths(1).toString();

        if (flat == true)
            str = changeFormatDate(str, "yyyy-MM-dd", currentformat);
        return str;
    }

    public String nextDate(String currentDate, String formatDate, int hour) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(formatDate);
        Date date = dateFormat.parse(currentDate);
        long long_date = date.getTime() + 1000 * 60 * 60 * hour;
        date.setTime(long_date);
        return dateFormat.format(date);
    }

    public String changeFormatDate(String date, String oldFormat, String newFormat) {
        Date localDate = null;
        dateFormat = new SimpleDateFormat(oldFormat);
        try {
            localDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat(newFormat);
        return dateFormat.format(localDate).toString();
    }

    public long convertDateTimeInstanceToMiliSecond(String date, String format) {
        dateFormat = new SimpleDateFormat(format);
        long time = 0l;
        try {
            Date localDate = dateFormat.parse(date);
            time = localDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public String changeInstanceFormatDate(String date, String format, String style) {
        Date localDate = null;
        DateFormat dateFormatter;
        dateFormat = new SimpleDateFormat(format);
        try {
            localDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (style) {
            case "DEFAULT":
                dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US);
                break;
            case "SHORT":
                dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
                break;
            case "MEDIUM":
                dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
                break;
            case "LONG":
                dateFormatter = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
                break;
            default:
                dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
                break;

        }
        return dateFormatter.format(localDate);
    }

    public static String getCurrentDateCN(String formatDate) {
        return formatChineseDate(convertToCurrentDateCN(formatDate));
    }

    public static String convertToCurrentDateCN(String formatDate) {
        DateFormat dateFormat = new SimpleDateFormat(formatDate);
        Date date = new Date();
        long long_date = date.getTime() + 1000 * 60 * 60;
        date.setTime(long_date);
        return dateFormat.format(date).toString();
    }

    public static String formatChineseDate(String date) {
        String[] str;
        String localDate;
        str = date.split("/");
        localDate = str[0] + "年";
        localDate += str[1] + "月";
        localDate += str[2] + "日";
        return localDate;
    }

    public static long getTime(String datetime, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(datetime);
            return date.getTime();
        } catch (ParseException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public LocalDate getPlusDate(int dayToAdd) {
        return LocalDate.now().plusDays(dayToAdd);
    }

    public String formatDate(LocalDate date, String formatDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        return date.format(formatter);
    }

    public  boolean checkFormatDate(String strDate, String dateFormat) {
        if (strDate.trim().equals("")) {
            return false;
        }
        SimpleDateFormat sdfrmt = new SimpleDateFormat(dateFormat);
        sdfrmt.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            sdfrmt.parse(strDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
