package in.clouthink.daas.sbb.account.service.impl;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.service.AppUserAccountService;
import in.clouthink.daas.sbb.account.service.AppUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class AppUserProfileServiceImpl implements AppUserProfileService {

	@Autowired
	private AppUserAccountService accountService;

	@Override
	public AppUser findUserById(String userId) {
		return accountService.findById(userId);
	}

	@Override
	public AppUser changeUserProfile(String userId, ChangeUserProfileRequest request) {
		return accountService.changeUserProfile(userId, request);
	}

	@Override
	public AppUser changeUserPassword(String userId, String oldPassword, String newPassword) {
		return accountService.changePassword(userId, oldPassword, newPassword);
	}

	@Override
	public void updateUserAvatar(String id, String url, AppUser byWho) {
		accountService.changeUserAvatar(byWho.getId(), id, url);
	}

}
