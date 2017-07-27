package in.clouthink.daas.sbb.storage.gridfs;

import in.clouthink.daas.fss.mongodb.MongoModuleConfiguration;
import in.clouthink.daas.sbb.storage.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@EnableConfigurationProperties(GridfsConfigureProperties.class)
@Import({MongoModuleConfiguration.class})
public class GridfsModuleConfiguration {

	@Bean
	public StorageService storageService() {
		return new StorageServiceGridfsImpl();
	}

}
