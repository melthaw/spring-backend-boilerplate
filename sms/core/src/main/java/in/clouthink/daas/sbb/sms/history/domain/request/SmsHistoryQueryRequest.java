package in.clouthink.daas.sbb.sms.history.domain.request;

import in.clouthink.daas.sbb.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;

/**
 */
public interface SmsHistoryQueryRequest extends DateRangedQueryRequest {

	String getCellphone();

	String getCategory();

	SmsHistory.SmsStatus getStatus();

}
