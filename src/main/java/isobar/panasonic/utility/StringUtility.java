package isobar.panasonic.utility;

/**
 * Created by user on 7/13/2017.
 */
public class StringUtility {
    public String removeSubString(String str, String subStr) {
        String localStr;
        int index = str.indexOf(subStr);
        if (index != -1) {
            localStr = str.substring(0, index) + str.substring(index + subStr.length(), str.length());
        } else {
            localStr = str;
        }
        return localStr;
    }

    public String getRandomKataKana() {
        String kata = "アイウエオカキクケコガギグゲゴサシスセソザジズゼゾタチツテトダヂヅデドナニヌネノハヒフヘホバビブベボパピプペポマミムメモヤユヨラリルレロワヲン";
        String randomKata = "";
        for (int i = 0; i < 4; i++) {
            randomKata += kata.charAt((int) (Math.random() * 71));
        }
        return randomKata;
    }

    public String getRandomCharacter() {
        int range = 3;
        String kata = "qQwWeErRtTyYuUiIoOpPaAsSdDfFgGhHjJkKlLzZxXcCvVbBnNmM";
        String randomKata = "";
        for (int i = 0; i < range; i++) {
            randomKata += kata.charAt((int) (Math.random() * kata.length()));
        }
        return randomKata;
    }

    public String getRandomCharacter(int range) {
        String kata = "qQwWeErRtTyYuUiIoOpPaAsSdDfFgGhHjJkKlLzZxXcCvVbBnNmM";
        String randomKata = "";
        for (int i = 0; i < range; i++) {
            randomKata += kata.charAt((int) (Math.random() * kata.length()));
        }
        return randomKata;
    }

    public String translateEnglishToThai(String data) {
        /*switch (data){
            case "Nong Prue": return "หนองปรือ";
            case "Nong Pla Lai": return "หนองปลาไหล";
            case "Somdej Charoen": return "สมเด็จเจริญ";
            default: return data;
        }*/
        return data;
    }

    public String capitalizeFirstLetter(String dayOfWeek) {
        String[] letters = dayOfWeek.split("");
        letters[0] = letters[0].toUpperCase();
        return String.join("", letters);
    }
}
