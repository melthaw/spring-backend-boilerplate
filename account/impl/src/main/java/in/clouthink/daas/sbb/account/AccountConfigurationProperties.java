package in.clouthink.daas.sbb.account;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "in.clouthink.daas.sbb.account")
public class AccountConfigurationProperties {

	private String passwordSalt = "@account.sbb.daas.clouthink.in";

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

}
