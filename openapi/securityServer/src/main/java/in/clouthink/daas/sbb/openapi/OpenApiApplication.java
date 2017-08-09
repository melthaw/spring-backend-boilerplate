package in.clouthink.daas.sbb.openapi;

import in.clouthink.daas.audit.annotation.EnableAudit;
import in.clouthink.daas.audit.configure.AuditConfigurer;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import in.clouthink.daas.sbb.account.AccountRestModuleConfiguration;
import in.clouthink.daas.sbb.attachment.AttachmentRestModuleConfiguration;
import in.clouthink.daas.sbb.audit.AuditRestModuleConfiguration;
import in.clouthink.daas.sbb.menu.MenuRestModuleConfiguration;
import in.clouthink.daas.sbb.news.NewsRestModuleConfiguration;
import in.clouthink.daas.sbb.rbac.RbacRestModuleConfiguration;
import in.clouthink.daas.sbb.security.impl.audit.AuditEventPersisterImpl;
import in.clouthink.daas.sbb.security.impl.audit.SecurityContextAuditImpl;
import in.clouthink.daas.sbb.setting.SystemSettingRestModuleConfiguration;
import in.clouthink.daas.sbb.sms.DummySmsRestModuleConfiguration;
import in.clouthink.daas.sbb.storage.GridfsModuleConfiguration;
import in.clouthink.daas.sbb.storage.StorageRestModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
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
		SpringApplication.run(new Object[]{AccountRestModuleConfiguration.class,
										   AuditRestModuleConfiguration.class,
										   MenuRestModuleConfiguration.class,
										   RbacRestModuleConfiguration.class,
										   GridfsModuleConfiguration.class,
										   StorageRestModuleConfiguration.class,
										   DummySmsRestModuleConfiguration.class,
										   NewsRestModuleConfiguration.class,
										   AttachmentRestModuleConfiguration.class,
										   SystemSettingRestModuleConfiguration.class,
										   OpenApiApplication.class}, args);
	}

}
