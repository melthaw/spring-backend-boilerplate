package in.clouthink.daas.sbb.account.service;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;

/**
 */
public interface SysUserProfileService {

	/**
	 * @param userId
	 * @return
	 */
	SysUser findUserById(String userId);


	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	SysUser changeUserProfile(String userId, ChangeUserProfileRequest request);

	/**
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	SysUser changeUserPassword(String userId, String oldPassword, String newPassword);

}
