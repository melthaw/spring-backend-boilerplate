package in.clouthink.daas.sbb.audit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.audit.rest.controller", "in.clouthink.daas.sbb.audit.rest.support.impl"})
@Import({AuditServiceModuleConfiguration.class, AuditMenuConfiguration.class})
public class AuditRestModuleConfiguration {

}
