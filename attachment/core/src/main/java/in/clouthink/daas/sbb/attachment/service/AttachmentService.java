package in.clouthink.daas.sbb.attachment.service;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
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

	void markAttachmentAsDownloaded(Attachment attachment, SysUser user);

	boolean isAttachmentDownloadedByUser(Attachment attachment, SysUser user);

	int countAttachmentDownloadHistory(Attachment attachment);

	Attachment createAttachment(SaveAttachmentRequest parameter, SysUser user);

	void updateAttachment(String id, SaveAttachmentRequest parameter, SysUser user);

	void deleteAttachment(String id, SysUser user);

	void publishAttachment(String id, SysUser user);

	void unpublishAttachment(String id, SysUser user);

	Page<AttachmentDownloadHistory> listDownloadHistory(String id, PageQueryRequest queryRequest);
}
