package in.clouthink.daas.sbb.news.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.news.rest.dto.*;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface NoticeRestSupport {

	Page<NoticeSummary> listNotice(NoticeQueryParameter queryRequest);

	NoticeDetail getNoticeDetail(String id);

	String createNotice(SaveNoticeParameter request, SysUser user);

	void updateNotice(String id, SaveNoticeParameter request, SysUser user);

	void deleteNotice(String id, SysUser user);

	void publishNotice(String id, SysUser user);

	void unpublishNotice(String id, SysUser user);

	Page<ReadSummary> listReadHistory(String id, PageQueryParameter queryParameter);

}
