package in.clouthink.daas.sbb.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.security.impl"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.security.impl.auth"})
public class SecurityModuleConfiguration {

}
