package in.clouthink.daas.sbb.sms.history.rest.support.mock;

import in.clouthink.daas.sbb.sms.history.domain.request.SmsHistoryQueryRequest;
import in.clouthink.daas.sbb.sms.history.rest.dto.SmsHistorySummary;
import in.clouthink.daas.sbb.sms.history.rest.support.SmsHistorySupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * SmsHistorySupport mocker
 *
 * @author dz
 */
@Component
public class SmsHistorySupportMockImpl implements SmsHistorySupport {

	@Override
	public Page<SmsHistorySummary> findPage(SmsHistoryQueryRequest request) {
		return null;
	}
}
