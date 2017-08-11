package in.clouthink.daas.sbb.storage;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.fss.rest",
				"in.clouthink.daas.sbb.storage.rest.controller",
				"in.clouthink.daas.sbb.storage.rest.support.impl"})
@Import({GridfsModuleConfiguration.class})
public class StorageModuleConfiguration {

}
