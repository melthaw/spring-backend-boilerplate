package in.clouthink.daas.appt.dashboard;

import in.clouthink.daas.plugin.annotation.Plugin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@Plugin(extensionPoints= {})
@ComponentScan({"in.clouthink.daas.appt.dashboard.rest", "in.clouthink.daas.fss"})
public class DashApiModuleConfiguration {

}
