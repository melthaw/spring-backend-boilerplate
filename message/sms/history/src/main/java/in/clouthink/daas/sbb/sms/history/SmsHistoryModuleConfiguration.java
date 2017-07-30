package in.clouthink.daas.sbb.sms.history;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.sms.history.service", "in.clouthink.daas.sbb.sms.history.repository"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.sms.history.repository"})
public class SmsHistoryModuleConfiguration {

}
