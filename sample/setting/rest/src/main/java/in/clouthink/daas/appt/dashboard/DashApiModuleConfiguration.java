package in.clouthink.daas.appt.dashboard;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.appt.dashboard.rest", "in.clouthink.daas.fss"})
public class DashApiModuleConfiguration {

}
