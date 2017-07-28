package in.clouthink.daas.sbb.dashboard.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.dashboard.rest.dto.SysUserProfile;

/**
 *
 */
public interface SysUserProfileRestSupport {

	SysUserProfile getUserProfile(SysUser byWho);

	void updateUserProfile(ChangeMyProfileParameter request, SysUser byWho);

	void changeMyPassword(ChangeMyPasswordRequest request, SysUser byWho);

}
