package in.clouthink.daas.sbb.storage.local;

import in.clouthink.daas.sbb.storage.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalfsModuleConfiguration {

	@Bean
	public StorageService storageService() {
		return new StorageServiceLocalFsImpl();
	}

}
