package in.clouthink.daas.sbb.event.sms.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dz
 */
@ConfigurationProperties(prefix = "in.clouthink.daas.appt.sms.aliyun")
public class AdvancedAliyunOptions extends AliyunOptions {

	private String templateId;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
