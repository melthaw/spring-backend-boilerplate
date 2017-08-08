package in.clouthink.daas.sbb.menu.rest.support.mock;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.menu.rest.dto.Menu;
import in.clouthink.daas.sbb.menu.rest.support.UserProfileExtensionRestSupport;
import in.clouthink.daas.sbb.rbac.model.Action;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UserProfileExtensionRestSupport mocker
 *
 * @author dz
 */
@Component
public class UserProfileExtensionRestSupportMockImpl implements UserProfileExtensionRestSupport {

	@Override
	public List<Menu> getGrantedMenus(User user) {
		return null;
	}

	@Override
	public List<Action> getGrantedActions(String code, User user) {
		return null;
	}
}
