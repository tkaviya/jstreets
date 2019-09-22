package net.streets.core.service;

import net.streets.core.contract.StrCurrencyList;
import net.streets.core.contract.StrEnumEntity;
import net.streets.core.contract.StrList;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface StreetsRequestProcessor extends RequestProcessor {

    StrEnumEntity getResponseCode(Long responseCodeId);

    StrList getResponseCodes();

    StrEnumEntity getLanguage(Long languageId);

    StrEnumEntity getChannel(Long channelId);

    StrEnumEntity getCountry(Long countryId);

    StrCurrencyList getCurrency(Long currencyId);

    StrCurrencyList getCurrencies();
}
