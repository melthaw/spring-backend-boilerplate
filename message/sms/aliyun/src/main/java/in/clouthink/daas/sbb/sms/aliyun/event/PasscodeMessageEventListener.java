package in.clouthink.daas.sbb.sms.aliyun.event;

import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.edm.sms.AdvancedSmsMessage;
import in.clouthink.daas.edm.sms.SmsSender;
import in.clouthink.daas.sbb.passcode.model.PasscodeMessage;
import in.clouthink.daas.sbb.passcode.model.Passcodes;
import in.clouthink.daas.sbb.sms.aliyun.impl.AdvancedAliyunOptions;
import in.clouthink.daas.sbb.sms.aliyun.impl.SmsException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dz
 */
public class PasscodeMessageEventListener implements EventListener<PasscodeMessage>, InitializingBean {

	public final static String REGISTER_PASSCODE = "{\"passcode\":\"%s\"}";

	public final static String FORGET_PWD_PASSCODE = "{\"passcode\":\"%s\"}";

	@Autowired
	private SmsSender smsSender;

	@Autowired
	private AdvancedAliyunOptions advancedYunpianOptions;

	@Override
	public void onEvent(PasscodeMessage passcodeMessage) {
		if (passcodeMessage == null) {
			return;
		}
		if (Passcodes.REGISTER.equalsIgnoreCase(passcodeMessage.getCategory())) {
			sendPasscode4Register(passcodeMessage.getCellphone(), passcodeMessage.getPasscode());
		}
		else if (Passcodes.FORGET_PASSWORD.equalsIgnoreCase(passcodeMessage.getCategory())) {
			sendPasscode4ForgetPassword(passcodeMessage.getCellphone(), passcodeMessage.getPasscode());
		}
	}

	public void sendPasscode4Register(String cellphone, String passcode) {
		AdvancedSmsMessage smsMessage = new AdvancedSmsMessage();
		smsMessage.setCellphone(cellphone);
		smsMessage.setMessage(String.format(REGISTER_PASSCODE, passcode));
		smsMessage.setOptions(advancedYunpianOptions);
		sendSms(smsMessage, Passcodes.REGISTER);
	}

	public void sendPasscode4ForgetPassword(String cellphone, String passcode) {
		AdvancedSmsMessage smsMessage = new AdvancedSmsMessage();
		smsMessage.setCellphone(cellphone);
		smsMessage.setMessage(String.format(FORGET_PWD_PASSCODE, passcode));
		smsMessage.setOptions(advancedYunpianOptions);
		sendSms(smsMessage, Passcodes.FORGET_PASSWORD);
	}

	private void sendSms(AdvancedSmsMessage message, String category) {
		Map smsHistory = new HashMap<>();
		smsHistory.put("cellphone", message.getCellphone());
		smsHistory.put("createdAt", new Date());
		smsHistory.put("category", category);
		smsHistory.put("message", message.getMessage());
		try {
			smsSender.send(message);
			smsHistory.put("status", "SENT");
		}
		catch (Exception e) {
			smsHistory.put("status", "FAILED");
			smsHistory.put("reason", e.getMessage());
			throw new SmsException(e);
		}
		finally {
			Edms.getEdm("sms-history").dispatch("sms-history", smsHistory);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Edms.getEdm("sms").register(Passcodes.REGISTER, this);
		Edms.getEdm("sms").register(Passcodes.FORGET_PASSWORD, this);
	}

}
