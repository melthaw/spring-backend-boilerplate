package in.clouthink.daas.sbb.account.repository.custom;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.domain.request.AppUserQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.UsernameQueryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 应用用户持久层扩展
 */
public interface AppUserRepositoryCustom {

	Page<AppUser> queryPage(UsernameQueryRequest queryRequest);

	Page<AppUser> queryPage(AppRole role, AppUserQueryRequest queryRequest);

	Page<AppUser> queryPage(AppUserQueryRequest queryRequest);

	Page<AppUser> queryPageByAndTag(String[] tags, Pageable pageable);

	Page<AppUser> queryPageByOrTag(String[] tags, Pageable pageable);

	Page<AppUser> queryPageByEnabled(Pageable pageable);

	void updateHideUserTypeAttribute(String userId, boolean hideUserType);

}
