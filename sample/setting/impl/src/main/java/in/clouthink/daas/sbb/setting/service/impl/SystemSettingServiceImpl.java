package in.clouthink.daas.sbb.setting.service.impl;

import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;
import in.clouthink.daas.sbb.setting.exception.SystemSettingException;
import in.clouthink.daas.sbb.setting.repository.SystemSettingRepository;
import in.clouthink.daas.sbb.setting.service.SystemSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author dz
 */
@Service
public class SystemSettingServiceImpl implements SystemSettingService {

	@Autowired
	private SystemSettingRepository systemSettingRepository;

	@Override
	public SystemSetting getSystemSetting() {
		return systemSettingRepository.findById(SystemSetting.DEFAULT_ID);
	}

	@Override
	public void updateSystemSetting(SystemSetting systemSetting) {
		if (StringUtils.isEmpty(systemSetting.getName())) {
			throw new SystemSettingException("系统名称不能为空");
		}

		SystemSetting existedSystemSetting = getSystemSetting();
		if (existedSystemSetting == null) {
			existedSystemSetting = new SystemSetting();
			existedSystemSetting.setId(SystemSetting.DEFAULT_ID);
			existedSystemSetting.setCreatedAt(new Date());
		}

		BeanUtils.copyProperties(systemSetting, existedSystemSetting, "id", "createdAt", "modifiedAt");
		existedSystemSetting.setModifiedAt(new Date());
		systemSettingRepository.save(existedSystemSetting);
	}


}
