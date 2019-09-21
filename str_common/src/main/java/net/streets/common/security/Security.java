package net.streets.common.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 12/8/13
 * Time: 7:05 PM
 */
public class Security {
    private static SecureRandom secureRandom;

    public static String generateIV() {
        int n = 16;
        char[] iv = new char[n];
        int c = 'A', r1;

        for (int i = 0; i < n; i++) {
            r1 = (int) (Math.random() * 3);
            switch (r1) {
                case 0:
                    c = '0' + (int) (Math.random() * 10);
                    break;
                case 1:
                    c = 'a' + (int) (Math.random() * 26);
                    break;
                case 2:
                    c = 'A' + (int) (Math.random() * 26);
                    break;
            }
            iv[i] = (char) c;
        }
        return new String(iv);
    }

    public static byte[] generateSecureRandomBytes() {

        if (secureRandom == null) {
            secureRandom = new SecureRandom();
        }

        byte[] randomBytes = new byte[StreetsSecurityEncryption.DEFAULT_SALT_LENGTH];

        secureRandom.nextBytes(randomBytes);

        return randomBytes;
    }

    public static String hashWithSalt(final String unencryptedStr, final byte[] salt) {
        return hashWithSalt(unencryptedStr, StreetsSecurityEncryption.DEFAULT_SECURITY_HASH, salt);
    }

    public static String hashWithSalt(final String input, final String encryptMode, final byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(encryptMode);
            messageDigest.reset();

            if (!isNullOrEmpty(Arrays.toString(salt))) {
                messageDigest.update(salt);
            }

            byte[] digested = messageDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte aDigested : digested) {
                sb.append(Integer.toHexString(0xff & aDigested));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static String encryptAES(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decryptAES(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
