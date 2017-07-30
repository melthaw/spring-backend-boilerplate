package in.clouthink.daas.sbb.audit.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.rest.dto.AuthEventQueryParameter;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 */
public interface AuthEventRestSupport {

	Page<AuthEvent> listAuthEventPage(AuthEventQueryParameter queryRequest);

	AuthEvent getAuthEventDetail(String id);

	void deleteAuthEventsByDay(String realm, Date day, User user);

	void deleteAuthEventsBeforeDay(String realm, Date day, User user);
}
