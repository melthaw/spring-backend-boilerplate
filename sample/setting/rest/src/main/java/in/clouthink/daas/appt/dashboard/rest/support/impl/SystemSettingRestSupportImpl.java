package in.clouthink.daas.appt.dashboard.rest.support.impl;

import in.clouthink.daas.appt.dashboard.rest.support.SystemSettingRestSupport;
import in.clouthink.daas.appt.setting.domain.model.SystemSetting;
import in.clouthink.daas.appt.setting.service.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemSettingRestSupportImpl implements SystemSettingRestSupport {

	@Autowired
	private SystemSettingService systemSettingService;

	@Override
	public SystemSetting getSystemSetting() {
		return systemSettingService.getSystemSetting();
	}

	@Override
	public void updateSystemSetting(SystemSetting systemSetting) {
		systemSettingService.updateSystemSetting(systemSetting);
	}

}
