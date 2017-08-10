package in.clouthink.daas.sbb.sms.mock;

import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.edm.sms.AdvancedSmsMessage;
import in.clouthink.daas.sbb.passcode.model.PasscodeMessage;
import in.clouthink.daas.sbb.passcode.model.Passcodes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dz
 */
public class PasscodeMessageEventListener implements EventListener<PasscodeMessage>, InitializingBean {

	private static final Log logger = LogFactory.getLog(PasscodeMessageEventListener.class);

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
		smsMessage.setMessage(passcode);
		sendSms(smsMessage, Passcodes.REGISTER);
		logger.debug(String.format("Sending register message %s to %s", passcode, cellphone));
	}

	public void sendPasscode4ForgetPassword(String cellphone, String passcode) {
		AdvancedSmsMessage smsMessage = new AdvancedSmsMessage();
		smsMessage.setCellphone(cellphone);
		smsMessage.setMessage(passcode);
		sendSms(smsMessage, Passcodes.FORGET_PASSWORD);
		logger.debug(String.format("Sending forget password message %s to %s", passcode, cellphone));
	}

	private void sendSms(AdvancedSmsMessage message, String category) {
		Map smsHistory = new HashMap<>();
		smsHistory.put("cellphone", message.getCellphone());
		smsHistory.put("createdAt", new Date());
		smsHistory.put("category", category);
		smsHistory.put("message", message.getMessage());
		smsHistory.put("status", "SENT");
		Edms.getEdm("sms-history").dispatch("sms-history", smsHistory);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Edms.getEdm("sms").register(Passcodes.REGISTER, this);
		Edms.getEdm("sms").register(Passcodes.FORGET_PASSWORD, this);
	}

}
