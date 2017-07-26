package in.clouthink.daas.sbb.account.exception;

import in.clouthink.daas.sbb.exception.BizException;

/**
 *
 */
public class AppUserException extends BizException {

	public AppUserException() {
	}

	public AppUserException(String message) {
		super(message);
	}

	public AppUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppUserException(Throwable cause) {
		super(cause);
	}
}
