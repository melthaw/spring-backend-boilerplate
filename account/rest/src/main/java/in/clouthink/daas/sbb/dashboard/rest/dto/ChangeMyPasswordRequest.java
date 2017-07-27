package in.clouthink.daas.sbb.dashboard.rest.dto;

import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
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
