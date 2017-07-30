package in.clouthink.daas.sbb.account.exception;

/**
 *
 */
public class UserNotFoundException extends UserException {

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
