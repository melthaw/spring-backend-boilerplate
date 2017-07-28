package in.clouthink.daas.sbb.news.exception;

/**
 *
 */
public class NewsNotFoundException extends NewsException {

	public NewsNotFoundException() {
	}

	public NewsNotFoundException(String message) {
		super(message);
	}

	public NewsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NewsNotFoundException(Throwable cause) {
		super(cause);
	}
}
