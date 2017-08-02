package in.clouthink.daas.sbb.rbac.exception;

/**
 * permission not found exception
 */
public class PermissionNotFoundException extends PermissionException {

	public PermissionNotFoundException() {
	}

	public PermissionNotFoundException(String message) {
		super(message);
	}

	public PermissionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionNotFoundException(Throwable cause) {
		super(cause);
	}

}
