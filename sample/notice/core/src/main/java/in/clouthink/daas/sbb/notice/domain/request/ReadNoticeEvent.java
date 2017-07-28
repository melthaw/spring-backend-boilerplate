package in.clouthink.daas.sbb.notice.domain.request;


import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.notice.domain.model.Notice;

/**
 */
public interface ReadNoticeEvent {

	String EVENT_NAME = ReadNoticeEvent.class.getName();

	Notice getNotice();

	SysUser getUser();

}
