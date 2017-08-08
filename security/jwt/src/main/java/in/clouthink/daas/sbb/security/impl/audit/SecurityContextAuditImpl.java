package in.clouthink.daas.sbb.security.impl.audit;

import in.clouthink.daas.sbb.security.impl.spring.UserDetails;
import in.clouthink.daas.audit.security.SecurityContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 */
public class SecurityContextAuditImpl implements SecurityContext {

	private static final Log logger = LogFactory.getLog(SecurityContextAuditImpl.class);

	@Override
	public String getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
		}

		logger.warn("Current User is not set, authentication: {}");
		return null;
	}

}
