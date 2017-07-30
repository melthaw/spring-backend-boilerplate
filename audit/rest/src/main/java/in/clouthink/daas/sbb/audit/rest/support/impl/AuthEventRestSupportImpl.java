package in.clouthink.daas.sbb.audit.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.rest.support.AuthEventRestSupport;
import in.clouthink.daas.sbb.audit.service.AuthEventService;
import in.clouthink.daas.sbb.audit.rest.dto.AuthEventQueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author dz
 */
@Component
public class AuthEventRestSupportImpl implements AuthEventRestSupport {

	@Autowired
	private AuthEventService authEventService;

	@Override
	public Page<AuthEvent> listAuthEventPage(AuthEventQueryParameter queryRequest) {
		return authEventService.listUserAuthEvents(queryRequest);
	}

	@Override
	public AuthEvent getAuthEventDetail(String id) {
		return authEventService.findUserAuthEventById(id);
	}

	@Override
	public void deleteAuthEventsByDay(String realm, Date day, User byWho) {
		if (!"administrator".equals(byWho.getUsername())) {
			throw new RuntimeException("只有超级管理员能删除用户登录审计数据.");
		}

		authEventService.deleteAuthEventsByDay(realm, day);
	}

	@Override
	public void deleteAuthEventsBeforeDay(String realm, Date day, User byWho) {
		if (!"administrator".equals(byWho.getUsername())) {
			throw new RuntimeException("只有超级管理员能删除用户登录审计数据.");
		}

		authEventService.deleteAuthEventsBeforeDay(realm, day);
	}
}
