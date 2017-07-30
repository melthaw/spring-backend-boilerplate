package in.clouthink.daas.sbb.sms;

import in.clouthink.daas.sbb.sms.mock.impl.SmsServiceMocker;
import in.clouthink.daas.sbb.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.sms.mock"})
public class SmsMockModuleConfiguration {

	@Bean
	@Autowired
	public SmsService smsServiceImpl() {
		return new SmsServiceMocker();
	}

}
