package in.clouthink.daas.sbb.sms.event;

import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.sbb.passcode.model.PasscodeMessage;
import in.clouthink.daas.sbb.sms.event.listener.PasscodeMessageEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsEventModuleConfiguration {

	@Bean
	public EventListener<PasscodeMessage> passcodeMessageEventListener() {
		return new PasscodeMessageEventListener();
	}

}
