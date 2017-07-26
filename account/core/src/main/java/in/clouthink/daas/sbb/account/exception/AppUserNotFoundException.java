package in.clouthink.daas.sbb.account.exception;

/**
 *
 */
public class AppUserNotFoundException extends AppUserException {

	public AppUserNotFoundException() {
	}

	public AppUserNotFoundException(String message) {
		super(message);
	}

	public AppUserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppUserNotFoundException(Throwable cause) {
		super(cause);
	}
}
