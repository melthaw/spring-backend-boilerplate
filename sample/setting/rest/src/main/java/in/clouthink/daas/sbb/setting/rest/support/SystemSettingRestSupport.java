package in.clouthink.daas.sbb.setting.rest.support;


import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;

/**
 * @author dz
 */
public interface SystemSettingRestSupport {

	SystemSetting getSystemSetting();

	void updateSystemSetting(SystemSetting updateSystemSetting);

}
