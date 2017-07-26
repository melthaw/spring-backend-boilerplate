package in.clouthink.daas.sbb.audit.repository.custom;

import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.domain.request.AuditEventQueryRequest;
import org.springframework.data.domain.Page;

public interface AuditEventRepositoryCustom {

	Page<AuditEvent> queryPage(AuditEventQueryRequest queryRequest);

}
