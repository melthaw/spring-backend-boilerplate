package in.clouthink.daas.sbb.menu.core;

import in.clouthink.daas.sbb.rbac.exception.ResourceException;

/**
 * menu exception
 *
 * @author dz
 */
public class MenuException extends ResourceException {
	public MenuException() {
	}

	public MenuException(String message) {
		super(message);
	}

	public MenuException(String message, Throwable cause) {
		super(message, cause);
	}

	public MenuException(Throwable cause) {
		super(cause);
	}

	public MenuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
