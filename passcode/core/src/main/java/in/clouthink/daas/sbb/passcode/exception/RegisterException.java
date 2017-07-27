package in.clouthink.daas.sbb.passcode.exception;

import in.clouthink.daas.sbb.exception.BizException;

/**
 */
public class RegisterException extends BizException {

	public RegisterException() {
	}

	public RegisterException(String message) {
		super(message);
	}

	public RegisterException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisterException(Throwable cause) {
		super(cause);
	}
}
