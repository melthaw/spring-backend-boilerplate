package in.clouthink.daas.sbb.account.exception;

/**
 *
 */
public class SysUserRequiredException extends SysUserException {

	public SysUserRequiredException() {
	}

	public SysUserRequiredException(String message) {
		super(message);
	}

	public SysUserRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public SysUserRequiredException(Throwable cause) {
		super(cause);
	}
}
