package in.clouthink.daas.sbb.account.service.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.service.AccountService;
import in.clouthink.daas.sbb.account.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private AccountService accountService;

	@Override
	public User findUserById(String userId) {
		return accountService.findById(userId);
	}

	@Override
	public User changeUserProfile(String userId, ChangeUserProfileRequest request) {
		return accountService.changeUserProfile(userId, request);
	}

	@Override
	public User changeUserPassword(String userId, String oldPassword, String newPassword) {
		return accountService.changePassword(userId, oldPassword, newPassword);
	}

	@Override
	public void updateUserAvatar(String id, String url, User byWho) {
		accountService.changeUserAvatar(byWho.getId(), id, url);
	}

}
