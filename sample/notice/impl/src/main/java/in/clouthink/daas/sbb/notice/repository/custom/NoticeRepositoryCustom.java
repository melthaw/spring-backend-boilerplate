package in.clouthink.daas.sbb.notice.repository.custom;

import in.clouthink.daas.sbb.notice.domain.model.Notice;
import in.clouthink.daas.sbb.notice.domain.request.NoticeQueryRequest;
import org.springframework.data.domain.Page;

public interface NoticeRepositoryCustom {

	Page<Notice> queryPage(NoticeQueryRequest queryRequest);

	void updateReadCounter(String id, int readCounter);
}
