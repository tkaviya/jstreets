package net.streets.core.service;

import net.streets.persistence.entity.complex_type.log.str_wallet_transaction;
import net.streets.persistence.entity.complex_type.wallet.str_wallet;
import net.streets.persistence.enumeration.StrResponseObject;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public interface WalletManager {
    StrResponseObject<str_wallet> updateWalletBalance(str_wallet_transaction walletTransactionDetails);
}
