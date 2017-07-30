package in.clouthink.daas.sbb.attachment.service;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.domain.model.AttachmentDownloadHistory;
import in.clouthink.daas.sbb.attachment.domain.request.AttachmentQueryRequest;
import in.clouthink.daas.sbb.attachment.domain.request.SaveAttachmentRequest;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import org.springframework.data.domain.Page;

/**
 * the attachment service
 */
public interface AttachmentService {

	Page<Attachment> listAttachments(AttachmentQueryRequest queryParameter);

	Attachment findAttachmentById(String id);

	void markAttachmentAsDownloaded(Attachment attachment, User user);

	boolean isAttachmentDownloadedByUser(Attachment attachment, User user);

	int countAttachmentDownloadHistory(Attachment attachment);

	Attachment createAttachment(SaveAttachmentRequest parameter, User user);

	void updateAttachment(String id, SaveAttachmentRequest parameter, User user);

	void deleteAttachment(String id, User user);

	void publishAttachment(String id, User user);

	void unpublishAttachment(String id, User user);

	Page<AttachmentDownloadHistory> listDownloadHistory(String id, PageQueryRequest queryRequest);
}
