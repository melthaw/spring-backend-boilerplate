package in.clouthink.daas.sbb.rbac;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.dashboard.rest", "in.clouthink.daas.fss"})
public class AccountRestModuleConfiguration {

}
