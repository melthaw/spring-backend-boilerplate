package in.clouthink.daas.sbb.storage;

import in.clouthink.daas.sbb.storage.local.StorageServiceLocalFsImpl;
import in.clouthink.daas.sbb.storage.service.StorageService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ConfigurationProperties.class})
public class LocalfsModuleConfiguration {

	@Bean
	public StorageService storageService() {
		return new StorageServiceLocalFsImpl();
	}

}
