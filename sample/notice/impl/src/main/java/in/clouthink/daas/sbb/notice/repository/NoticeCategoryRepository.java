package in.clouthink.daas.sbb.notice.repository;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.notice.domain.model.NoticeCategory;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 */
public interface NoticeCategoryRepository extends AbstractRepository<NoticeCategory> {

	Page<NoticeCategory> findByCreatedBy(User createdBy, Pageable pageable);

}