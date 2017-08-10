package in.clouthink.daas.sbb.sms;

import in.clouthink.daas.sbb.sms.history.SmsHistoryModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AliyunSmsModuleConfiguration.class, SmsHistoryModuleConfiguration.class})
public class SmsRestModuleConfiguration {

}
