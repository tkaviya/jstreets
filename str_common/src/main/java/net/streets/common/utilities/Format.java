package net.streets.common.utilities;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 7/20/13
 * Time: 8:33 PM
 */
public class Format {

    static final String[] VALID_DATE_FORMATS = new String[]{"dd/MM/yyyy", "dd-MM-yyyy", "dd/MM/yy"};
    static final String TEN_DIGIT_MSISDN_REGEX = "^0[0-9]{9}";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public enum HTML_COLOR {
        BLACK("#000000"),
        WHITE("#FFFFFF"),
        RED("#FF0000"),
        GREEN("#00FF00"),
        BLUE("#0000FF"),
        ORANGE("#FFA500");

        private String color;

        HTML_COLOR(String color) {
            this.color = color;
        }

        public String getColor() {
            return this.color;
        }
    }

    public static String formatColor(String text, String color) {
        if (text.contains("<span style=\'"))
            return text.replaceFirst("<span style=\'", "<span style=\'color: \" + color + \"; ");
        else
            return "<span style=\'color: " + color + ";\'>" + text + "</span>";
    }

    public static String formatBlink(String text) {
        return "<emphasize>" + text + "</emphasize>";
    }
}
