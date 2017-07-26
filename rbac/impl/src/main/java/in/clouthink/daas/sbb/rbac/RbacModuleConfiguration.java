package in.clouthink.daas.sbb.rbac;

import in.clouthink.daas.sbb.rbac.impl.service.impl.ResourceServiceImpl;
import in.clouthink.daas.sbb.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.rbac"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.rbac"})
@EnableConfigurationProperties(RbacConfigurationProperties.class)
public class RbacModuleConfiguration {

	@Bean
	@Autowired
	public ResourceService resourceServiceImpl(RbacConfigurationProperties rbacConfigurationProperties) {
		ResourceServiceImpl result = new ResourceServiceImpl();
		result.setResourceFile(rbacConfigurationProperties.getResourceFile());
		return result;
	}

}
