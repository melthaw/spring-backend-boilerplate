package in.clouthink.daas.sbb.account.service;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;

/**
 */
public interface UserProfileService {

	/**
	 * @param userId
	 * @return
	 */
	User findUserById(String userId);


	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	User changeUserProfile(String userId, ChangeUserProfileRequest request);

	/**
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	User changeUserPassword(String userId, String oldPassword, String newPassword);

	/**
	 * @param id
	 * @param url
	 * @param byWho
	 */
	void updateUserAvatar(String id, String url, User byWho);

}
