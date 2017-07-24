package in.clouthink.daas.sbb.shared.util;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.codec.Hex;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class KeyUtils {

	private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	private static Random random = new SecureRandom();

	public static String generateId() {
		return generateHexKey();
	}

	public static String generateHexKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String generateBase32Key() {
		byte[] hexBytes = Hex.decode(UUID.randomUUID().toString().replace("-", ""));
		return new Base32().encodeAsString(hexBytes);
	}

	public static String generateBase64Key() {
		byte[] hexBytes = Hex.decode(UUID.randomUUID().toString().replace("-", ""));
		return new Base64().encodeAsString(hexBytes);
	}

	public static String generateState() {
		byte[] verifierBytes = new byte[6];
		random.nextBytes(verifierBytes);
		return getAuthorizationCodeString(verifierBytes);
	}

	public static String generateState(int length) {
		if (length <= 1) {
			throw new IllegalArgumentException("The length of key must be greater than zero!");
		}
		byte[] verifierBytes = new byte[length];
		random.nextBytes(verifierBytes);
		String codeString = getAuthorizationCodeString(verifierBytes);
		return codeString.toLowerCase();
	}

	protected static String getAuthorizationCodeString(byte[] verifierBytes) {
		char[] chars = new char[verifierBytes.length];
		for (int i = 0; i < verifierBytes.length; i++) {
			chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
		}
		return new String(chars);
	}

}
