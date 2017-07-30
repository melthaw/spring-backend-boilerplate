package in.clouthink.daas.sbb.account;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.account.service"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.account.repository"})
@EnableConfigurationProperties(AccountConfigurationProperties.class)
public class AccountServiceModuleConfiguration {

}
