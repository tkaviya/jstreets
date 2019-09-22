package net.streets.persistence.enumeration;

/***************************************************************************
 * *
 * Created:     18 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public enum StrEventType {

    //AUTHENTICATION
    USER_REGISTRATION, USER_ASSIGN_CHANNEL, USER_CREATE, USER_LOGIN, USER_LOGOUT, USER_UPDATE,
    USER_PIN_UPDATE, USER_PIN_RESET,

    //WALLET
    WALLET_LOAD, WALLET_UPDATE, WALLET_TRANSFER, WALLET_HISTORY,
}
