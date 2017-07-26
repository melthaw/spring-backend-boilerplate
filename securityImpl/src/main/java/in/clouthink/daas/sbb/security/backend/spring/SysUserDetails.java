package in.clouthink.daas.sbb.security.backend.spring;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import org.springframework.security.core.userdetails.User;

/**
 *
 */
public class SysUserDetails extends User {

	private SysUser user;

	public SysUserDetails(SysUser user) {
		super(user.getUsername(),
			  user.getPassword(),
			  user.isEnabled(),
			  !user.isExpired(),
			  !user.isExpired(),
			  !user.isLocked(),
			  user.getAuthorities());
		this.user = user;
	}

	public String getUserId() {
		return user.getId();
	}

	public SysUser getUser() {
		return user;
	}

}
