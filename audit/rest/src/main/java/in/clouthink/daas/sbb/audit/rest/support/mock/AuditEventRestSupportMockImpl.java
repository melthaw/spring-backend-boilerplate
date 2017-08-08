package in.clouthink.daas.sbb.audit.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.rest.dto.AuditEventQueryParameter;
import in.clouthink.daas.sbb.audit.rest.support.AuditEventRestSupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AuditEventRestSupport mocker
 *
 * @author dz
 */
@Component
public class AuditEventRestSupportMockImpl implements AuditEventRestSupport {
	@Override
	public Page<AuditEvent> listAuditEventPage(AuditEventQueryParameter queryRequest) {
		return null;
	}

	@Override
	public AuditEvent getAuditEventDetail(String id) {
		return null;
	}

	@Override
	public void deleteAuditEventsByDay(String realm, Date day, User user) {

	}

	@Override
	public void deleteAuditEventsBeforeDay(String realm, Date day, User user) {

	}
}
