package in.clouthink.daas.sbb.account;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.account.password")
public class AccountPasswordConfigurationProperties {

	private String salt = "@account.sbb.daas.clouthink.in";

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
