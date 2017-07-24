package in.clouthink.daas.sbb.dashboard.rest.dto;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class SysUserDetail extends SysUserSummary {

	public static SysUserDetail from(SysUser user) {
		if (user == null) {
			return null;
		}
		SysUserDetail result = new SysUserDetail();
		convert(user, result);
		return result;
	}

	//TODO add roles

}
