package in.clouthink.daas.sbb.notice.repository;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.notice.domain.model.Notice;
import in.clouthink.daas.sbb.notice.domain.model.NoticeReadHistory;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 */
public interface NoticeReadHistoryRepository extends AbstractRepository<NoticeReadHistory> {

	Page<NoticeReadHistory> findByNotice(Notice notice, Pageable pageable);

	NoticeReadHistory findByNoticeAndReadBy(Notice notice, User user);

	int countByNotice(Notice notice);

}