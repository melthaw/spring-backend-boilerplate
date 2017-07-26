package in.clouthink.daas.sbb.setting.service;

import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;

/**
 * @author dz
 */
public interface SystemSettingService {

	SystemSetting getSystemSetting();

	void updateSystemSetting(SystemSetting systemSetting);

}
