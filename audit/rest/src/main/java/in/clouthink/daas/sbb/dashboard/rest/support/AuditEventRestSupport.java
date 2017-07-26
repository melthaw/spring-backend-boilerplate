package in.clouthink.daas.sbb.dashboard.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.support.request.AuditEventQueryParameter;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 */
public interface AuditEventRestSupport {

	Page<AuditEvent> listAuditEventPage(AuditEventQueryParameter queryRequest);

	AuditEvent getAuditEventDetail(String id);

	void deleteAuditEventsByDay(String realm, Date day, SysUser user);

	void deleteAuditEventsBeforeDay(String realm, Date day, SysUser user);

}
