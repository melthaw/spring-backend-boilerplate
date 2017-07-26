package in.clouthink.daas.sbb.storage.exception;

/**
 */
public class FileException extends RuntimeException {

	public FileException() {
	}

	public FileException(String message) {
		super(message);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

}
