package in.clouthink.daas.sbb.storage;

import in.clouthink.daas.sbb.storage.service.StorageService;
import in.clouthink.daas.sbb.storage.service.impl.StorageServiceGridfsImpl;
import in.clouthink.daas.fss.mongodb.MongoModuleConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"in.clouthink.daas.fss.rest"})
//@EnableConfigurationProperties(GridfsConfigureProperties.class)
@Import({MongoModuleConfiguration.class})
public class GridfsModuleConfiguration {

	@Bean
	public StorageService storageService() {
		return new StorageServiceGridfsImpl();
	}

}
