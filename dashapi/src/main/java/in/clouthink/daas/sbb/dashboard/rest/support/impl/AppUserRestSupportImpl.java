package in.clouthink.daas.sbb.dashboard.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.dto.AppUserDetail;
import in.clouthink.daas.sbb.account.dto.AppUserQueryParameter;
import in.clouthink.daas.sbb.account.dto.AppUserSummary;
import in.clouthink.daas.sbb.account.service.AppUserAccountService;
import in.clouthink.daas.sbb.account.service.AppUserProfileService;
import in.clouthink.daas.sbb.dashboard.rest.support.AppUserRestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AppUserRestSupportImpl implements AppUserRestSupport {

	@Autowired
	private AppUserAccountService accountService;

	@Autowired
	private AppUserProfileService userProfileService;

	@Override
	public Page<AppUserSummary> listAppUsers(AppUserQueryParameter queryRequest) {
		Page<AppUser> userPage = accountService.listUsers(queryRequest);
		return new PageImpl<>(userPage.getContent().stream().map(AppUserSummary::from).collect(Collectors.toList()),
							  new PageRequest(queryRequest.getStart(), queryRequest.getLimit()),
							  userPage.getTotalElements());
	}

	@Override
	public AppUserDetail getAppUserDetail(String userId) {
		AppUser appUser = userProfileService.findUserById(userId);
		AppUserDetail result = AppUserDetail.from(appUser);
		return result;
	}

	@Override
	public void enableAppUser(String userId) {
		accountService.enable(userId);
	}

	@Override
	public void disableAppUser(String userId) {
		accountService.disable(userId);
	}

	@Override
	public void lockAppUser(String userId) {
		accountService.lock(userId);
	}

	@Override
	public void unlockAppUser(String userId) {
		accountService.unlock(userId);
	}


}
