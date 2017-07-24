package in.clouthink.daas.sbb.security.frontend;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.security.frontend.auth"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.security.frontend.auth"})
public class SecurityFrontendModuleConfiguration {

}
