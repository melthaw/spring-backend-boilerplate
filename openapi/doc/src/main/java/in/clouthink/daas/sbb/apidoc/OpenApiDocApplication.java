package in.clouthink.daas.sbb.apidoc;

import in.clouthink.daas.sbb.account.AccountMockModuleConfiguration;
import in.clouthink.daas.sbb.attachment.AttachmentMockModuleConfiguration;
import in.clouthink.daas.sbb.audit.AuditMockModuleConfiguration;
import in.clouthink.daas.sbb.menu.MenuMockModuleConfiguration;
import in.clouthink.daas.sbb.news.NewsMockModuleConfiguration;
import in.clouthink.daas.sbb.rbac.RbacMockModuleConfiguration;
import in.clouthink.daas.sbb.setting.SystemSettingMockModuleConfiguration;
import in.clouthink.daas.sbb.sms.history.SmsHistoryMockModuleConfiguration;
import in.clouthink.daas.sbb.storage.StorageMockModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@Import({SpringfoxConfiguration.class})
public class OpenApiDocApplication extends SpringBootServletInitializer {

	@Override
	protected WebApplicationContext run(SpringApplication application) {
		application.getSources().remove(ErrorPageFilter.class);
		return super.run(application);
	}

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{AccountMockModuleConfiguration.class,
										   AuditMockModuleConfiguration.class,
										   MenuMockModuleConfiguration.class,
										   RbacMockModuleConfiguration.class,
										   StorageMockModuleConfiguration.class,
										   SmsHistoryMockModuleConfiguration.class,
										   NewsMockModuleConfiguration.class,
										   AttachmentMockModuleConfiguration.class,
										   SystemSettingMockModuleConfiguration.class,
										   OpenApiDocApplication.class}, args);
	}

}
