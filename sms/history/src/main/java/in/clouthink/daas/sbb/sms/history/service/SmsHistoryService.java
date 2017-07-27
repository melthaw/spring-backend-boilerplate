package in.clouthink.daas.sbb.sms.history.service;

import in.clouthink.daas.sbb.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.sms.history.domain.request.SmsHistoryQueryRequest;
import org.springframework.data.domain.Page;

public interface SmsHistoryService {

	Page<SmsHistory> findPage(SmsHistoryQueryRequest request);

	SmsHistory save(SmsHistory smsHistory);

}
