package in.clouthink.daas.sbb.security.impl.auth;

import in.clouthink.daas.sbb.audit.domain.model.AuthEvent;
import in.clouthink.daas.sbb.security.impl.spring.UserDetails;
import in.clouthink.daas.sbb.shared.util.UserAgentUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author dz
 */
public class AuthEventHelper {

	public static AuthEvent buildFailedAuthEvent(HttpServletRequest request, AuthenticationException exception) {
		AuthEvent result = new AuthEvent();
		result.setRealm("backend");
		String userAgent = request.getHeader("User-Agent");
		result.setUserAgent(userAgent);
		result.setOsFamily(UserAgentUtils.getOsFamily(userAgent));
		result.setBrowser(UserAgentUtils.getBrowserFamily(userAgent));
		result.setLoginAt(new Date());
		result.setIpAddress(request.getRemoteAddr());
		result.setFailureReason(exception + "");
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
																  .getAuthentication()
																  .getPrincipal();
			result.setUserId(user.getUserId());
			result.setUsername(user.getUsername());
		}
		catch (Exception e) {
			result.setUsername(request.getParameter("username"));
		}
		result.setSucceed(false);
		// X-Forwarded-For
		return result;
	}

	public static AuthEvent buildSucceedAuthEvent(HttpServletRequest request) {
		AuthEvent result = new AuthEvent();
		result.setRealm("backend");
		String userAgent = request.getHeader("User-Agent");
		result.setUserAgent(userAgent);
		result.setOsFamily(UserAgentUtils.getOsFamily(userAgent));
		result.setBrowser(UserAgentUtils.getBrowserFamily(userAgent));
		result.setLoginAt(new Date());
		result.setIpAddress(request.getRemoteAddr());
		try {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
																  .getAuthentication()
																  .getPrincipal();
			result.setUserId(user.getUserId());
			result.setUsername(user.getUsername());
		}
		catch (Exception e) {
			result.setUsername(request.getParameter("username"));
		}
		result.setSucceed(true);
		// X-Forwarded-For
		return result;
	}

}
