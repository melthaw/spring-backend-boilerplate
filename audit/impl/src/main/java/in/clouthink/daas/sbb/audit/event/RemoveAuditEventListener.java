package in.clouthink.daas.sbb.audit.event;

import in.clouthink.daas.audit.spi.AuditEventDispatcher;
import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.sbb.audit.repository.AuditEventRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 */
@Component
public class RemoveAuditEventListener implements EventListener<RemoveEventObject>, InitializingBean {

	private static final Log logger = LogFactory.getLog(RemoveAuditEventListener.class);

	@Autowired
	private AuditEventRepository auditEventRepository;

	@Override
	public void onEvent(RemoveEventObject eventObject) {
		if (StringUtils.isEmpty(eventObject.getRealm())) {
			logger.warn("The realm is empty or null , nothing happens");
			return;
		}
		if (null == eventObject.getBeforeDay()) {
			logger.warn("The before day is not specified , nothing happens");
			return;
		}

		auditEventRepository.deleteByRealmAndRequestedAtBefore(eventObject.getRealm(), eventObject.getBeforeDay());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Edms.getEdm(AuditEventDispatcher.QUEUE_NAME).register(RemoveEventConstants.DELETE_AUDIT_EVENT_BEFORE_DAY, this);
	}

}
