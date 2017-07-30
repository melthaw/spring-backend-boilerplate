package in.clouthink.daas.sbb.account.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.account.rest.dto.UserProfile;
import in.clouthink.daas.sbb.account.service.UserProfileService;
import in.clouthink.daas.sbb.account.rest.support.UserProfileRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProfileRestSupportImpl implements UserProfileRestSupport {

	@Autowired
	private UserProfileService userProfileService;

	@Override
	public UserProfile getUserProfile(User user) {
		User result = userProfileService.findUserById(user.getId());
		return UserProfile.from(result);
	}

	@Override
	public void updateUserProfile(ChangeMyProfileParameter request, User byWho) {
		userProfileService.changeUserProfile(byWho.getId(), request);
	}

	@Override
	public void changeMyPassword(ChangeMyPasswordRequest request, User byWho) {
		userProfileService.changeUserPassword(byWho.getId(), request.getOldPassword(), request.getNewPassword());
	}

}
