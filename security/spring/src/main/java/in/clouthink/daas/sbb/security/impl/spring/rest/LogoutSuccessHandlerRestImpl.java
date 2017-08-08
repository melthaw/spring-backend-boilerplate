package in.clouthink.daas.sbb.security.impl.spring.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dz
 */
public class LogoutSuccessHandlerRestImpl implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().append("{\"succeed\":true}");
		response.flushBuffer();
	}

}
