package in.clouthink.daas.sbb.dashboard;

import in.clouthink.daas.sbb.account.AccountModuleConfiguration;
import in.clouthink.daas.sbb.attachment.AttachmentModuleConfiguration;
import in.clouthink.daas.sbb.audit.AuditModuleConfiguration;
import in.clouthink.daas.sbb.event.EventModuleConfiguration;
import in.clouthink.daas.sbb.rbac.RbacModuleConfiguration;
import in.clouthink.daas.sbb.setting.repository.RepositoryModuleConfiguration;
import in.clouthink.daas.sbb.security.impl.SecurityBackendModuleConfiguration;
import in.clouthink.daas.sbb.security.impl.audit.AuditEventPersisterImpl;
import in.clouthink.daas.sbb.security.impl.audit.SecurityContextImpl;
import in.clouthink.daas.sbb.setting.service.ServiceModuleConfiguration;
import in.clouthink.daas.sbb.storage.GridfsModuleConfiguration;
import in.clouthink.daas.audit.annotation.EnableAudit;
import in.clouthink.daas.audit.configure.AuditConfigurer;
import in.clouthink.daas.audit.spi.AuditEventPersister;
import in.clouthink.daas.plugin.annotation.EnablePlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ComponentScan({"in.clouthink.daas.appt.dashboard"})
@Import({DashboardSecurityConfigurer.class, DashboardWebMvcConfigurer.class})
@EnableConfigurationProperties(DashboardConfigurationProperties.class)
@EnableMongoRepositories({"in.clouthink.daas.appt.security.backend.audit.repository",
						  "in.clouthink.daas.appt.security.backend.auth.repository"})
@EnableScheduling
@EnableAsync
@EnableAudit
@EnablePlugin
public class DashboardApplication extends SpringBootServletInitializer implements SchedulingConfigurer {
	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(20);
		scheduler.setThreadNamePrefix("task-");
		scheduler.setAwaitTerminationSeconds(60);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		return scheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(taskScheduler());
	}

	@Bean
	public AuditEventPersister auditEventPersisterImpl() {
		return new AuditEventPersisterImpl();
	}

	@Bean
	public AuditConfigurer auditConfigurer() {
		return result -> {
			result.setSecurityContext(new SecurityContextImpl());
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
										   EventModuleConfiguration.class,
										   RbacModuleConfiguration.class,
										   AuditModuleConfiguration.class,
										   GridfsModuleConfiguration.class,
										   AttachmentModuleConfiguration.class,
										   DashApiModuleConfiguration.class,
										   SecurityBackendModuleConfiguration.class,
										   DashboardApplication.class}, args);
	}

}
