package in.clouthink.daas.sbb.audit.event;

import in.clouthink.daas.audit.spi.AuditEventDispatcher;
import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.sbb.audit.repository.AuthEventRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 */
@Component
public class RemoveAuthEventListener implements EventListener<RemoveEventObject>, InitializingBean {

	private static final Log logger = LogFactory.getLog(RemoveAuthEventListener.class);

	@Autowired
	private AuthEventRepository authEventRepository;

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

		authEventRepository.deleteByRealmAndLoginAtBefore(eventObject.getRealm(), eventObject.getBeforeDay());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Edms.getEdm(AuditEventDispatcher.QUEUE_NAME).register(RemoveEventConstants.DELETE_AUTH_EVENT_BEFORE_DAY, this);
	}

}
