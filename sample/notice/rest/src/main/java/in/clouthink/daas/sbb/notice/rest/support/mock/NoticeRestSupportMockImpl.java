package in.clouthink.daas.sbb.notice.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.notice.rest.dto.*;
import in.clouthink.daas.sbb.notice.rest.support.NoticeRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * NoticeRestSupport mocker
 *
 * @author dz
 */
@Component
public class NoticeRestSupportMockImpl implements NoticeRestSupport {
	@Override
	public Page<NoticeSummary> listNotice(NoticeQueryParameter queryRequest) {
		return null;
	}

	@Override
	public NoticeDetail getNoticeDetail(String id) {
		return null;
	}

	@Override
	public String createNotice(SaveNoticeParameter request, User user) {
		return null;
	}

	@Override
	public void updateNotice(String id, SaveNoticeParameter request, User user) {

	}

	@Override
	public void deleteNotice(String id, User user) {

	}

	@Override
	public void publishNotice(String id, User user) {

	}

	@Override
	public void unpublishNotice(String id, User user) {

	}

	@Override
	public Page<ReadSummary> listReadHistory(String id, PageQueryParameter queryParameter) {
		return null;
	}
}
