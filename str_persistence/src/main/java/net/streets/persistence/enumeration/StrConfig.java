package net.streets.persistence.enumeration;

/***************************************************************************
 * Created:     3/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public enum StrConfig {

    //SYSTEM SETTINGS
    CONFIG_SYSTEM_NAME,
    CONFIG_CONFIG_UPDATE_DURATION,
    CONFIG_THREAD_CORE_POOL_SIZE ,
    CONFIG_THREAD_MAX_POOL_SIZE,
    CONFIG_MUTEX_LOCK_WAIT_TIME,
    CONFIG_MUTEX_LOCK_WAIT_INTERVAL,

    //MAIL SETTINGS
    CONFIG_EMAIL_PROTOCOL,
    CONFIG_EMAIL_DISABLE,
    CONFIG_EMAIL_HOST,
    CONFIG_EMAIL_PORT,
    CONFIG_EMAIL_USERNAME,
    CONFIG_EMAIL_PASSWORD,
    CONFIG_EMAIL_SMTP_AUTH,
    CONFIG_EMAIL_SMTP_STARTTLS_ENABLE,
    CONFIG_EMAIL_SMTP_DEBUG,
    CONFIG_EMAIL_FROM,
    CONFIG_EMAIL_ALERT_TO,

    //LOCALIZATION SETTINGS
    CONFIG_DEFAULT_COUNTRY,
    CONFIG_DEFAULT_COUNTRY_CODE,
    CONFIG_DEFAULT_CURRENCY_SYMBOL,
    CONFIG_DEFAULT_LANGUAGE,

    //REGISTRATION SETTINGS
    CONFIG_INITIAL_AUTH_GROUP,
    CONFIG_INITIAL_POCKET_BALANCE,
    CONFIG_INITIAL_ACCOUNT_BALANCE,
    CONFIG_INITIAL_CURRENT_BALANCE,
    CONFIG_INITIAL_CURRENT_ENERGY,
    CONFIG_INITIAL_MAX_ENERGY,

    //COMPANY SETTINGS
    CONFIG_DOMAIN_NAME,
    CONFIG_CONTACT_ADDRESS,
    CONFIG_SUPPORT_EMAIL,
    CONFIG_SUPPORT_PHONE,

    //WEB SETTINGS
    CONFIG_DEFAULT_REPORTING_DAYS,

    //SECURITY SETTINGS
    CONFIG_MAX_PASSWORD_TRIES,
}
