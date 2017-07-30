package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("修改当前用户密码")
public class ChangeMyPasswordRequest {

	private String oldPassword;

	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
