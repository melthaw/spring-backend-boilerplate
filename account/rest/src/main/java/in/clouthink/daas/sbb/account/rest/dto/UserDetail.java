package in.clouthink.daas.sbb.account.rest.dto;

import in.clouthink.daas.sbb.account.domain.model.User;
import io.swagger.annotations.ApiModel;

@ApiModel("用户明细")
public class UserDetail extends UserSummary {

	static void convert(User user, UserDetail result) {
		UserSummary.convert(user, result);
	}

	public static UserDetail from(User user) {
		if (user == null) {
			return null;
		}
		UserDetail result = new UserDetail();
		convert(user, result);
		return result;
	}

	private boolean followed;

	private String email;

	private String signature;

	private boolean socialGroupManager;

	public boolean isFollowed() {
		return followed;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isSocialGroupManager() {
		return socialGroupManager;
	}

	public void setSocialGroupManager(boolean socialGroupManager) {
		this.socialGroupManager = socialGroupManager;
	}

}
