package in.clouthink.daas.sbb.rbac;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.rbac")
public class RbacServiceConfigurationProperties {

	private String resourceFile;

	public String getResourceFile() {
		return resourceFile;
	}

	public void setResourceFile(String resourceFile) {
		this.resourceFile = resourceFile;
	}

}
