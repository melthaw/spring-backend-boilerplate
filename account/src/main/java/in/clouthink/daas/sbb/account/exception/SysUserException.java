package in.clouthink.daas.sbb.account.exception;

import in.clouthink.daas.sbb.exception.BizException;

/**
 *
 */
public class SysUserException extends BizException {

	public SysUserException() {
	}

	public SysUserException(String message) {
		super(message);
	}

	public SysUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public SysUserException(Throwable cause) {
		super(cause);
	}
}
