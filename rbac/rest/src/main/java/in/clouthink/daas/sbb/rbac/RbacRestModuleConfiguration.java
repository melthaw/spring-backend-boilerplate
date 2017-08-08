package in.clouthink.daas.sbb.rbac;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.dashboard.rest"})
@Import({RbacServiceModuleConfiguration.class, RbacMenuConfiguration.class})
public class RbacRestModuleConfiguration {

}
