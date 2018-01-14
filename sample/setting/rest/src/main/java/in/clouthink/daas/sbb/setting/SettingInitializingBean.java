package in.clouthink.daas.sbb.setting;

import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;
import in.clouthink.daas.sbb.setting.service.SystemSettingService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dz
 */
public class SettingInitializingBean implements InitializingBean {

    private static final Log logger = LogFactory.getLog(SettingInitializingBean.class);

    @Autowired
    private SystemSettingService systemSettingService;

    @Autowired
    private SettingConfigurationProperties settingConfigurationProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        tryCreateSystemSetting();
    }

    private void tryCreateSystemSetting() {
        if (StringUtils.isEmpty(settingConfigurationProperties.getSystem().getName())) {
            logger.debug("The system setting is not pre-configured, we will skip it");
            return;
        }

        SystemSetting systemSetting = systemSettingService.getSystemSetting();

        if (systemSetting != null) {
            logger.debug("The system setting is initialized before, we will skip it");
            return;
        }

        systemSettingService.saveSystemSetting(settingConfigurationProperties.getSystem(), null);
    }

}
