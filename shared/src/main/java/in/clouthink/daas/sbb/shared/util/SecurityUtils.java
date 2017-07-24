package in.clouthink.daas.sbb.shared.util;

import org.apache.commons.codec.binary.Base32;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 */
public class SecurityUtils {

	public static final String MD5_PASSWORD_ENCODER = "MD5";

	private static MessageDigest md5MessageDigest;

	private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	private static Random random = new SecureRandom();

	private static Random ranGen = new SecureRandom();

	private static Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

	static {
		try {
			md5MessageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String generateHexIdentity(String value) {
		try {
			if (md5MessageDigest != null) {
				return new String(Hex.encode(md5MessageDigest.digest(value.getBytes("utf-8"))));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String generatePasswordSalt() {
		byte[] aesKey = new byte[16];
		ranGen.nextBytes(aesKey);
		String salt = new String(Base64.encode(aesKey));
		salt = salt.replace("\r", "");
		salt = salt.replace("\n", "");
		return salt;
	}

	public static String generatedSaltedPasswordHash(String password, String salt, String passwordEncoderName) {
		if (StringUtils.isEmpty(passwordEncoderName)) {
			return md5PasswordEncoder.encodePassword(password, salt);
		}
		if (MD5_PASSWORD_ENCODER.equalsIgnoreCase(passwordEncoderName)) {
			return md5PasswordEncoder.encodePassword(password, salt);
		}
		// if (User.SHA1_PASSWORD_ENCODER.equalsIgnoreCase(passwordEncoderName))
		// {
		// return sha1PasswordEncoder.encodePassword(password, salt);
		// }
		throw new RuntimeException(String.format("Unsupported password encoder '%s'", passwordEncoderName));
	}

	public static String generateKey() {
		byte[] verifierBytes = new byte[6];
		random.nextBytes(verifierBytes);
		return getAuthorizationCodeString(verifierBytes);
	}

	public static String generateKey(int length) {
		if (length <= 1) {
			throw new IllegalArgumentException("The length of key must be greater than zero!");
		}
		byte[] verifierBytes = new byte[length];
		random.nextBytes(verifierBytes);
		String codeString = getAuthorizationCodeString(verifierBytes);
		return codeString.toLowerCase();
	}

	static String getAuthorizationCodeString(byte[] verifierBytes) {
		char[] chars = new char[verifierBytes.length];
		for (int i = 0; i < verifierBytes.length; i++) {
			chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
		}
		return new String(chars);
	}

	public static Authentication currentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static Object currentUser() {
		Authentication authentication = currentAuthentication();
		if (authentication != null) {
			return authentication.getPrincipal();
		}
		return null;
	}

	public static String generateId() {
		return generateHexKey();
	}

	public static String generateHexKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String generateBase32Key() {
		byte[] hexBytes = Hex.decode(generateHexKey());
		return new Base32().encodeAsString(hexBytes);
	}

	public static String generateBase64Key() {
		byte[] hexBytes = Hex.decode(generateHexKey());
		return new String(Base64.encode(hexBytes));
	}

}
