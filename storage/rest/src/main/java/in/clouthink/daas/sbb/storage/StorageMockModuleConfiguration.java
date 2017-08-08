package in.clouthink.daas.sbb.storage;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.storage.rest.controller", "in.clouthink.daas.sbb.storage.rest.support.mock"})
public class StorageMockModuleConfiguration {

}
