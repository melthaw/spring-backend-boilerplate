package in.clouthink.daas.sbb.security.frontend.spring;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import org.springframework.security.core.userdetails.User;

/**
 *
 */
public class AppUserDetails extends User {

	private AppUser user;

	public AppUserDetails(AppUser user) {
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

	public AppUser getUser() {
		return user;
	}

}
