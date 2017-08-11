package in.clouthink.daas.sbb.rbac;

import in.clouthink.daas.sbb.rbac.service.DefaultResourceService;
import in.clouthink.daas.sbb.rbac.service.ResourceService;
import in.clouthink.daas.sbb.rbac.spi.ResourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.rbac.impl.service", "in.clouthink.daas.sbb.rbac.impl.repository"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.rbac.impl.repository"})
public class RbacServiceModuleConfiguration {

	@Bean
	@Autowired(required = false)
	public ResourceService resourceServiceImpl(List<ResourceProvider> resourceProviderList) {
		DefaultResourceService result = new DefaultResourceService();
		result.setResourceProviderList(resourceProviderList);
		return result;
	}

}
