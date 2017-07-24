package in.clouthink.daas.sbb.openapi.support.impl;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.account.service.AppUserProfileService;
import in.clouthink.daas.sbb.openapi.support.AppUserProfileRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class AppUserProfileRestSupportImpl implements AppUserProfileRestSupport {

	@Autowired
	private AppUserProfileService userProfileService;

	@Override
	public AppUserDetail getUserProfile(String userId, AppUser byWho) {
		AppUser appUser = userProfileService.findUserById(userId);
		AppUserDetail result = AppUserDetail.from(appUser);
		return result;
	}

}
