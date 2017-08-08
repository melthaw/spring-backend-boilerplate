package in.clouthink.daas.sbb.security.impl.spring.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dz
 */
public class AccessDeniedHandlerMvcImpl extends org.springframework.security.web.access.AccessDeniedHandlerImpl {

	private static final Log logger = LogFactory.getLog(AccessDeniedHandlerMvcImpl.class);

	@Override
	public void handle(HttpServletRequest request,
					   HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		logger.error(accessDeniedException, accessDeniedException);
		if (!response.isCommitted()) {
			String accept = request.getHeader("Accept");
			if (accept != null && accept.contains("application/json")) {
				logger.warn("The ajax request access is denied.");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.flushBuffer();
			}
			else {
				super.handle(request, response, accessDeniedException);
			}
		}
	}

}
