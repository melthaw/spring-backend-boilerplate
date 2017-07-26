package in.clouthink.daas.sbb.audit.repository;


import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.repository.custom.AuditEventRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

import java.util.Date;
import java.util.List;

public interface AuditEventRepository extends AbstractRepository<AuditEvent>, AuditEventRepositoryCustom {

	List<AuditEvent> findListByRequestedAtBetween(Date from, Date to);

	long deleteByRealmAndRequestedAtBetween(String realm, Date from, Date to);

	long deleteByRealmAndRequestedAtBefore(String realm, Date day);

}
