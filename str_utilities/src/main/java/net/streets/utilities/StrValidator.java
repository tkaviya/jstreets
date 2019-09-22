package net.streets.utilities;

import net.streets.common.utilities.CommonUtilities;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import static net.streets.persistence.enumeration.StrConfig.CONFIG_DEFAULT_COUNTRY_CODE;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

/**
 * User: tkaviya
 * Date: 3/27/2015
 * Time: 6:44 AM
 */
public class StrValidator {
    public static final Integer MIN_PASSWORD_LENGTH = 6;
    public static final Integer MAX_PASSWORD_LENGTH = 20;
    public static final Integer PIN_LEN = 4;
    public static final Integer MIN_CARD_NUM_LEN = 13;
    public static final Integer MAX_CARD_NUM_LEN = 19;
    public static final Integer MIN_CARD_PIN_LEN = 4;
    public static final Integer MAX_CARD_PIN_LEN = 6;
    public static final Integer MIN_NAME_LEN = 2;
    public static final Integer MAX_NAME_LEN = 40;
    public static final Integer MIN_PLAIN_TEXT_LEN = 2;
    public static final Integer MAX_PLAIN_TEXT_LEN = 50;
    public static final Integer MIN_ADDRESS_LEN = 2;
    public static final Integer MAX_ADDRESS_LEN = 50;
    public static final Integer MIN_UNAME_LEN = 4;
    public static final Integer MAX_UNAME_LEN = 20;
    public static final Integer MAX_AUTH_TOKEN_LEN = 128;
    public static final Integer MAX_MSISDN_LEN = 15;

    public static final String NAME_CHARS = "[a-zA-Z-_ ]";
    public static final String PLAIN_TEXT_CHARS = "[_a-zA-Z0-9- ]";
    public static final String PASSWORD_CHARS = "[a-zA-Z0-9<>.!@();:#$%&*+/=?^_{|}~-]";
    public static final String ADDRESS_CHARS = "[a-zA-Z0-9-\\./, ]";
    public static final String UNAME_CHARS = "[a-zA-Z0-9-_.]";
    public static final String EMAIL_REGEX = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$";
    public static final String PASSWORD_REGEX = PASSWORD_CHARS + "{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}";
    public static final String PIN_REGEX = "[0-9]{" + PIN_LEN + "}";
    public static final String CARD_NUMBER_REGEX = "[0-9]{" + MIN_CARD_NUM_LEN + "," + MAX_CARD_NUM_LEN + "}";
    public static final String CARD_PIN_REGEX = "[0-9]{" + MIN_CARD_PIN_LEN + "," + MAX_CARD_PIN_LEN + "}";
    public static final String MSISDN_REGEX = "^(([0]{2})|[+])*(" + getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE) + ")[0-9]{9}";
    public static final String FULL_MSISDN_REGEX = "^(" + getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE) + ")[0-9]{9}";
    public static final String TEN_DIGIT_MSISDN_REGEX = "^(0)[0-9]{9}";
    public static final String NAME_REGEX = NAME_CHARS + "{" + MIN_NAME_LEN + "," + MAX_NAME_LEN + "}";
    public static final String PLAIN_TEXT_REGEX = PLAIN_TEXT_CHARS + "{" + MIN_PLAIN_TEXT_LEN + "," + MAX_PLAIN_TEXT_LEN + "}";
    public static final String ADDRESS_REGEX = ADDRESS_CHARS + "{" + MIN_ADDRESS_LEN + "," + MAX_ADDRESS_LEN + "}";
    public static final String USERNAME_REGEX = UNAME_CHARS + "{" + MIN_UNAME_LEN + "," + MAX_UNAME_LEN + "}";

    public static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    public static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
    public static final Pattern pinPattern = Pattern.compile(PIN_REGEX);
    public static final Pattern cardNumberPattern = Pattern.compile(CARD_NUMBER_REGEX);
    public static final Pattern cardPinPattern = Pattern.compile(CARD_PIN_REGEX);
    public static final Pattern msisdnPattern = Pattern.compile(MSISDN_REGEX);
    public static final Pattern tenDigitMsisdnPattern = Pattern.compile(TEN_DIGIT_MSISDN_REGEX);
    public static final Pattern fullMsisdnPattern = Pattern.compile(FULL_MSISDN_REGEX);
    public static final Pattern namePattern = Pattern.compile(NAME_REGEX);
    public static final Pattern plainTextPattern = Pattern.compile(PLAIN_TEXT_REGEX);
    public static final Pattern addressPattern = Pattern.compile(ADDRESS_REGEX);
    public static final Pattern usernamePattern = Pattern.compile(USERNAME_REGEX);

    public static boolean isNumeric(Object testObject) {
        if (testObject == null) return false;
        if (testObject instanceof Number) return true;
        try {
            new BigDecimal(String.valueOf(testObject));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidEmail(String emailAddress) {
        return !CommonUtilities.isNullOrEmpty(emailAddress) && emailPattern.matcher(emailAddress).matches();
    }

    public static boolean isValidPassword(String password) {
        return !CommonUtilities.isNullOrEmpty(password) && passwordPattern.matcher(password).matches();
    }

    public static boolean isValidPin(String pin) {
        return !CommonUtilities.isNullOrEmpty(pin) && pinPattern.matcher(pin).matches();
    }

    public static boolean isValidCardNumber(String cardNumber) {
        return !CommonUtilities.isNullOrEmpty(cardNumber) && cardNumberPattern.matcher(cardNumber).matches();
    }

    public static boolean isValidCardPin(String pin) {
        return !CommonUtilities.isNullOrEmpty(pin) && cardPinPattern.matcher(pin).matches();
    }

    public static boolean isValidMsisdn(String msisdn, String countryCodePrefix) {
        return msisdn != null && msisdn.matches("^(([0]{2})|[+])*(" + countryCodePrefix + ")[0-9]{9}");
    }

    public static boolean isValidMsisdn(String msisdn) {
        return !CommonUtilities.isNullOrEmpty(msisdn) && msisdnPattern.matcher(msisdn).matches();
    }

    public static boolean isValidTenDigitMsisdn(String msisdn) {
        return !CommonUtilities.isNullOrEmpty(msisdn) && tenDigitMsisdnPattern.matcher(msisdn).matches();
    }

    public static boolean isValidFullMsisdn(String msisdn) {
        return !CommonUtilities.isNullOrEmpty(msisdn) && fullMsisdnPattern.matcher(msisdn).matches();
    }

    public static boolean isValidName(String name) {
        return !CommonUtilities.isNullOrEmpty(name) && namePattern.matcher(name).matches();
    }

    public static boolean isValidPlainText(String text) {
        return !CommonUtilities.isNullOrEmpty(text) && plainTextPattern.matcher(text).matches();
    }

    public static boolean isValidAddress(String text) {
        return !CommonUtilities.isNullOrEmpty(text) && addressPattern.matcher(text).matches();
    }

    public static boolean isValidUsername(String username) {
        return !CommonUtilities.isNullOrEmpty(username) && usernamePattern.matcher(username).matches();
    }

    public static boolean isValidAuthData(String authData) {
        return !CommonUtilities.isNullOrEmpty(authData) && authData.length() <= MAX_AUTH_TOKEN_LEN;
    }
}
