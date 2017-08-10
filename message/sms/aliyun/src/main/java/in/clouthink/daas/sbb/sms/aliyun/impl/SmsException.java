package in.clouthink.daas.sbb.sms.aliyun.impl;

/**
 * @author dz
 */
public class SmsException extends RuntimeException {

	public SmsException() {
	}

	public SmsException(String message) {
		super(message);
	}

	public SmsException(String message, Throwable cause) {
		super(message, cause);
	}

	public SmsException(Throwable cause) {
		super(cause);
	}

	public SmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
