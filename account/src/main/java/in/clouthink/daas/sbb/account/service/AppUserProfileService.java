package in.clouthink.daas.sbb.account.service;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;

/**
 */
public interface AppUserProfileService {

	/**
	 * @param userId
	 * @return
	 */
	AppUser findUserById(String userId);

	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	AppUser changeUserProfile(String userId, ChangeUserProfileRequest request);

	/**
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	AppUser changeUserPassword(String userId, String oldPassword, String newPassword);

	/**
	 * @param id
	 * @param url
	 * @param byWho
	 */
	void updateUserAvatar(String id, String url, AppUser byWho);

}
