package in.clouthink.daas.sbb.account.exception;

import in.clouthink.daas.sbb.exception.BizException;

/**
 *
 */
public class UserException extends BizException {

	public UserException() {
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserException(Throwable cause) {
		super(cause);
	}
}
