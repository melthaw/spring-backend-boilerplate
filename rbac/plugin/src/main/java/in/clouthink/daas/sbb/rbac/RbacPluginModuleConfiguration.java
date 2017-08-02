package in.clouthink.daas.sbb.rbac;

import in.clouthink.daas.sbb.rbac.impl.service.impl.ResourceServiceImpl;
import in.clouthink.daas.sbb.rbac.service.ResourceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan({"in.clouthink.daas.sbb.rbac.service", "in.clouthink.daas.sbb.rbac.repository"})
@EnableMongoRepositories({"in.clouthink.daas.sbb.rbac.repository"})
@EnableConfigurationProperties(RbacConfigurationProperties.class)
public class RbacPluginModuleConfiguration {

	@Bean
	@Autowired
	public ResourceRegistry resourceServiceImpl(RbacConfigurationProperties rbacConfigurationProperties) {
		ResourceServiceImpl result = new ResourceServiceImpl();
		//TODO change to registered 
		//		result.setResourceFile(rbacConfigurationProperties.getResourceFile());
		return result;
	}

}
