package in.clouthink.daas.sbb.openapi;

import in.clouthink.daas.sbb.account.AccountModuleConfiguration;
import in.clouthink.daas.sbb.audit.AuditModuleConfiguration;
import in.clouthink.daas.sbb.event.MockEventModuleConfiguration;
import in.clouthink.daas.sbb.setting.repository.RepositoryModuleConfiguration;
import in.clouthink.daas.sbb.security.frontend.SecurityFrontendModuleConfiguration;
import in.clouthink.daas.sbb.security.frontend.audit.AuditEventPersisterImpl;
import in.clouthink.daas.sbb.security.frontend.audit.SecurityContextAuditImpl;
import in.clouthink.daas.sbb.setting.service.ServiceModuleConfiguration;
import in.clouthink.daas.sbb.storage.GridfsModuleConfiguration;
import in.clouthink.daas.audit.annotation.EnableAudit;
import in.clouthink.daas.audit.configure.AuditConfigurer;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ComponentScan({"in.clouthink.daas.sbb.openapi"})
@Import({OpenApiSecurityConfigurer.class, OpenApiWebMvcConfigurer.class})
@EnableAsync
@EnableAudit
public class OpenApiApplication extends SpringBootServletInitializer {

	@Bean
	public AuditEventPersister auditEventPersisterImpl() {
		return new AuditEventPersisterImpl();
	}

	@Bean
	public AuditConfigurer auditConfigurer() {
		return result -> {
			result.setSecurityContext(new SecurityContextAuditImpl());
			result.setAuditEventPersister(auditEventPersisterImpl());
			result.setErrorDetailRequired(true);
		};
	}

	@Override
	protected WebApplicationContext run(SpringApplication application) {
		application.getSources().remove(ErrorPageFilter.class);
		return super.run(application);
	}

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{AccountModuleConfiguration.class,
										   RepositoryModuleConfiguration.class,
										   ServiceModuleConfiguration.class,
										   MockEventModuleConfiguration.class,
										   GridfsModuleConfiguration.class,
										   OpenApiModuleConfiguration.class,
										   AuditModuleConfiguration.class,
										   SecurityFrontendModuleConfiguration.class,
										   OpenApiApplication.class}, args);
	}

}
