package net.streets.core.impl;

import net.streets.core.service.WalletManager;
import net.streets.persistence.entity.complex_type.log.str_wallet_transaction;
import net.streets.persistence.entity.complex_type.wallet.str_wallet;
import net.streets.persistence.enumeration.StrResponseObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.persistence.enumeration.StrResponseCode.*;

/***************************************************************************
 * Created:     23 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Service
public class WalletManagerImpl implements WalletManager {

    private static final Logger logger = Logger.getLogger(WalletManagerImpl.class.getSimpleName());

    @Override
    public synchronized StrResponseObject<str_wallet> updateWalletBalance(str_wallet_transaction walletTransactionDetails) {

        if (walletTransactionDetails.getId() != null) {
            logger.severe("Wallet balance update failed! Wallet transaction details already exist. Cannot update existing transaction.");
            return new StrResponseObject<str_wallet>(INPUT_INVALID_REQUEST).setMessage("Cannot update existing transaction!");
        }
        if (walletTransactionDetails.getTransaction_amount() == null) {
            logger.severe("Wallet balance update failed! Invalid amount (null) specified.");
            return new StrResponseObject<str_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid amount (null) specified!");
        }
        if (walletTransactionDetails.getEvent_type() == null) {
            logger.severe("Wallet balance update failed! Invalid event type (null) specified.");
            return new StrResponseObject<str_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid event type (null) specified!");
        }
        if (walletTransactionDetails.getTransaction_link_reference() == null) {
            logger.severe("Wallet balance update failed! Invalid link reference (null) specified.");
            return new StrResponseObject<str_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid link reference (null) specified!");
        }
        if (walletTransactionDetails.getTransaction_time() == null) {
            logger.severe("Wallet balance update failed! Invalid transaction time (null) specified.");
            return new StrResponseObject<str_wallet>(INPUT_INVALID_REQUEST).setMessage("Invalid transaction time (null) specified!");
        }
        if (isNullOrEmpty(walletTransactionDetails.getTransaction_description())) {
            logger.severe("Wallet balance update failed! Transaction description not specified.");
            return new StrResponseObject<str_wallet>(INPUT_INVALID_REQUEST).setMessage("Transaction description not specified!");
        }

        //make sure we are using an up to date balance
        str_wallet wallet = walletTransactionDetails.getWallet().refresh();
        logger.info(format("Updating wallet id %s (current balance: %s) with amount %s",
                wallet.getId(), wallet.getCurrent_balance().doubleValue(), walletTransactionDetails.getTransaction_amount().doubleValue()));

        try {
            //check if new balance will be greater than or equal to 0
            if (wallet.getCurrent_balance().add(walletTransactionDetails.getTransaction_amount()).compareTo(new BigDecimal(0.0)) < 0) {
                logger.severe("Balance update not processed! Insufficient funds.");
                return new StrResponseObject<>(INSUFFICIENT_FUNDS, wallet);
            }

            wallet.setCurrent_balance(wallet.getCurrent_balance().add(walletTransactionDetails.getTransaction_amount())).save();

            logger.info(format("Balance updated successfully. New balance for wallet %s is %s",
                    wallet.getWallet_admin_user(), wallet.getCurrent_balance().toPlainString()));

            walletTransactionDetails.save();

            return new StrResponseObject<>(SUCCESS, wallet);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new StrResponseObject<>(GENERAL_ERROR, wallet);
        }
    }
}
