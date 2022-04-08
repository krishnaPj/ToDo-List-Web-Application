package net.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordManager {

	public static final int SALT_BYTES = 24, HASH_BYTES = 24, NUMBER_OF_ALGORITHM_ITERATIONS = 20000,
							INDEX_OF_NUMBER_OF_ITERATIONS = 0, INDEX_OF_SALT = 1, INDEX_OF_HASH = 2;

	public static String createHash(String password) { 
		return createHash(password.toCharArray()); 
	}

	public static String createHash(char[] password) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTES];
		random.nextBytes(salt);
		byte[] hash = pbkdf2(password, salt, NUMBER_OF_ALGORITHM_ITERATIONS, HASH_BYTES);
		return NUMBER_OF_ALGORITHM_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	public static boolean validatePassword(String password, String goodHash) {
		return validatePassword(password.toCharArray(), goodHash);
	}

	public static boolean validatePassword(char[] password, String goodHash) {
		String[] params = goodHash.split(":");
		int iterations = Integer.parseInt(params[INDEX_OF_NUMBER_OF_ITERATIONS]);
		byte[] salt = fromHex(params[INDEX_OF_SALT]),
			   hash = fromHex(params[INDEX_OF_HASH]),
			   testHash = pbkdf2(password, salt, iterations, hash.length);
		return slowEquals(hash, testHash);
	}

	private static boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++) diff |= a[i] ^ b[i];
		return diff == 0;
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			return skf.generateSecret(spec).getEncoded();
		} catch (Exception ex) {
			throw new RuntimeException("Unexpected error performing password encryption.", ex);
		}
	}

	private static byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		return binary;
	}

	public static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}
}
