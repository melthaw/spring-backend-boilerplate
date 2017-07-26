package in.clouthink.daas.sbb.event.sms.service.impl;

import in.clouthink.daas.sbb.account.domain.model.Passcodes;
import in.clouthink.daas.sbb.event.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.event.sms.history.service.SmsHistoryService;
import in.clouthink.daas.sbb.event.sms.service.SmsService;
import in.clouthink.daas.sbb.event.sms.support.AdvancedAliyunOptions;
import in.clouthink.daas.sbb.event.sms.support.SmsException;
import in.clouthink.daas.edm.sms.AdvancedSmsMessage;
import in.clouthink.daas.edm.sms.SmsSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 */
public class SmsServiceImpl implements SmsService {

	private static final Log logger = LogFactory.getLog(SmsServiceImpl.class);

	public final static String REGISTER_PASSCODE = "{\"passcode\":\"%s\"}";

	public final static String FORGET_PWD_PASSCODE = "{\"passcode\":\"%s\"}";

	@Autowired
	private SmsSender smsSender;

	@Autowired
	private AdvancedAliyunOptions advancedYunpianOptions;

	@Autowired
	private SmsHistoryService smsHistoryService;

	@Override
	public void sendPasscode4Register(String cellphone, String passcode) {
		AdvancedSmsMessage smsMessage = new AdvancedSmsMessage();
		smsMessage.setCellphone(cellphone);
		smsMessage.setMessage(String.format(REGISTER_PASSCODE, passcode));
		smsMessage.setOptions(advancedYunpianOptions);
		sendSms(smsMessage, Passcodes.REGISTER);
	}

	@Override
	public void sendPasscode4ForgetPassword(String cellphone, String passcode) {
		AdvancedSmsMessage smsMessage = new AdvancedSmsMessage();
		smsMessage.setCellphone(cellphone);
		smsMessage.setMessage(String.format(FORGET_PWD_PASSCODE, passcode));
		smsMessage.setOptions(advancedYunpianOptions);
		sendSms(smsMessage, Passcodes.FORGET_PASSWORD);
	}

	private void sendSms(AdvancedSmsMessage message, String category) {
		SmsHistory smsHistory = new SmsHistory();
		smsHistory.setCellphone(message.getCellphone());
		smsHistory.setCreatedAt(new Date());
		smsHistory.setCategory(category);
		smsHistory.setMessage(message.getMessage());
		try {
			smsSender.send(message);
			smsHistory.setStatus(SmsHistory.SmsStatus.SENT);
		}
		catch (Exception e) {
			smsHistory.setStatus(SmsHistory.SmsStatus.FAILED);
			throw new SmsException(e);
		}
		finally {
			smsHistoryService.save(smsHistory);
		}
	}

}
