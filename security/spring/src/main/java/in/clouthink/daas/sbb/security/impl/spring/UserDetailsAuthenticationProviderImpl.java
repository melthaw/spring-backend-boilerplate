package in.clouthink.daas.sbb.security.impl.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.Assert;

public class UserDetailsAuthenticationProviderImpl extends AbstractUserDetailsAuthenticationProvider {

	// FIXME move the key to configurable place
	private PasswordEncoder passwordEncoder = new StandardPasswordEncoder("bbt2016@bbt.com.cn");

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Implementation of an abstract method defined in the base class. The
	 * retrieveUser() method is called by authenticate() method of the base
	 * class. The latter is called by the AuthenticationManager.
	 */
	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails details;
		try {
			details = this.getUserDetailsService().loadUserByUsername(username);
			authentication.setDetails(details);
		}
		catch (DataAccessException repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (details == null) {
			throw new AuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return details;
	}

	/**
	 * Implementation of an abstract method defined in the base class. The
	 * additionalAuthenticationChecks() method is called by authenticate()
	 * method of the base class after the invocation of retrieveUser() method.
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
												  UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.warn("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"), null);
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			logger.warn("Authentication failed: password does not match stored value");
			throw new BadCredentialsException(messages.getMessage("UserDetailsAuthenticationProviderImpl.badCredentials",
																  "Bad credentials"), null);
		}
	}

	// *************getter/setter**************
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	// ***********InitializingBean*************
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
	}

}
