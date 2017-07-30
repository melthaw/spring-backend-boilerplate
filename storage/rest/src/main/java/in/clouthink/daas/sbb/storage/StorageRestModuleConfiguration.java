package in.clouthink.daas.sbb.storage;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.fss.rest", "in.clouthink.daas.sbb.storage.rest"})

public class StorageRestModuleConfiguration {

}
