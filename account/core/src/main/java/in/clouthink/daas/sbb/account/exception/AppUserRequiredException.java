package in.clouthink.daas.sbb.account.exception;

/**
 *
 */
public class AppUserRequiredException extends AppUserException {

	public AppUserRequiredException() {
	}

	public AppUserRequiredException(String message) {
		super(message);
	}

	public AppUserRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppUserRequiredException(Throwable cause) {
		super(cause);
	}
}
