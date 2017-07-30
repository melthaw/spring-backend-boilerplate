package in.clouthink.daas.sbb.security.impl.spring;

import in.clouthink.daas.sbb.account.domain.model.User;

/**
 *
 */
public class SysUserDetails extends org.springframework.security.core.userdetails.User {

	private User user;

	public SysUserDetails(User user) {
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

	public User getUser() {
		return user;
	}

}
