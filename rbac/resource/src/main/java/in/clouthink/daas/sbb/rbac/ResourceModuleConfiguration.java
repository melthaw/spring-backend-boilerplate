package in.clouthink.daas.sbb.rbac;

import in.clouthink.daas.sbb.rbac.service.ResourceMemoryRegistry;
import in.clouthink.daas.sbb.rbac.service.ResourceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceModuleConfiguration {

	@Bean
	@Autowired
	public ResourceRegistry resourceServiceImpl() {
		ResourceMemoryRegistry result = new ResourceMemoryRegistry();
		return result;
	}

}
