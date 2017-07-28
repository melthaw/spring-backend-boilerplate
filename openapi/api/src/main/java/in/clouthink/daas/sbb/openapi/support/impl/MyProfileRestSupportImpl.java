package in.clouthink.daas.sbb.openapi.support.impl;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.account.dto.ChangeMyPasswordRequest;
import in.clouthink.daas.sbb.account.dto.ChangeMyProfileParameter;
import in.clouthink.daas.sbb.account.service.AppUserProfileService;
import in.clouthink.daas.sbb.storage.service.StorageService;
import in.clouthink.daas.sbb.openapi.support.MyProfileRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MyProfileRestSupportImpl implements MyProfileRestSupport {

	@Autowired
	private AppUserProfileService userProfileService;

	@Autowired
	private StorageService attachmentService;

	@Override
	public AppUserDetail getUserProfile(AppUser byWho) {
		AppUser appUser = userProfileService.findUserById(byWho.getId());
		return AppUserDetail.from(appUser);
	}

	@Override
	public void updateUserProfile(ChangeMyProfileParameter request, AppUser byWho) {
		userProfileService.changeUserProfile(byWho.getId(), request);
	}

	@Override
	public void updateUserAvatar(String imageId, AppUser byWho) {
		if (StringUtils.isEmpty(imageId)) {
			userProfileService.updateUserAvatar(null, null, byWho);
		}
		else {
			String avatarUrl = attachmentService.resolveImageUrl(imageId);
			userProfileService.updateUserAvatar(imageId, avatarUrl, byWho);
		}
	}

	@Override
	public void changeMyPassword(ChangeMyPasswordRequest request, AppUser byWho) {
		userProfileService.changeUserPassword(byWho.getId(), request.getOldPassword(), request.getNewPassword());
	}

}