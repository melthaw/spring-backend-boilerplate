package in.clouthink.daas.sbb.rbac.impl.spring.security;

import in.clouthink.daas.sbb.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * How to configure
 * <p>
 * http.authorizeRequests()
 * .accessDecisionManager(accessDecisionManager())
 * .antMatchers("put the wanted url here")
 * .access("passRbacCheck")
 * <p>
 */
public class RbacWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

	@Autowired
	private PermissionService rbacService;

	@Override
	public SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
																	 FilterInvocation fi) {
		RbacWebSecurityExpressionRoot root = new RbacWebSecurityExpressionRoot(authentication, fi, rbacService);
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setRoleHierarchy(getRoleHierarchy());
		return root;
	}

}
