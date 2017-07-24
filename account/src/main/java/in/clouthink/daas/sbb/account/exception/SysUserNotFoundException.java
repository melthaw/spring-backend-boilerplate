package in.clouthink.daas.sbb.account.exception;

/**
 *
 */
public class SysUserNotFoundException extends SysUserException {

	public SysUserNotFoundException() {
	}

	public SysUserNotFoundException(String message) {
		super(message);
	}

	public SysUserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SysUserNotFoundException(Throwable cause) {
		super(cause);
	}
}
