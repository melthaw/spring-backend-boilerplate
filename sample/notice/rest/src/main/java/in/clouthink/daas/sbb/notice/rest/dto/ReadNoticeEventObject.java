package in.clouthink.daas.sbb.notice.rest.dto;


import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.notice.domain.model.Notice;
import in.clouthink.daas.sbb.notice.domain.request.ReadNoticeEvent;

/**
 */
public class ReadNoticeEventObject implements ReadNoticeEvent {

	private Notice notice;

	private User user;

	public ReadNoticeEventObject(Notice notice, User user) {
		this.notice = notice;
		this.user = user;
	}

	@Override
	public Notice getNotice() {
		return notice;
	}

	@Override
	public User getUser() {
		return user;
	}
}
