package in.clouthink.daas.sbb.audit.exception;

/**
 * @author dz
 */
public class AuditEventException extends RuntimeException {

	public AuditEventException() {
	}

	public AuditEventException(String message) {
		super(message);
	}

	public AuditEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuditEventException(Throwable cause) {
		super(cause);
	}

}
