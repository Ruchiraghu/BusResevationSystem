package utility;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;
public class PasswordUtil {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 64;
    private static final int ITERATIONS = 10000;


    public static String hashPassword(String password) {
        byte[] salt = getSalt();
        byte[] hashedPassword = hashPassword(password, salt);

        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
    }
    public static boolean checkPassword(String password, String storedPassword) {
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Stored password format is invalid.");
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hash = Base64.getDecoder().decode(parts[1]);

        byte[] newHash = hashPassword(password, salt);

        return java.util.Arrays.equals(hash, newHash);
    }

    private static byte[] getSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] hashPassword(String password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH * 8);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
