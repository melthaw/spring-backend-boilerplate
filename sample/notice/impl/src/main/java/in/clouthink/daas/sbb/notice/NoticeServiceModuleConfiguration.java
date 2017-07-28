package in.clouthink.daas.sbb.notice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.notice.service"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.notice.repository"})
public class NoticeServiceModuleConfiguration {

}
