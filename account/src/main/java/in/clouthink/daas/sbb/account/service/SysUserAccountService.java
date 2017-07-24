package in.clouthink.daas.sbb.account.service;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveSysUserRequest;
import in.clouthink.daas.sbb.account.domain.request.SysUserQueryRequest;
import org.springframework.data.domain.Page;

/**
 * The system user service
 */
public interface SysUserAccountService {

	/**
	 * @param username unique username
	 * @return
	 */
	SysUser findAccountByUsername(String username);

	/**
	 * @param request
	 * @param sysRoles
	 * @return
	 */
	SysUser createAccount(SaveSysUserRequest request, SysRole... sysRoles);

	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	SysUser updateAccount(String userId, SaveSysUserRequest request);

	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	SysUser changeUserProfile(String userId, ChangeUserProfileRequest request);

	/**
	 * @param userId
	 * @return
	 */
	SysUser enable(String userId);

	/**
	 * @param userId
	 * @return
	 */
	SysUser disable(String userId);

	/**
	 * @param userId
	 * @return
	 */
	SysUser lock(String userId);

	/**
	 * @param userId
	 * @return
	 */
	SysUser unlock(String userId);

	/**
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	SysUser changePassword(String userId, String oldPassword, String newPassword);

	/**
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	SysUser changePassword(String userId, String newPassword);

	/**
	 * @param userId
	 * @return
	 */
	SysUser findById(String userId);

	/**
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);

	/**
	 * @param username
	 */
	void forgetPassword(String username);

	/**
	 * @param userId
	 */
	void deleteUser(String userId, SysUser byWho);

	/**
	 * @param queryParameter
	 * @return
	 */
	Page<SysUser> listUsers(SysUserQueryRequest queryParameter);

	/**
	 * @param role
	 * @param queryParameter
	 * @return
	 */
	Page<SysUser> listUsersByRole(SysRole role, SysUserQueryRequest queryParameter);

	/**
	 * @param queryParameter
	 * @return
	 */
	Page<SysUser> listArchivedUsers(SysUserQueryRequest queryParameter);

	/**
	 * 归档用户,只有管理员和超级管理员能够归档用户
	 *
	 * @param id
	 * @param byWho
	 */
	void archiveAccount(String id, SysUser byWho);

}
