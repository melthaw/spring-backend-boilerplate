package in.clouthink.daas.sbb.sms.history;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.sms.history.rest.controller",
				"in.clouthink.daas.sbb.sms.history.rest.support.mock"})
public class SmsHistoryMockModuleConfiguration {

}
