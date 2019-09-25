package net.streets.common.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/12/13
 * Time: 10:05 PM
 */
public class CommonUtilities {
    private static final Logger logger = Logger.getLogger(CommonUtilities.class.getSimpleName());

    public static String joinWithDelimiter(Object delimiter, final Object... args) {

        if (args == null) {
            return null;
        }

        if (delimiter == null) {
            delimiter = "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        int countPresent = 0;
        for (Object arg : args) {
            if (arg != null) {
                countPresent++;
                if (!arg.toString().trim().equals("")) {
                    stringBuilder.append(arg).append(delimiter);
                }
            }
        }
        if (countPresent == 0) {
            return null;
        } else if (!delimiter.equals("") && stringBuilder.toString().endsWith(delimiter.toString())) {
            return stringBuilder.substring(0, stringBuilder.lastIndexOf(delimiter.toString()));
        } else {
            return stringBuilder.toString();
        }
    }

    public static String join(final Object... parts) {
        return joinWithDelimiter(null, parts);
    }

    public static String toCamelCase(final String str) {
        return toCamelCase(str, " ");
    }

    public static String toCamelCase(final String str, final String delimiter) {

        if (isNullOrEmpty(str)) return str;

        StringBuilder returnString = new StringBuilder(str.length());

        for (final String word : str.split(delimiter)) {
            if (!word.isEmpty()) {
                returnString.append(word.substring(0, 1).toUpperCase());
                returnString.append(word.substring(1).toLowerCase());
            }
            if (!(returnString.length() == str.length()))
                returnString.append(delimiter);
        }

        return returnString.toString();
    }

    public static String decapitalizeFirst(final String str) {
        return isNullOrEmpty(str) ? str : join(str.substring(0, 1).toLowerCase(), str.substring(1));
    }

    public static String capitalizeFirst(final String str) {
        return isNullOrEmpty(str) ? str : Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String capitalizeAll(final String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        char stringArray[] = str.toCharArray();
        String resultStr = "";
        for (char c : stringArray) {
            resultStr += Character.toUpperCase(c);
        }
        return resultStr;
    }

    public static String decapitalizeAll(final String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        char stringArray[] = str.toCharArray();
        String resultStr = "";
        for (char c : stringArray) {
            resultStr += Character.toLowerCase(c);
        }
        return resultStr;
    }

    public static String alignStringToLength(String str, final int length) {
        if (isNullOrEmpty(str)) str = "";
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < length) {
            strBuilder.append(" ");
        }
        str = strBuilder.toString();
        return str;
    }

    public static String rightAlignStringToLength(String str, final int length) {
        if (isNullOrEmpty(str)) str = "";
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < length) {
            strBuilder.insert(0, " ");
        }
        str = strBuilder.toString();
        return str;
    }

    public static String formatDoubleToMoney(final double value, final String currencySymbol) {
        DecimalFormat df = new DecimalFormat("###,##0.00");

        String formattedString = df.format(value);

        if (!formattedString.startsWith("-")) {
            return (currencySymbol == null ? "" : currencySymbol) + formattedString;
        } else {
            return "-" + (currencySymbol == null ? "" : currencySymbol) + formattedString.replaceFirst("-", "");
        }
    }

    public static String formatBigDecimalToMoney(final BigDecimal value) {
        return formatDoubleToMoney(value.doubleValue(), null);
    }

    public static String formatBigDecimalToMoney(final BigDecimal value, final String currencySymbol) {
        return formatDoubleToMoney(value.doubleValue(), currencySymbol);
    }

    public static String formatDoubleToMoney(final double value) {
        return formatDoubleToMoney(value, null);
    }

    public static String formatFullMsisdn(String msisdn, String countryCodePrefix) {

        if (msisdn == null || countryCodePrefix == null) {
            return msisdn;
        }

        if (msisdn.length() > 10) {
            if (msisdn.startsWith(countryCodePrefix)) {
                return msisdn;
            } else if (msisdn.startsWith("+")) {
                msisdn = msisdn.replaceFirst("\\+", "");
            } else if (msisdn.startsWith("00")) {
                msisdn = msisdn.replaceFirst("00", "");
            }
            return msisdn;
        } else if (msisdn.length() == 10 && msisdn.startsWith("0")) {
            return msisdn.replaceFirst("0", countryCodePrefix);
        }
        return msisdn;
    }

