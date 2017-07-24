package in.clouthink.daas.sbb.account.service;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.domain.request.AppUserQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveAppUserRequest;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import org.springframework.data.domain.Page;

/**
 * The application user service
 *
 * @author dz
 */
public interface AppUserAccountService {

	/**
	 * @param cellphone as unique username
	 * @return
	 */
	AppUser findAccountByCellphone(String cellphone);

	/**
	 * @param username unique username
	 * @return
	 */
	AppUser findAccountByUsername(String username);

	/**
	 * @param saveAppUserRequest
	 * @param appRoles
	 * @return
	 */
	AppUser createAccount(SaveAppUserRequest saveAppUserRequest, AppRole... appRoles);

	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	AppUser updateAccount(String userId, SaveAppUserRequest request);

	/**
	 * @param userId
	 * @param request
	 * @return
	 */
	AppUser changeUserProfile(String userId, ChangeUserProfileRequest request);

	/**
	 * @param userId
	 * @param avatarId
	 * @return
	 */
	AppUser changeUserAvatar(String userId, String avatarId, String avatarUrl);

	/**
	 * @param userId
	 * @return
	 */
	AppUser enable(String userId);

	/**
	 * @param userId
	 * @return
	 */
	AppUser disable(String userId);

	/**
	 * @param userId
	 * @return
	 */
	AppUser lock(String userId);

	/**
	 * @param userId
	 * @return
	 */
	AppUser unlock(String userId);

	/**
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	AppUser changePassword(String userId, String oldPassword, String newPassword);

	/**
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	AppUser changePassword(String userId, String newPassword);

	/**
	 * @param userId
	 * @return
	 */
	AppUser findById(String userId);

	/**
	 * @param username
	 * @return
	 */
	AppUser findByUsername(String username);

	/**
	 * @param queryRequest
	 * @return
	 */
	Page<AppUser> listUsers(AppUserQueryRequest queryRequest);

	/**
	 * 查询指定角色的用户
	 *
	 * @param appRole
	 * @param queryRequest
	 * @return
	 */
	Page<AppUser> findUsersByRole(AppRole appRole, PageQueryRequest queryRequest);

}
