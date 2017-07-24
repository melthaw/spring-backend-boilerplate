package in.clouthink.daas.sbb.shared.security.algorithm;

import org.apache.commons.codec.binary.Base32;
import org.springframework.security.crypto.codec.Hex;

public class TOTPUtils {

	public static final long DEFAULT_TIMESTEP_IN_MILLS = 30 * 1000;

	/**
	 * @param base32Key base32 encoded key
	 * @return
	 */
	public static String getGeneratedValue(String base32Key) {
		// default time step
		return getGeneratedValue(base32Key, DEFAULT_TIMESTEP_IN_MILLS);
	}

	/**
	 * @param base32Key       base32 encoded key
	 * @param timeStepInMills
	 * @return
	 */
	public static String getGeneratedValue(String base32Key, long timeStepInMills) {
		// time step
		String hexKey = new String(Hex.encode(new Base32().decode(base32Key)));
		return TOTP.generateTOTP(hexKey,
								 Long.toHexString(System.currentTimeMillis() / timeStepInMills),
								 "6",
								 "HmacSHA1");
	}

}
