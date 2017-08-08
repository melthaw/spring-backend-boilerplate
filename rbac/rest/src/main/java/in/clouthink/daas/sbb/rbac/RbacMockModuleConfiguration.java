package in.clouthink.daas.sbb.rbac;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.rbac.rest.controller", "in.clouthink.daas.sbb.rbac.rest.support.mock"})
public class RbacMockModuleConfiguration {

}
