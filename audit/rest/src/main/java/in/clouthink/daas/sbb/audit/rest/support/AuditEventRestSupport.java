package in.clouthink.daas.sbb.audit.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.rest.dto.AuditEventQueryParameter;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 */
public interface AuditEventRestSupport {

	Page<AuditEvent> listAuditEventPage(AuditEventQueryParameter queryRequest);

	AuditEvent getAuditEventDetail(String id);

	void deleteAuditEventsByDay(String realm, Date day, User user);

	void deleteAuditEventsBeforeDay(String realm, Date day, User user);

}
