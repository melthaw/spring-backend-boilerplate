package in.clouthink.daas.sbb.shared;

import java.util.regex.Pattern;

/**
 * @author dz
 */
public class DomainConstants {

	/**
	 * Email reg exp
	 */
	public final static Pattern VALID_EMAIL_REGEX = Pattern.compile(
			"^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	/**
	 * Cellphone reg exp
	 */
	public final static Pattern VALID_CELLPHONE_REGEX = Pattern.compile("^[1][1-9][0-9]{9}$");

	public final static long HOW_LONG_OF_ONE_DAY = 24 * 60 * 60 * 1000l;

}
