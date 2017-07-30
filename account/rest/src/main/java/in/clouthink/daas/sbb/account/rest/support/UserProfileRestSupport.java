package in.clouthink.daas.sbb.account.rest.support;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.account.rest.dto.UserProfile;

/**
 *
 */
public interface UserProfileRestSupport {

	UserProfile getUserProfile(User byWho);

	void updateUserProfile(ChangeMyProfileParameter request, User byWho);

	void changeMyPassword(ChangeMyPasswordRequest request, User byWho);

}
