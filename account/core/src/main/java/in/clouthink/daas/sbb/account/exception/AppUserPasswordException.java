package in.clouthink.daas.sbb.account.exception;

import in.clouthink.daas.sbb.exception.BizException;

/**
 *
 */
public class AppUserPasswordException extends BizException {

	public AppUserPasswordException() {
	}

	public AppUserPasswordException(String message) {
		super(message);
	}

	public AppUserPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppUserPasswordException(Throwable cause) {
		super(cause);
	}
}
