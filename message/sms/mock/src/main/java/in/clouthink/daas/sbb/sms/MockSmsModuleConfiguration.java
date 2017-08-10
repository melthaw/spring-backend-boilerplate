package in.clouthink.daas.sbb.sms;

import in.clouthink.daas.edm.EventListener;
import in.clouthink.daas.sbb.sms.mock.PasscodeMessageEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockSmsModuleConfiguration {

	@Bean
	public EventListener passcodeMessageEventListener() {
		return new PasscodeMessageEventListener();
	}

}
