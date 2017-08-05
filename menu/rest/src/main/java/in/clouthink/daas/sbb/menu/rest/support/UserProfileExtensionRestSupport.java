package in.clouthink.daas.sbb.menu.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.menu.rest.dto.Menu;
import in.clouthink.daas.sbb.rbac.model.Action;

import java.util.List;

/**
 *
 */
public interface UserProfileExtensionRestSupport {

	/**
	 *
	 * @param user
	 * @return
	 */
	List<Menu> getGrantedMenus(User user);

	/**
	 *
	 * @param code
	 * @param user
	 * @return
	 */
	List<Action> getGrantedActions(String code, User user);
}
