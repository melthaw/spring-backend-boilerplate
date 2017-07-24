package in.clouthink.daas.sbb.dashboard.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.dashboard.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.dashboard.rest.dto.MenuSummary;
import in.clouthink.daas.sbb.dashboard.rest.dto.SysUserProfile;

import java.util.List;

/**
 *
 */
public interface SysUserProfileRestSupport {

	SysUserProfile getUserProfile(SysUser byWho);

	void updateUserProfile(ChangeMyProfileParameter request, SysUser byWho);

	void changeMyPassword(ChangeMyPasswordRequest request, SysUser byWho);

	List<MenuSummary> getUserGrantedMenus(SysUser user);

}
