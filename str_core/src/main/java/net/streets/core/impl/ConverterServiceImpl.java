package net.streets.core.impl;

import net.streets.core.contract.StrCurrency;
import net.streets.core.contract.StrSystemUser;
import net.streets.core.contract.StrWallet;
import net.streets.core.contract.StrWalletTransaction;
import net.streets.core.contract.base.EnumEntityData;
import net.streets.core.service.ConverterService;
import net.streets.persistence.entity.complex_type.log.str_session;
import net.streets.persistence.entity.complex_type.log.str_wallet_transaction;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.complex_type.wallet.str_wallet;
import net.streets.persistence.entity.enumeration.str_currency;
import net.streets.persistence.entity.super_class.str_enum_entity;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Service;

import static net.streets.common.utilities.CommonUtilities.format10DigitPhoneNumber;
import static net.streets.common.utilities.CommonUtilities.formatBigDecimalToMoney;
import static net.streets.common.utilities.StrTransformer.dateToString;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_DEFAULT_COUNTRY_CODE;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_DEFAULT_CURRENCY_SYMBOL;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Service
public class ConverterServiceImpl implements ConverterService {

    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFullTypeMatchingRequired(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
        return mapper;
    }

    @Override
    public StrCurrency toDTO(str_currency sourceData) {
        if (sourceData == null) {
            return null;
        }
        StrCurrency strCurrency = new StrCurrency();
        modelMapper().map(sourceData, strCurrency);
        strCurrency.setCurrencyId(sourceData.getId());
        strCurrency.setCurrencyName(sourceData.getName());
        return strCurrency;
    }

    @Override
    public StrSystemUser toDTO(str_user strUser) {
        if (strUser == null) {
            return null;
        }
        StrSystemUser strSystemUser = new StrSystemUser();
        modelMapper().map(strUser, strSystemUser);
        strSystemUser.setUserId(strUser.getId());
        strSystemUser.setPhoneNumber(format10DigitPhoneNumber(strUser.getMsisdn(), getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)));
        strSystemUser.setWalletId(strUser.getWallet().getId());
        strSystemUser.setWalletBalance(strUser.getWallet().getCurrent_balance().doubleValue());
        strSystemUser.setCountry(strUser.getCountry().getName());
        strSystemUser.setLanguage(strUser.getLanguage().getName());
        strSystemUser.setUserStatus(strUser.getUser_status().getName());
        return strSystemUser;
    }

    @Override
    public StrSystemUser toDTO(str_auth_user sourceData) {
        if (sourceData == null) {
            return null;
        }
        StrSystemUser strSystemUser = toDTO(sourceData.getUser());
        strSystemUser.setAuthUserId(sourceData.getId());
        strSystemUser.setGroup(sourceData.getAuth_group().getName());
        return strSystemUser;
    }

    @Override
    public StrSystemUser toDTO(str_session sourceData) {
        if (sourceData == null) {
            return null;
        }
        StrSystemUser strSystemUser = toDTO(sourceData.getAuth_user());
        strSystemUser.setSessionId(sourceData.getId());
        strSystemUser.setUserId(sourceData.getAuth_user().getUser().getId());
        strSystemUser.setAuthToken(sourceData.getAuth_token());
        return strSystemUser;
    }

    @Override
    public StrWallet toDTO(str_wallet sourceData) {
        if (sourceData == null) {
            return null;
        }
        StrWallet strWallet = new StrWallet();
        modelMapper().map(sourceData, strWallet);
        strWallet.setWalletId(sourceData.getId());
        return strWallet;
    }

    @Override
    public StrWalletTransaction toDTO(str_wallet_transaction sourceData) {
        if (sourceData == null) {
            return null;
        }
        StrWalletTransaction strWalletTransaction = new StrWalletTransaction();
        modelMapper().map(sourceData, strWalletTransaction);
        strWalletTransaction.setWalletTransactionId(sourceData.getId());
        strWalletTransaction.setWalletId(sourceData.getWallet().getId());
        strWalletTransaction.setEventType(sourceData.getEvent_type().getName());
        strWalletTransaction.setTransactionAmount(
            formatBigDecimalToMoney(sourceData.getTransaction_amount(), getStrConfigDao().getConfig(CONFIG_DEFAULT_CURRENCY_SYMBOL))
        );
        strWalletTransaction.setTransactionTime(dateToString(sourceData.getTransaction_time()));
        return strWalletTransaction;
    }

    @Override
    public EnumEntityData toDTO(str_enum_entity sourceData) {
        if (sourceData == null) {
            return null;
        }
        EnumEntityData enumEntityData = new EnumEntityData();
        modelMapper().map(sourceData, enumEntityData);
        return enumEntityData;
    }


}
