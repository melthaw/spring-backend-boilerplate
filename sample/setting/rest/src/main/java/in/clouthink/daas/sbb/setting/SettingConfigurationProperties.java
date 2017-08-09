package in.clouthink.daas.sbb.setting;

import in.clouthink.daas.sbb.setting.rest.dto.SaveSystemSettingParameter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dz
 */
@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.setting")
public class SettingConfigurationProperties {

	private SaveSystemSettingParameter system = new SaveSystemSettingParameter();

	public SaveSystemSettingParameter getSystem() {
		return system;
	}

	public void setSystem(SaveSystemSettingParameter system) {
		this.system = system;
	}

}
