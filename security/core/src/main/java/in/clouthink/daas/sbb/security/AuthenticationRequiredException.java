package in.clouthink.daas.sbb.security;

/**
 * If no authentication found in the security context, throw this.
 *
 * @author dz
 */
public class AuthenticationRequiredException extends RuntimeException {

	public AuthenticationRequiredException() {

	}

	public AuthenticationRequiredException(String message) {
		super(message);
	}

	public AuthenticationRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationRequiredException(Throwable cause) {
		super(cause);
	}

	public AuthenticationRequiredException(String message,
										   Throwable cause,
										   boolean enableSuppression,
										   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
