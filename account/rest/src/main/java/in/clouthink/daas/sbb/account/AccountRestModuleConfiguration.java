package in.clouthink.daas.sbb.account;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.account.rest"})
@Import({AccountServiceModuleConfiguration.class})
public class AccountRestModuleConfiguration {

}
