package in.clouthink.daas.sbb.rbac;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.rbac.rest.controller",
				"in.clouthink.daas.sbb.rbac.rest.service",
				"in.clouthink.daas.sbb.rbac.rest.support.impl"})
@Import({RbacServiceModuleConfiguration.class, RbacMenuConfiguration.class})
public class RbacRestModuleConfiguration {

}
