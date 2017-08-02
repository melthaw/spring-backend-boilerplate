package in.clouthink.daas.sbb.rbac.support.matcher;

import in.clouthink.daas.sbb.rbac.model.ResourceMatcher;

/**
 * The ResourceMatcher utilities.
 *
 * @author dz
 */
public abstract class ResourceMatchers {

	private static ResourceAntPathMatcher resourceAntPathMatcher = new ResourceAntPathMatcher();

	public final static ResourceMatcher matchAntPath(String url) {
		return (resource) -> resourceAntPathMatcher.matched(resource, url);
	}

	public final static ResourceMatcher matchType(String type) {
		return (resource) -> resource.getType().equals(type);
	}

	public final static ResourceMatcher matchCode(String code) {
		return (resource) -> resource.getCode().equals(code);
	}

}