    public static Long format10DigitPhoneNumber(Long msisdn, String countryCodePrefix) {
        String result = format10DigitPhoneNumber(String.valueOf(msisdn), countryCodePrefix);
        if (!isNullOrEmpty(result)) {
            return Long.parseLong(result);
        } else {
            return msisdn;
        }
    }

    public static String format10DigitPhoneNumber(String msisdn, String countryCodePrefix) {

        String returnMsisdn = msisdn;
        if (msisdn == null || countryCodePrefix == null) {
            return msisdn;
        }
        if (msisdn.length() == 10 && msisdn.startsWith("0")) {
            return msisdn;
        }
        if (returnMsisdn.length() > 10) {
            if (returnMsisdn.startsWith("+")) {
                returnMsisdn = returnMsisdn.replaceFirst("\\+","");
            }
            if (returnMsisdn.startsWith("00")) {
                returnMsisdn = returnMsisdn.replaceFirst("00", "");
            }
            if (returnMsisdn.startsWith(countryCodePrefix)) {
                returnMsisdn = returnMsisdn.replaceFirst(countryCodePrefix,"0");
            }
        }
        return returnMsisdn.length() == 10 && returnMsisdn.startsWith("0") ? returnMsisdn : msisdn;
    }

    public static long secondsBetween(final Date firstDate, final Date secondDate) {
        return abs((firstDate.getTime() - secondDate.getTime()) / 1000);
    }

    public static long minutesBetween(final Date firstDate, final Date secondDate) {
        return secondsBetween(firstDate, secondDate) / 60;
    }

    public static long hoursBetween(final Date firstDate, final Date secondDate) {
        return minutesBetween(firstDate, secondDate) / 60;
    }

    public static long daysBetween(final Date firstDate, final Date secondDate) {
        return hoursBetween(firstDate, secondDate) / 24;
    }

    public static long weeksBetween(final Date firstDate, final Date secondDate) {
        return daysBetween(firstDate, secondDate) / 7;
    }

    public static int round(final double d) {
        double dAbs = abs(d);
        int i = (int) dAbs;

        if ((dAbs - (double) i) < 0.5) return d < 0 ? -i : i;        //negative value
        else return d < 0 ? -(i + 1) : i + 1;    //positive value
    }

    public static String javaToJSRegex(String regex) {
        return '/' + regex + '/';
    }

//    public static StrResponseObject<String> getValueByStringPattern(String inputString, String inputPattern, int group) {
//        try {
//
//            logger.info(format("Getting value by pattern %s", inputPattern));
//            logger.info(format("Group = %d. Input string = %s", group, inputString));
//
//            //process string for data
//            if (inputPattern == null) {
//                logger.severe("Regex required to parse inputString");
//                return new StrResponseObject<String>(GENERAL_ERROR)
//                        .setMessage(format("Regex required to parse inputString %s", inputString));
//            }
//            if (inputString == null) {
//                logger.severe("inputString was not supplied for processing");
//                return new StrResponseObject<String>(GENERAL_ERROR)
//                        .setMessage("inputString was not supplied for processing");
//            }
//            Pattern pattern = Pattern.compile(inputPattern);
//            Matcher matcher = pattern.matcher(inputString);
//            if (!matcher.find()) {
//                logger.warning(format("Regex pattern could not find value in inputString %s", inputString));
//                return new StrResponseObject<>(DATA_NOT_FOUND);
//            }
//            return new StrResponseObject<>(SUCCESS, matcher.group(group));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new StrResponseObject<String>(GENERAL_ERROR).setMessage(ex.getMessage());
//        }
//    }

    public static Throwable getRealThrowable(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

    public static String throwableAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.equals("");
    }
}
