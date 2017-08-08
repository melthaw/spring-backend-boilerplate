package in.clouthink.daas.sbb.security.impl.spring.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dz
 */
public class AuthenticationEntryPointMvcImpl extends LoginUrlAuthenticationEntryPoint {

	private static final Log logger = LogFactory.getLog(AuthenticationEntryPointMvcImpl.class);

	public AuthenticationEntryPointMvcImpl(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		if (authException != null) {
			String accept = request.getHeader("Accept");
			if (accept != null && accept.contains("application/json")) {
				logger.warn("The ajax request is not authenticated.");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.flushBuffer();
				return;
			}
		}

		super.commence(request, response, authException);
	}


}
