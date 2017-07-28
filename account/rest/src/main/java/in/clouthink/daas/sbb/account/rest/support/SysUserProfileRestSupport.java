package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.account.rest.dto.SysUserProfile;

/**
 *
 */
public interface SysUserProfileRestSupport {

	SysUserProfile getUserProfile(SysUser byWho);

	void updateUserProfile(ChangeMyProfileParameter request, SysUser byWho);

	void changeMyPassword(ChangeMyPasswordRequest request, SysUser byWho);

}
