package in.clouthink.daas.sbb.attachment.domain.request;

import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;

import java.util.Date;

/**
 *
 */
public interface AttachmentQueryRequest extends DateRangedQueryRequest {

	String getTitle();

	String getCategory();

	Boolean getPublished();

	Date getCreatedAtBegin();

	Date getCreatedAtEnd();

}
