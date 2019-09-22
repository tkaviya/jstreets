package net.streets.core.impl;

import net.streets.core.contract.*;
import net.streets.core.service.ConverterService;
import net.streets.core.service.StreetsRequestProcessor;
import net.streets.persistence.entity.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.streets.persistence.dao.EnumEntityRepoManager.findEnabled;
import static net.streets.persistence.enumeration.StrResponseCode.DATA_NOT_FOUND;
import static net.streets.persistence.enumeration.StrResponseCode.SUCCESS;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Service
public class StreetsRequestProcessorImpl implements StreetsRequestProcessor {

    private static final Logger logger = Logger.getLogger(StreetsRequestProcessorImpl.class.getSimpleName());
    private final ConverterService converterService;

    @Autowired
    public StreetsRequestProcessorImpl(ConverterService converterService) {
        this.converterService = converterService;
    }

    public StrEnumEntity getResponseCode(Long responseCodeId) {

        str_response_code response_code = getEntityManagerRepo().findById(str_response_code.class, responseCodeId);

        if (response_code == null) {
            return new StrEnumEntity(DATA_NOT_FOUND);
        }

        return new StrEnumEntity(SUCCESS, converterService.toDTO(response_code));
    }

    @Override
    public StrList getResponseCodes() {
        logger.info("Getting system response code list");
        List<str_response_code> dbResponseCodes = findEnabled(str_response_code.class);
        logger.info(format("Got %s str_response_codes from DB", dbResponseCodes.size()));
        ArrayList<StrResponse> responses = new ArrayList<>();
        dbResponseCodes.forEach((r) -> responses.add(new StrResponse(r)));
        return new StrList(SUCCESS, responses);
    }

    @Override
    public StrEnumEntity getLanguage(Long languageId) {
        return new StrEnumEntity(SUCCESS, converterService.toDTO(getEntityManagerRepo().findById(str_language.class, languageId)));
    }

    @Override
    public StrEnumEntity getChannel(Long channelId) {
        return new StrEnumEntity(SUCCESS, converterService.toDTO(getEntityManagerRepo().findById(str_channel.class, channelId)));
    }

    @Override
    public StrEnumEntity getCountry(Long countryId) {
        return new StrEnumEntity(SUCCESS, converterService.toDTO(getEntityManagerRepo().findById(str_country.class, countryId)));
    }

    @Override
    public StrCurrencyList getCurrency(Long currencyId) {
        logger.info(format("Getting currency with Id %s", currencyId));
        str_currency currency = getEntityManagerRepo().findById(str_currency.class, currencyId);
        if (currency == null) {
            return new StrCurrencyList(DATA_NOT_FOUND);
        }
        logger.info(format("Returning currency with Id %s: %s", currencyId, currency.toString()));
        return new StrCurrencyList(SUCCESS, converterService.toDTO(currency));
    }

    @Override
    public StrCurrencyList getCurrencies() {
        logger.info("Getting currency list");
        ArrayList<StrCurrency> currencies = new ArrayList<>();
        findEnabled(str_currency.class).forEach(c -> currencies.add(converterService.toDTO(c)));
        logger.info(format("Returning %s currencies", currencies.size()));
        return new StrCurrencyList(SUCCESS, currencies);
    }
}
