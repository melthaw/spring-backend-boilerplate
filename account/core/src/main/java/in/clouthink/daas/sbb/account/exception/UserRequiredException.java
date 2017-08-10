package in.clouthink.daas.sbb.account.exception;

import in.clouthink.daas.security.token.exception.AuthenticationRequiredException;

/**
 *
 */
public class UserRequiredException extends AuthenticationRequiredException {

	public UserRequiredException() {
	}

	public UserRequiredException(String message) {
		super(message);
	}

	public UserRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserRequiredException(Throwable cause) {
		super(cause);
	}
}
