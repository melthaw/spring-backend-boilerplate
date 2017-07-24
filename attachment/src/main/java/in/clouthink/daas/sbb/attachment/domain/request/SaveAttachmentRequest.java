package in.clouthink.daas.sbb.attachment.domain.request;

/**
 * The attachment save request
 */
public interface SaveAttachmentRequest {

	String getCategory();

	String getTitle();

	//不超过140个字
	String getSummary();

	String getFileObjectId();

}
