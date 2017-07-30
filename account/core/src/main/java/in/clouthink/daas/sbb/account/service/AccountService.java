package in.clouthink.daas.sbb.account.service;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveUserRequest;
import in.clouthink.daas.sbb.account.domain.request.UserQueryRequest;
import org.springframework.data.domain.Page;

/**
 * The system user service
 */
public interface AccountService {

	/**
	 * @param username unique username
	 * @return
	 */
	User findAccountByUsername(String username);

	/**
	 * @param request
	 * @param sysRoles
	 * @return
	 */
	User createAccount(SaveUserRequest request, SysRole... sysRoles);

	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	User updateAccount(String userId, SaveUserRequest request);

	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	User changeUserProfile(String userId, ChangeUserProfileRequest request);

	/**
	 * @param userId
	 * @param avatarId
	 * @return
	 */
	User changeUserAvatar(String userId, String avatarId, String avatarUrl);

	/**
	 * @param userId
	 * @return
	 */
	User enable(String userId);

	/**
	 * @param userId
	 * @return
	 */
	User disable(String userId);

	/**
	 * @param userId
	 * @return
	 */
	User lock(String userId);

	/**
	 * @param userId
	 * @return
	 */
	User unlock(String userId);

	/**
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	User changePassword(String userId, String oldPassword, String newPassword);

	/**
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	User changePassword(String userId, String newPassword);

	/**
	 * @param userId
	 * @return
	 */
	User findById(String userId);

	/**
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * @param username
	 */
	void forgetPassword(String username);

	/**
	 * @param userId
	 */
	void deleteUser(String userId, User byWho);

	/**
	 * @param queryParameter
	 * @return
	 */
	Page<User> listUsers(UserQueryRequest queryParameter);

	/**
	 * @param role
	 * @param queryParameter
	 * @return
	 */
	Page<User> listUsersByRole(SysRole role, UserQueryRequest queryParameter);

	/**
	 * @param queryParameter
	 * @return
	 */
	Page<User> listArchivedUsers(UserQueryRequest queryParameter);

	/**
	 * 归档用户,只有管理员和超级管理员能够归档用户
	 *
	 * @param id
	 * @param byWho
	 */
	void archiveAccount(String id, User byWho);

}
