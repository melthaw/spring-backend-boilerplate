package in.clouthink.daas.sbb.news.rest.dto;


import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.notice.domain.model.Notice;
import in.clouthink.daas.sbb.notice.domain.request.ReadNoticeEvent;

/**
 */
public class ReadNoticeEventObject implements ReadNoticeEvent {

	private Notice notice;

	private SysUser user;

	public ReadNoticeEventObject(Notice notice, SysUser user) {
		this.notice = notice;
		this.user = user;
	}

	@Override
	public Notice getNotice() {
		return notice;
	}

	@Override
	public SysUser getUser() {
		return user;
	}
}
