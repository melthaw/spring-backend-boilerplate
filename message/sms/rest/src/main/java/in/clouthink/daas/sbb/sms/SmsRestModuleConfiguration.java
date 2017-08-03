package in.clouthink.daas.sbb.sms;

import in.clouthink.daas.sbb.sms.event.SmsEventModuleConfiguration;
import in.clouthink.daas.sbb.sms.history.SmsHistoryModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.sms.rest"})
@Import({SmsAliyunModuleConfiguration.class,
		 SmsHistoryModuleConfiguration.class,
		 SmsEventModuleConfiguration.class,
		 SmsMenuConfiguration.class})
public class SmsRestModuleConfiguration {

}
