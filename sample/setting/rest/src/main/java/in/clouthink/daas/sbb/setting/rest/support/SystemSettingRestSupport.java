package in.clouthink.daas.sbb.setting.rest.support;


import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.setting.domain.request.SaveSystemSettingRequest;
import in.clouthink.daas.sbb.setting.rest.dto.SystemSettingSummary;

/**
 * @author dz
 */
public interface SystemSettingRestSupport {

	SystemSettingSummary getSystemSetting();

	void updateSystemSetting(SaveSystemSettingRequest updateSystemSetting, User byWho);

}
