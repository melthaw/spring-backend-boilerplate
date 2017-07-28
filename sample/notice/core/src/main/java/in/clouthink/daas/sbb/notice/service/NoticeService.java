package in.clouthink.daas.sbb.notice.service;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.notice.domain.model.Notice;
import in.clouthink.daas.sbb.notice.domain.model.NoticeReadHistory;
import in.clouthink.daas.sbb.notice.domain.request.NoticeQueryRequest;
import in.clouthink.daas.sbb.notice.domain.request.SaveNoticeRequest;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface NoticeService {

	Page<Notice> listNotices(NoticeQueryRequest queryParameter);

	Notice findNoticeById(String id);

	void markNoticeAsRead(Notice notice, SysUser user);

	boolean isNoticeReadByUser(Notice notice, SysUser user);

	int countNoticeReadHistory(Notice notice);

	Notice createNotice(SaveNoticeRequest notice, SysUser user);

	void updateNotice(String id, SaveNoticeRequest notice, SysUser user);

	void deleteNotice(String id, SysUser user);

	void publishNotice(String id, SysUser user);

	void unpublishNotice(String id, SysUser user);

	Page<NoticeReadHistory> listReadHistory(String id, PageQueryRequest queryRequest);

}
