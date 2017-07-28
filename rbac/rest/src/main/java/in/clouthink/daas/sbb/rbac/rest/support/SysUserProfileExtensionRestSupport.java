package in.clouthink.daas.sbb.rbac.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.rbac.rest.dto.MenuSummary;

import java.util.List;

/**
 *
 */
public interface SysUserProfileExtensionRestSupport {

	List<MenuSummary> getUserGrantedMenus(SysUser user);

}
