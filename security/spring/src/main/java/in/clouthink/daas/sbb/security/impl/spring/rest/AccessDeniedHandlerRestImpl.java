package in.clouthink.daas.sbb.security.impl.spring.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dz
 */
public class AccessDeniedHandlerRestImpl extends AccessDeniedHandlerImpl {

	private static final Log logger = LogFactory.getLog(AccessDeniedHandlerRestImpl.class);

	@Override
	public void handle(HttpServletRequest request,
					   HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		logger.error("The ajax request access is denied.", accessDeniedException);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter()
				.append(String.format("{\"succeed\":false,\"message\":\"%s\"}", accessDeniedException.getMessage()));
		response.flushBuffer();
	}

}
