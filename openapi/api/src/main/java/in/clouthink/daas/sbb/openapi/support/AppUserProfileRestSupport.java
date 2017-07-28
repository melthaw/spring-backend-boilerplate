package in.clouthink.daas.sbb.openapi.support;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;

/**
 * app user's profile rest support
 */
public interface AppUserProfileRestSupport {

	AppUserDetail getUserProfile(String userId, AppUser byWho);

}
