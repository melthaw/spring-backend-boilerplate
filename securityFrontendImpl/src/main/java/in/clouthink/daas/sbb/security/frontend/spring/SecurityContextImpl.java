package in.clouthink.daas.sbb.security.frontend.spring;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.exception.AppUserRequiredException;
import in.clouthink.daas.sbb.security.SecurityContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 */
public class SecurityContextImpl implements SecurityContext<AppUser> {

	private static final Log logger = LogFactory.getLog(SecurityContextImpl.class);

	@Override
	public AppUser currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof AppUserDetails) {
				return ((AppUserDetails) principal).getUser();
			}
		}

		logger.warn("Current User is not set, authentication: {}");
		return null;
	}

	@Override
	public AppUser requireUser() {
		AppUser user = currentUser();
		if (user == null) {
			throw new AppUserRequiredException();
		}
		return user;
	}

}
