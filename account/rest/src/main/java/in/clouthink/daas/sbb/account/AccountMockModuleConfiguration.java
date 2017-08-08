package in.clouthink.daas.sbb.account;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.account.rest.controller", "in.clouthink.daas.sbb.account.rest.support.mock"})
public class AccountMockModuleConfiguration {

}
