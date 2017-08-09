package in.clouthink.daas.sbb.setting.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.setting.domain.request.SaveSystemSettingRequest;
import in.clouthink.daas.sbb.setting.rest.dto.SystemSettingSummary;
import in.clouthink.daas.sbb.setting.rest.support.SystemSettingRestSupport;
import in.clouthink.daas.sbb.setting.service.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemSettingRestSupportImpl implements SystemSettingRestSupport {

	@Autowired
	private SystemSettingService systemSettingService;

	@Override
	public SystemSettingSummary getSystemSetting() {
		return SystemSettingSummary.from(systemSettingService.getSystemSetting());
	}

	@Override
	public void updateSystemSetting(SaveSystemSettingRequest systemSetting, User byWho) {
		systemSettingService.saveSystemSetting(systemSetting, byWho);
	}

}
