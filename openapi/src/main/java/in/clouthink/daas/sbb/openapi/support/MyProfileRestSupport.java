package in.clouthink.daas.sbb.openapi.support;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.account.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.dto.ChangeMyProfileParameter;

/**
 *
 */
public interface MyProfileRestSupport {

	/**
	 * 查询用户的个人资料
	 *
	 * @param byWho
	 * @return
	 */
	AppUserDetail getUserProfile(AppUser byWho);

	/**
	 * 更新用户的个人资料
	 *
	 * @param request
	 * @param byWho
	 */
	void updateUserProfile(ChangeMyProfileParameter request, AppUser byWho);

	/**
	 * 更新用户头像
	 *
	 * @param imageId 如果为空,则清空用户头像
	 * @param byWho
	 */
	void updateUserAvatar(String imageId, AppUser byWho);

	/**
	 * 修改用户密码
	 *
	 * @param request 新密码和旧密码不能相同
	 * @param byWho
	 */
	void changeMyPassword(ChangeMyPasswordRequest request, AppUser byWho);

}
