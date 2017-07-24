package in.clouthink.daas.sbb.event.sms.service.impl;

import in.clouthink.daas.sbb.account.domain.model.Passcodes;
import in.clouthink.daas.sbb.event.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.event.sms.history.repository.SmsHistoryRepository;
import in.clouthink.daas.sbb.event.sms.service.SmsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 */
public class SmsServiceMocker implements SmsService {

	private static final Log logger = LogFactory.getLog(SmsServiceMocker.class);

	@Autowired
	private SmsHistoryRepository smsHistoryRepository;

	@Override
	public void sendPasscode4Register(String cellphone, String passcode) {
		SmsHistory smsHistory = new SmsHistory();
		smsHistory.setProvider(SmsServiceMocker.class.getCanonicalName());
		smsHistory.setCellphone(cellphone);
		smsHistory.setCreatedAt(new Date());
		smsHistory.setCategory(Passcodes.REGISTER);
		try {
			String message = String.format("Send Register Code '%s' to '%s'", passcode, cellphone);
			logger.info(message);
			smsHistory.setMessage(message);
		}
		catch (Exception e) {
			smsHistory.setStatus(SmsHistory.SmsStatus.FAILED);
			smsHistory.setFailureReason(e + "");
			logger.error(e, e);
		}
		finally {
			smsHistoryRepository.save(smsHistory);
		}
	}

	@Override
	public void sendPasscode4ForgetPassword(String cellphone, String passcode) {
		SmsHistory smsHistory = new SmsHistory();
		smsHistory.setProvider(SmsServiceMocker.class.getCanonicalName());
		smsHistory.setCellphone(cellphone);
		smsHistory.setCreatedAt(new Date());
		smsHistory.setCategory(Passcodes.FORGET_PASSWORD);
		try {
			String message = String.format("Send Forget Password Code '%s' to '%s'", passcode, cellphone);
			logger.info(message);
			smsHistory.setMessage(message);
		}
		catch (Exception e) {
			smsHistory.setStatus(SmsHistory.SmsStatus.FAILED);
			smsHistory.setFailureReason(e + "");
			logger.error(e, e);
		}
		finally {
			smsHistoryRepository.save(smsHistory);
		}
	}

}
