package in.clouthink.daas.sbb.sms.history.event;

import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.sbb.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.sms.history.service.SmsHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author dz
 */
@Component
public class SmsHistoryEventListener implements EventListener<Map>, InitializingBean {

	@Autowired
	private SmsHistoryService smsHistoryService;

	@Override
	public void onEvent(Map event) {
		Optional.ofNullable(event).ifPresent(history -> {
			SmsHistory smsHistory = new SmsHistory();
			BeanUtils.copyProperties(event, smsHistory);
			smsHistoryService.save(smsHistory);
		});
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Edms.getEdm("sms-history").register("sms-history", this);
	}

}
