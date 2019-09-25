package net.streets.utilities;

import java.math.BigDecimal;

import static net.streets.common.utilities.CommonUtilities.*;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_DEFAULT_COUNTRY_CODE;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_DEFAULT_CURRENCY_SYMBOL;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

/***************************************************************************
 *                                                                         *
 * Created:     25 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public class StreetsUtils {

	public static String formatMoney(final double value) {
		return formatDoubleToMoney(value, getStrConfigDao().getConfig(CONFIG_DEFAULT_CURRENCY_SYMBOL));
	}

	public static String formatMoney(final BigDecimal value) {
		return formatMoney(value.doubleValue());
	}

	public static String formatFullPhone(final String phoneNumber) {
		return formatFullMsisdn(phoneNumber, getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE));
	}

	public static String formatShortPhone(final String msisdn) {
		return format10DigitPhoneNumber(msisdn, getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE));
	}

}
