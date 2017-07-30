package in.clouthink.daas.sbb.attachment.repository;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.attachment.domain.model.AttachmentCategory;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * the attachment category persist service
 */
public interface AttachmentCategoryRepository extends AbstractRepository<AttachmentCategory> {

	Page<AttachmentCategory> findByCreatedBy(User createdBy, Pageable pageable);

}
