package in.clouthink.daas.sbb.audit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.audit.rest.controller", "in.clouthink.daas.sbb.audit.rest.support.mock"})
public class AuditMockModuleConfiguration {

}
