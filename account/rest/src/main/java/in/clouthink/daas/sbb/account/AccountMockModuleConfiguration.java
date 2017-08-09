package in.clouthink.daas.sbb.account;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.account.rest.controller", "in.clouthink.daas.sbb.account.rest.support.mock"})
@EnableConfigurationProperties(AccountAdministratorConfigurationProperties.class)
public class AccountMockModuleConfiguration {

	@Bean
	AccountInitializingBean accountInitializingBean() {
		return new AccountInitializingBean();
	}
}
