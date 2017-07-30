package in.clouthink.daas.sbb.rbac.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.rbac.rest.dto.MenuSummary;

import java.util.List;

/**
 *
 */
public interface UserProfileExtensionRestSupport {

	List<MenuSummary> getUserGrantedMenus(User user);

}
