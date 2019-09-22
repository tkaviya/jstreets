package net.streets.core.service;

import net.streets.core.contract.StrCurrency;
import net.streets.core.contract.StrSystemUser;
import net.streets.core.contract.StrWallet;
import net.streets.core.contract.StrWalletTransaction;
import net.streets.core.contract.base.EnumEntityData;
import net.streets.persistence.entity.complex_type.log.str_session;
import net.streets.persistence.entity.complex_type.log.str_wallet_transaction;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.complex_type.wallet.str_wallet;
import net.streets.persistence.entity.enumeration.str_currency;
import net.streets.persistence.entity.super_class.str_enum_entity;
import org.modelmapper.ModelMapper;

/***************************************************************************
 * *
 * Created:     22 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface ConverterService {
    ModelMapper modelMapper();

    EnumEntityData toDTO(str_enum_entity enumEntity);

    StrCurrency toDTO(str_currency strCurrency);

    StrSystemUser toDTO(str_user strUser);

    StrSystemUser toDTO(str_auth_user strAuthUser);

    StrSystemUser toDTO(str_session strSession);

    StrWallet toDTO(str_wallet wallet);

    StrWalletTransaction toDTO(str_wallet_transaction strWalletTransaction);
}
