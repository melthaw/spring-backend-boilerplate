package in.clouthink.daas.sbb.rbac.support.matcher;


import in.clouthink.daas.sbb.rbac.model.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

/**
 * The resource matcher based on spring AntPathMatcher.
 */
public class ResourceAntPathMatcher {

	private static final Log logger = LogFactory.getLog(ResourceAntPathMatcher.class);

	private AntPathMatcher matcher = new AntPathMatcher();

	public boolean matched(Resource resource, String requestUrl) {
		if (resource.getPatterns() == null) {
			return false;
		}
		if (StringUtils.isEmpty(requestUrl)) {
			logger.warn("The incoming expression is empty");
			return false;
		}
		for (String pattern : resource.getPatterns()) {
			if (StringUtils.isEmpty(pattern)) {
				continue;
			}
			if (matcher.match(pattern, requestUrl)) {
				logger.info(String.format("The resource pattern '%s' is matched on the incoming expression '%s'",
										  pattern,
										  requestUrl));
				return true;
			}
		}
		return false;
	}

}
