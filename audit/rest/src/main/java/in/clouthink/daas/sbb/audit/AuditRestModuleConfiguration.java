package in.clouthink.daas.sbb.audit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.audit.rest"})
@Import({AuditServiceModuleConfiguration.class})
public class AuditRestModuleConfiguration {

}
