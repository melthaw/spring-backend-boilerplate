package in.clouthink.daas.sbb.setting.rest.support.mock;

import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;
import in.clouthink.daas.sbb.setting.rest.support.SystemSettingRestSupport;
import org.springframework.stereotype.Component;

/**
 * SystemSettingRestSupport mocker
 *
 * @author dz
 */
@Component
public class SystemSettingRestSupportMockImpl implements SystemSettingRestSupport {
	@Override
	public SystemSetting getSystemSetting() {
		return null;
	}

	@Override
	public void updateSystemSetting(SystemSetting updateSystemSetting) {

	}
}
