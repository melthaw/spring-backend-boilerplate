package in.clouthink.daas.sbb.sms.history;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.sms.history.repository",
				"in.clouthink.daas.sbb.sms.history.event",
				"in.clouthink.daas.sbb.sms.history.service",
				"in.clouthink.daas.sbb.sms.history.rest.controller",
				"in.clouthink.daas.sbb.sms.history.rest.support.impl"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.sms.history.repository"})
@Import({SmsHistoryMenuConfiguration.class})
public class SmsHistoryModuleConfiguration {

}
