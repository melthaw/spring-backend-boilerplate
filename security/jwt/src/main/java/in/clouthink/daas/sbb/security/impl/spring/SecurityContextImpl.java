package in.clouthink.daas.sbb.security.impl.spring;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.exception.UserRequiredException;
import in.clouthink.daas.sbb.security.SecurityContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 */
public class SecurityContextImpl implements SecurityContext {

	private static final Log logger = LogFactory.getLog(SecurityContextImpl.class);

	@Override
	public User currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUser();
			}
		}

		logger.warn("Current User is not set, authentication: {}");
		return null;
	}

	@Override
	public User requireUser() {
		User user = currentUser();
		if (user == null) {
			throw new UserRequiredException();
		}
		return user;
	}

}
