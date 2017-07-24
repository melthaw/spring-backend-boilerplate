package in.clouthink.daas.sbb.dashboard;

import in.clouthink.daas.sbb.account.domain.model.Gender;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.service.SysUserAccountService;
import in.clouthink.daas.sbb.dashboard.rest.dto.SaveSysUserParameter;
import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;
import in.clouthink.daas.sbb.setting.service.SystemSettingService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashboardStartupCallback implements InitializingBean {

	private static final Log logger = LogFactory.getLog(DashboardStartupCallback.class);

	@Autowired
	private SysUserAccountService accountService;

	@Autowired
	private SystemSettingService systemSettingService;

	@Autowired
	private DashboardConfigurationProperties dashboardConfigurationProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		initializeApplication();
	}

	private void initializeApplication() {
		// initialize System Administrator
		DashboardConfigurationProperties.Administrator administrator = dashboardConfigurationProperties.getAdministrator();
		SysUser adminUser = accountService.findByUsername(administrator.getUsername());
		if (adminUser != null) {
			logger.debug("The admin user is created before, we will skip it");
		}
		else {
			SaveSysUserParameter saveSysUserParameter = new SaveSysUserParameter();
			saveSysUserParameter.setEmail(administrator.getEmail());
			saveSysUserParameter.setUsername(administrator.getUsername());
			saveSysUserParameter.setPassword(administrator.getPassword());
			saveSysUserParameter.setCellphone(administrator.getCellphone());
			saveSysUserParameter.setGender(Gender.MALE);
			accountService.createAccount(saveSysUserParameter, SysRole.ROLE_USER, SysRole.ROLE_ADMIN);
		}

		SystemSetting systemSetting = systemSettingService.getSystemSetting();
		if (systemSetting != null) {
			logger.debug("The system setting is initialized before, we will skip it");
		}
		else {
			systemSetting = new SystemSetting();
			systemSetting.setName("APPT");
			systemSettingService.updateSystemSetting(systemSetting);
		}

	}

}
