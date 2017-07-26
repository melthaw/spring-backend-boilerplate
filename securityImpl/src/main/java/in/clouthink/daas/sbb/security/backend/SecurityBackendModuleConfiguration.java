package in.clouthink.daas.sbb.security.backend;

import in.clouthink.daas.sbb.security.SecurityContext;
import in.clouthink.daas.plugin.annotation.Plugin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Plugin(extensionPoints = {SecurityContext.class})
@ComponentScan({"in.clouthink.daas.sbb.security.backend.auth"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.security.backend.auth"})
public class SecurityBackendModuleConfiguration {

}
