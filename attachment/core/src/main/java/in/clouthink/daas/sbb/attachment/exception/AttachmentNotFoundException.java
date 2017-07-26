package in.clouthink.daas.sbb.attachment.exception;

public class AttachmentNotFoundException extends AttachmentException {

	public AttachmentNotFoundException() {
	}

	public AttachmentNotFoundException(String message) {
		super(message);
	}

	public AttachmentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AttachmentNotFoundException(Throwable cause) {
		super(cause);
	}
}
