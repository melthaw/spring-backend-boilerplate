package in.clouthink.daas.sbb.security.impl.spring.rest;

import in.clouthink.daas.sbb.audit.service.AuthEventService;
import in.clouthink.daas.sbb.security.impl.auth.AuthEventHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dz
 */
public class AuthenticationFailureHandlerRestImpl implements AuthenticationFailureHandler {

	private static final Log logger = LogFactory.getLog(AuthenticationFailureHandlerRestImpl.class);

	@Autowired
	private AuthEventService userAuditService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
										HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		logger.error("The ajax request is not authenticated.", exception);
		try {
			userAuditService.saveUserAuthEvent(AuthEventHelper.buildFailedAuthEvent(request, exception));
		}
		catch (Throwable e) {
			//do nothing
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().append(String.format("{\"succeed\":false,\"message\":\"%s\"}", exception.getMessage()));
		response.flushBuffer();
	}

}
