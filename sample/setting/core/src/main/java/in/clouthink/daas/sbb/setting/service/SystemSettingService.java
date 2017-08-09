package in.clouthink.daas.sbb.setting.service;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;
import in.clouthink.daas.sbb.setting.domain.request.SaveSystemSettingRequest;

/**
 * @author dz
 */
public interface SystemSettingService {

	SystemSetting getSystemSetting();

	void saveSystemSetting(SaveSystemSettingRequest systemSetting, User byWho);

}
