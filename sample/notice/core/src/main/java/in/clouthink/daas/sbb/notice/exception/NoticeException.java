package in.clouthink.daas.sbb.notice.exception;

/**
 *
 */
public class NoticeException extends RuntimeException {
	public NoticeException() {
	}

	public NoticeException(String message) {
		super(message);
	}

	public NoticeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoticeException(Throwable cause) {
		super(cause);
	}
}
