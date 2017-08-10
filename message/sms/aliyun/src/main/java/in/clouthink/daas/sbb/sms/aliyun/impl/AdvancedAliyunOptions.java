package in.clouthink.daas.sbb.sms.aliyun.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dz
 */
@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.sms.aliyun")
public class AdvancedAliyunOptions extends AliyunOptions {

	private String templateId;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
