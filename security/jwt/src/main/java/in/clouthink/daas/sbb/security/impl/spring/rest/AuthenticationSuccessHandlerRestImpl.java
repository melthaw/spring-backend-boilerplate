package in.clouthink.daas.sbb.security.impl.spring.rest;

import in.clouthink.daas.sbb.audit.service.AuthEventService;
import in.clouthink.daas.sbb.security.impl.auth.AuthEventHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dz
 */
public class AuthenticationSuccessHandlerRestImpl implements AuthenticationSuccessHandler {

	@Autowired
	private AuthEventService userAuditService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		try {
			userAuditService.saveUserAuthEvent(AuthEventHelper.buildSucceedAuthEvent(request));
		}
		catch (Throwable e) {
			//do nothing
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().append("{\"succeed\":true}");
		response.flushBuffer();
	}

}
