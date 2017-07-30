package in.clouthink.daas.sbb.apidoc;

import in.clouthink.daas.sbb.openapi.OpenApiModuleConfiguration;
import in.clouthink.daas.sbb.news.repository.RepositoryModuleConfiguration;
import in.clouthink.daas.sbb.news.service.ServiceModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class OpenApiDocApplication extends SpringBootServletInitializer {

	@Override
	protected WebApplicationContext run(SpringApplication application) {
		application.getSources().remove(ErrorPageFilter.class);
		return super.run(application);
	}

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{
										   OpenApiDocApplication.class,
										   SpringfoxConfiguration.class}, args);
	}

}
