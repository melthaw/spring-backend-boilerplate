package in.clouthink.daas.sbb.account.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.rest.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.account.rest.dto.UserProfile;
import in.clouthink.daas.sbb.account.rest.support.UserProfileRestSupport;
import in.clouthink.daas.sbb.account.service.UserProfileService;
import in.clouthink.daas.sbb.storage.spi.DownloadUrlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserProfileRestSupportImpl implements UserProfileRestSupport {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private DownloadUrlProvider storageService;

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

	@Override
	public void updateUserAvatar(String imageId, User byWho) {
		if (StringUtils.isEmpty(imageId)) {
			userProfileService.updateUserAvatar(null, null, byWho);
		}
		else {
			String avatarUrl = storageService.getDownloadUrl(imageId);
			userProfileService.updateUserAvatar(imageId, avatarUrl, byWho);
		}
	}

}
