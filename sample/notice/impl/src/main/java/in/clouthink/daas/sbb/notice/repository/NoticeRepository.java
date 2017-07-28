package in.clouthink.daas.sbb.notice.repository;

import in.clouthink.daas.sbb.notice.domain.model.Notice;
import in.clouthink.daas.sbb.notice.repository.custom.NoticeRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 */
public interface NoticeRepository extends AbstractRepository<Notice>, NoticeRepositoryCustom {

	Page<Notice> findByPublished(boolean published, Pageable pageable);

}