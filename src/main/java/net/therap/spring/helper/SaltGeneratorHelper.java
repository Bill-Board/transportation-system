package net.therap.spring.helper;

import java.security.SecureRandom;

/**
 * @author shoebakib
 * @since 5/8/24
 */
public class SaltGeneratorHelper {

    public static String getSalt() {
        int saltLength = 16;
        byte[] salt = generateSalt(saltLength);

        return bytesToHex(salt);
    }

    private static byte[] generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];

        random.nextBytes(salt);

        return salt;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}