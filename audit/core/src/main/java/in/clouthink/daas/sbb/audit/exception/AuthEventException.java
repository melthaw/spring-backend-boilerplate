package in.clouthink.daas.sbb.audit.exception;

/**
 * @author dz
 */
public class AuthEventException extends RuntimeException {

	public AuthEventException() {
	}

	public AuthEventException(String message) {
		super(message);
	}

	public AuthEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthEventException(Throwable cause) {
		super(cause);
	}

}
