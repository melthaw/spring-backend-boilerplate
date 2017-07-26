package in.clouthink.daas.sbb.setting.exception;

/**
 * @author dz
 */
public class SystemSettingException extends RuntimeException {

	public SystemSettingException() {
	}

	public SystemSettingException(String message) {
		super(message);
	}

	public SystemSettingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemSettingException(Throwable cause) {
		super(cause);
	}

	public SystemSettingException(String message,
								  Throwable cause,
								  boolean enableSuppression,
								  boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
