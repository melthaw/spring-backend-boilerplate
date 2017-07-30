package in.clouthink.daas.sbb.audit.service.impl;

import in.clouthink.daas.sbb.audit.domain.model.AuditEvent;
import in.clouthink.daas.sbb.audit.domain.request.AuditEventQueryRequest;
import in.clouthink.daas.sbb.audit.event.RemoveEventConstants;
import in.clouthink.daas.sbb.audit.event.RemoveEventObject;
import in.clouthink.daas.sbb.audit.exception.AuditEventException;
import in.clouthink.daas.sbb.audit.repository.AuditEventRepository;
import in.clouthink.daas.sbb.audit.service.AuditEventService;
import in.clouthink.daas.sbb.shared.DomainConstants;
import in.clouthink.daas.sbb.shared.util.DateTimeUtils;
import in.clouthink.daas.audit.spi.AuditEventDispatcher;
import in.clouthink.daas.edm.Edms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class AuditEventServiceImpl implements AuditEventService {

	@Autowired
	private AuditEventRepository auditEventRepository;

	@Override
	public Page<AuditEvent> findAuditEventPage(AuditEventQueryRequest queryRequest) {
		if (StringUtils.isEmpty(queryRequest.getRealm())) {
			throw new AuditEventException("realm不能为空");
		}
		return auditEventRepository.queryPage(queryRequest);
	}

	@Override
	public AuditEvent findById(String id) {
		return auditEventRepository.findById(id);
	}

	@Override
	public void deleteAuditEventsByDay(String realm, Date day) {
		if (StringUtils.isEmpty(realm)) {
			throw new AuditEventException("realm不能为空");
		}
		if (System.currentTimeMillis() - day.getTime() < 15 * DomainConstants.HOW_LONG_OF_ONE_DAY) {
			throw new AuditEventException("只能删除15天前的数据");
		}

		Date from = DateTimeUtils.startOfDay(day);
		Date to = DateTimeUtils.endOfDay(day);
		auditEventRepository.deleteByRealmAndRequestedAtBetween(realm, from, to);
	}

	@Override
	public void deleteAuditEventsBeforeDay(String realm, Date day) {
		if (StringUtils.isEmpty(realm)) {
			throw new AuditEventException("realm不能为空");
		}
		if (System.currentTimeMillis() - day.getTime() < 15 * DomainConstants.HOW_LONG_OF_ONE_DAY) {
			throw new AuditEventException("只能删除15天前的数据");
		}

		Edms.getEdm(AuditEventDispatcher.QUEUE_NAME)
			.dispatch(RemoveEventConstants.DELETE_AUDIT_EVENT_BEFORE_DAY, new RemoveEventObject(realm, day));
	}

}
