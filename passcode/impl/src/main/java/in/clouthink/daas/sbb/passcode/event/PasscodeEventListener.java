package in.clouthink.daas.sbb.passcode.event;

import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.sbb.passcode.engine.PasscodeEngine;
import in.clouthink.daas.sbb.passcode.model.PasscodeRequest;
import in.clouthink.daas.sbb.passcode.model.Passcodes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dz
 */
@Component
public class PasscodeEventListener implements EventListener<PasscodeRequest>, InitializingBean {

	@Autowired
	private PasscodeEngine passcodeEngine;

	@Override
	public void onEvent(PasscodeRequest request) {
		if (request == null) {
			return;
		}
		passcodeEngine.handlePasscodeRequest(request);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Edms.getEdm(Passcodes.EVENT_GROUP_NAME).register(Passcodes.REGISTER, this);
		Edms.getEdm(Passcodes.EVENT_GROUP_NAME).register(Passcodes.FORGET_PASSWORD, this);
	}

}
