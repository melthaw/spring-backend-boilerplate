package in.clouthink.daas.sbb.audit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.audit.event",
				"in.clouthink.daas.sbb.audit.service",
				"in.clouthink.daas.sbb.audit.repository"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.audit.repository"})
public class AuditServiceModuleConfiguration {

}
