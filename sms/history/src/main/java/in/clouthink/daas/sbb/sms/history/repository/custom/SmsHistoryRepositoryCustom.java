package in.clouthink.daas.sbb.sms.history.repository.custom;

import in.clouthink.daas.sbb.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.sms.history.domain.request.SmsHistoryQueryRequest;
import in.clouthink.daas.sbb.shared.repository.custom.AbstractCustomRepository;
import org.springframework.data.domain.Page;

/**
 * 短信发送记录持久层扩展
 */
public interface SmsHistoryRepositoryCustom extends AbstractCustomRepository {

	Page<SmsHistory> queryPage(SmsHistoryQueryRequest parameter);

}
