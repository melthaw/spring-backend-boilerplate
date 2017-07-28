package in.clouthink.daas.sbb.account.rest.dto;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class SysUserProfile extends SysUserSummary {

	public static SysUserProfile from(SysUser user) {
		if (user == null) {
			return null;
		}
		SysUserProfile result = new SysUserProfile();
		convert(user, result);
		return result;
	}

	//TODO add roles

}
