package in.clouthink.daas.sbb.account.dto;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel("用户明细")
public class AppUserDetail extends AppUserSummary {

	static void convert(AppUser user, AppUserDetail result) {
		AppUserSummary.convert(user, result);
		if (user.getTags() != null) {
			result.setTags(user.getTags().toArray(new String[]{}));
		}
	}

	public static AppUserDetail from(AppUser user) {
		if (user == null) {
			return null;
		}
		AppUserDetail result = new AppUserDetail();
		convert(user, result);
		return result;
	}

	private boolean followed;

	private String email;

	private String signature;

	private boolean socialGroupManager;

	private String[] tags;


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

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

}
