package in.clouthink.daas.sbb.attachment.repository;

import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.repository.custom.AttachmentRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * the attachment persist service
 */
public interface AttachmentRepository extends AbstractRepository<Attachment>, AttachmentRepositoryCustom {

	Page<Attachment> findByPublished(boolean published, Pageable pageable);

}