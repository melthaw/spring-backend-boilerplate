package in.clouthink.daas.sbb.news.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.news.rest.dto.*;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface NoticeRestSupport {

	Page<NoticeSummary> listNotice(NoticeQueryParameter queryRequest);

	NoticeDetail getNoticeDetail(String id);

	String createNotice(SaveNoticeParameter request, User user);

	void updateNotice(String id, SaveNoticeParameter request, User user);

	void deleteNotice(String id, User user);

	void publishNotice(String id, User user);

	void unpublishNotice(String id, User user);

	Page<ReadSummary> listReadHistory(String id, PageQueryParameter queryParameter);

}
