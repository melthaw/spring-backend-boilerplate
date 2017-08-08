package in.clouthink.daas.sbb.security.impl.spring.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dz
 */
public class AuthenticationEntryPointRestImpl implements AuthenticationEntryPoint {

	private static final Log logger = LogFactory.getLog(AuthenticationEntryPointRestImpl.class);

	@Override
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		logger.error("The ajax request access is denied.", authException);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().append(String.format("{\"succeed\":false,\"message\":\"%s\"}", authException.getMessage()));
		response.flushBuffer();
	}

}
