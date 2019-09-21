package net.streets.common.security;

/**
 * Created by photon on 2015/12/31.
 */
public class StreetsSecurityEncryption {

    public static final String SHA256 = "SHA-256";

    public static final String DEFAULT_SECURITY_HASH = SHA256;

    public static final byte[] DEFAULT_ENCRYPTION_SALT = null;

    public static final int DEFAULT_SALT_LENGTH = 64;

//    public static final int MAX_PASSWORD_TRIES = parseInt(Configuration.getProperty("MaxPasswordTries"));
}
