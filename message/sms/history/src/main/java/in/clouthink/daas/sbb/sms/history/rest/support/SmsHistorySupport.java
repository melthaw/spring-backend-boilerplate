package in.clouthink.daas.sbb.sms.history.rest.support;

import in.clouthink.daas.sbb.sms.history.domain.request.SmsHistoryQueryRequest;
import in.clouthink.daas.sbb.sms.history.rest.dto.SmsHistorySummary;
import org.springframework.data.domain.Page;

public interface SmsHistorySupport {

	Page<SmsHistorySummary> findPage(SmsHistoryQueryRequest request);
}
