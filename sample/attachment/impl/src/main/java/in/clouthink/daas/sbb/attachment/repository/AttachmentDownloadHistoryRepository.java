package in.clouthink.daas.sbb.attachment.repository;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.domain.model.AttachmentDownloadHistory;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * the attachment download history persist service
 */
public interface AttachmentDownloadHistoryRepository extends AbstractRepository<AttachmentDownloadHistory> {

	Page<AttachmentDownloadHistory> findByAttachment(Attachment attachment, Pageable pageable);

	AttachmentDownloadHistory findByAttachmentAndDownloadedBy(Attachment attachment, User user);

	int countByAttachment(Attachment attachment);

}