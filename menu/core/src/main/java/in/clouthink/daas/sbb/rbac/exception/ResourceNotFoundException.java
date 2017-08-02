package in.clouthink.daas.sbb.rbac.exception;

/**
 * resource not found exception
 */
public class ResourceNotFoundException extends ResourceException {

	public ResourceNotFoundException() {
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
}
