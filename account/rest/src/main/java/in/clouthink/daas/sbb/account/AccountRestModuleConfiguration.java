package in.clouthink.daas.sbb.account;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.account.rest.controller", "in.clouthink.daas.sbb.account.rest.support.impl"})
@Import({AccountServiceModuleConfiguration.class, AccountMenuConfiguration.class})
public class AccountRestModuleConfiguration {

}
