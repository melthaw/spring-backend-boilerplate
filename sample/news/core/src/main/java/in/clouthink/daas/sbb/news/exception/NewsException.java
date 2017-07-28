package in.clouthink.daas.sbb.news.exception;

/**
 *
 */
public class NewsException extends RuntimeException {
	public NewsException() {
	}

	public NewsException(String message) {
		super(message);
	}

	public NewsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NewsException(Throwable cause) {
		super(cause);
	}
}
