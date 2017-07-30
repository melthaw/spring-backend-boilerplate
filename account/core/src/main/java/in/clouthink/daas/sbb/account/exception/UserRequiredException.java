package in.clouthink.daas.sbb.account.exception;

/**
 *
 */
public class UserRequiredException extends UserException {

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
