package in.clouthink.daas.sbb.audit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@org.springframework.context.annotation.Configuration
@ComponentScan({"in.clouthink.daas.sbb.audit"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.audit.repository"})
public class AuditModuleConfiguration {

}
