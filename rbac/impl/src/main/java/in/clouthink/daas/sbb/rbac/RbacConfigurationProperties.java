package in.clouthink.daas.sbb.rbac;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "in.clouthink.daas.appt.rbac")
public class RbacConfigurationProperties {

	private String resourceFile;

	public String getResourceFile() {
		return resourceFile;
	}

	public void setResourceFile(String resourceFile) {
		this.resourceFile = resourceFile;
	}

}
