package in.clouthink.daas.sbb.event.listener;

import in.clouthink.daas.sbb.account.domain.model.PasscodeMessage;
import in.clouthink.daas.sbb.account.domain.model.Passcodes;
import in.clouthink.daas.sbb.event.sms.service.SmsService;
import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dz
 */
@Component
public class PasscodeMessageEventListener implements EventListener<PasscodeMessage>, InitializingBean {

	@Autowired
	private SmsService smsService;

	@Override
	public void onEvent(PasscodeMessage passcodeMessage) {
		if (passcodeMessage == null) {
			return;
		}
		if (Passcodes.REGISTER.equalsIgnoreCase(passcodeMessage.getCategory())) {
			smsService.sendPasscode4Register(passcodeMessage.getCellphone(), passcodeMessage.getPasscode());
		}
		else if (Passcodes.FORGET_PASSWORD.equalsIgnoreCase(passcodeMessage.getCategory())) {
			smsService.sendPasscode4ForgetPassword(passcodeMessage.getCellphone(), passcodeMessage.getPasscode());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Edms.getEdm("sms").register(Passcodes.REGISTER, this);
		Edms.getEdm("sms").register(Passcodes.FORGET_PASSWORD, this);
	}

}
