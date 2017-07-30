package in.clouthink.daas.sbb.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.storage.local")
public class LocalfsConfigureProperties {

	public static final String BY_MONTH = "MONTH";

	public static final String BY_DAY = "DAY";

	private String path = "/var/sbb/storage";

	private String strategy = BY_DAY;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
}
