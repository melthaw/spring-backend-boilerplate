package in.clouthink.daas.sbb.security.frontend.spring;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.service.AppUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppUserAccountService accountService;

	/**
	 * @param username the account name (may be in email format)
	 */
	@Transactional(readOnly = true)
	public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		if (!StringUtils.isEmpty(username)) {
			username = username.trim().toLowerCase();
		}
		AppUser user = accountService.findAccountByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("无效的用户名%s", username));
		}

		return new AppUserDetails(user);
	}

}
