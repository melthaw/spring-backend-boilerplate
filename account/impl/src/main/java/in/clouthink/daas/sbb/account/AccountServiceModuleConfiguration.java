package in.clouthink.daas.sbb.account;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.account.service", "in.clouthink.daas.sbb.account.repository"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.account.repository"})
@EnableConfigurationProperties(AccountPasswordConfigurationProperties.class)
public class AccountServiceModuleConfiguration {

}
