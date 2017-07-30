package in.clouthink.daas.sbb.audit.service.impl;

import in.clouthink.daas.audit.spi.AuditEventDispatcher;
import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.audit.domain.request.AuthEventQueryRequest;
import in.clouthink.daas.sbb.audit.event.RemoveEventConstants;
import in.clouthink.daas.sbb.audit.event.RemoveEventObject;
import in.clouthink.daas.sbb.audit.exception.AuthEventException;
import in.clouthink.daas.sbb.audit.repository.AuthEventRepository;
import in.clouthink.daas.sbb.audit.service.AuthEventService;
import in.clouthink.daas.sbb.shared.DomainConstants;
import in.clouthink.daas.sbb.shared.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class AuthEventServiceImpl implements AuthEventService {

	@Autowired
	private AuthEventRepository authEventRepository;

	@Override
	public Page<AuthEvent> listUserAuthEvents(AuthEventQueryRequest queryRequest) {
		if (StringUtils.isEmpty(queryRequest.getRealm())) {
			throw new AuthEventException("realm不能为空");
		}
		return authEventRepository.queryPage(queryRequest);
	}

	@Override
	public AuthEvent findUserAuthEventById(String id) {
		return authEventRepository.findById(id);
	}

	@Override
	public AuthEvent saveUserAuthEvent(AuthEvent authEvent) {
		return authEventRepository.save(authEvent);
	}

	@Override
	public void deleteAuthEventsByDay(String realm, Date day) {
		if (StringUtils.isEmpty(realm)) {
			throw new AuthEventException("realm不能为空");
		}

		if (System.currentTimeMillis() - day.getTime() < 15 * DomainConstants.HOW_LONG_OF_ONE_DAY) {
			throw new AuthEventException("只能删除15天前的数据");
		}

		Date from = DateTimeUtils.startOfDay(day);
		Date to = DateTimeUtils.endOfDay(day);
		authEventRepository.deleteByRealmAndLoginAtBetween(realm, from, to);
	}

	@Override
	public void deleteAuthEventsBeforeDay(String realm, Date day) {
		if (StringUtils.isEmpty(realm)) {
			throw new AuthEventException("realm不能为空");
		}

		if (System.currentTimeMillis() - day.getTime() < 15 * DomainConstants.HOW_LONG_OF_ONE_DAY) {
			throw new AuthEventException("只能删除15天前的数据");
		}

		Edms.getEdm(AuditEventDispatcher.QUEUE_NAME)
			.dispatch(RemoveEventConstants.DELETE_AUTH_EVENT_BEFORE_DAY, new RemoveEventObject(realm, day));
	}

}
