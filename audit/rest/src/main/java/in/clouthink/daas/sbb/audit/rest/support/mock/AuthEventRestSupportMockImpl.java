package in.clouthink.daas.sbb.audit.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.rest.dto.AuthEventQueryParameter;
import in.clouthink.daas.sbb.audit.rest.support.AuthEventRestSupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AuthEventRestSupport mocker
 *
 * @author dz
 */
@Component
public class AuthEventRestSupportMockImpl implements AuthEventRestSupport {

	@Override
	public Page<AuthEvent> listAuthEventPage(AuthEventQueryParameter queryRequest) {
		return null;
	}

	@Override
	public AuthEvent getAuthEventDetail(String id) {
		return null;
	}

	@Override
	public void deleteAuthEventsByDay(String realm, Date day, User user) {

	}

	@Override
	public void deleteAuthEventsBeforeDay(String realm, Date day, User user) {

	}
}
