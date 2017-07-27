package in.clouthink.daas.sbb.passcode;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.passcode"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.passcode.repository"})
@EnableConfigurationProperties(PasscodeConfigurationProperties.class)
public class PasscodeServiceModuleConfiguration {

}
