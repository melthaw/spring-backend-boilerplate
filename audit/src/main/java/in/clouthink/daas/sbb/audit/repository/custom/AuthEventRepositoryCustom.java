package in.clouthink.daas.sbb.audit.repository.custom;

import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.domain.request.AuthEventQueryRequest;
import org.springframework.data.domain.Page;

/**
 */
public interface AuthEventRepositoryCustom {

	Page<AuthEvent> queryPage(AuthEventQueryRequest queryRequest);

}
