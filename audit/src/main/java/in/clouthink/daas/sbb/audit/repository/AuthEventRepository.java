package in.clouthink.daas.sbb.audit.repository;

import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.repository.custom.AuthEventRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

import java.util.Date;
import java.util.List;

public interface AuthEventRepository extends AbstractRepository<AuthEvent>, AuthEventRepositoryCustom {

	List<AuthEvent> findListByLoginAtBetween(Date from, Date to);

	long deleteByRealmAndLoginAtBetween(String realm, Date from, Date to);

	long deleteByRealmAndLoginAtBefore(String realm, Date day);

}
