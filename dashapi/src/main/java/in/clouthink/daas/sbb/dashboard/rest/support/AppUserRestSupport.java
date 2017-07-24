package in.clouthink.daas.sbb.dashboard.rest.support;

import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.account.dto.AppUserQueryParameter;
import in.clouthink.daas.sbb.account.dto.AppUserSummary;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface AppUserRestSupport {

	Page<AppUserSummary> listAppUsers(AppUserQueryParameter queryRequest);

	AppUserDetail getAppUserDetail(String id);

	void enableAppUser(String id);

	void disableAppUser(String id);

	void lockAppUser(String id);

	void unlockAppUser(String id);

}
