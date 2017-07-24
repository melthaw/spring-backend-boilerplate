package in.clouthink.daas.sbb.security.frontend.audit;

import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.repository.AuditEventRepository;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


public class AuditEventPersisterImpl implements AuditEventPersister {

	@Autowired
	@Lazy
	private AuditEventRepository auditEventRepository;

	@Override
	public AuditEvent saveAuditEvent(in.clouthink.daas.audit.core.AuditEvent o) {
		AuditEvent auditEvent = new AuditEvent();
		BeanUtils.copyProperties(o, auditEvent);
		if (o.getRequestedBy() != null) {
			auditEvent.setRequestedBy(o.getRequestedBy().toString());
		}
		auditEvent.setRealm("frontend");
		return auditEventRepository.save(auditEvent);
	}

}
