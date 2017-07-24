package in.clouthink.daas.sbb.account.service.impl;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.domain.request.AbstractUserRequest;
import in.clouthink.daas.sbb.account.domain.request.AppUserQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveAppUserRequest;
import in.clouthink.daas.sbb.account.exception.AppUserException;
import in.clouthink.daas.sbb.account.exception.AppUserNotFoundException;
import in.clouthink.daas.sbb.account.exception.AppUserPasswordException;
import in.clouthink.daas.sbb.account.repository.AppUserRepository;
import in.clouthink.daas.sbb.account.service.AppUserAccountService;
import in.clouthink.daas.sbb.account.util.ValidationUtils;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author dz
 */
@Service
public class AppUserAccountServiceImpl implements AppUserAccountService {

	private static final Log logger = LogFactory.getLog(AppUserAccountServiceImpl.class);

	private PasswordEncoder passwordEncoder = new StandardPasswordEncoder("bbt2016@bbt.com.cn");

	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public AppUser findAccountByCellphone(String cellphone) {
		if (StringUtils.isEmpty(cellphone)) {
			return null;
		}
		cellphone = cellphone.trim().toLowerCase();
		AppUser account = appUserRepository.findByCellphone(cellphone);
		if (account == null) {
			return null;
		}

		AppUser result = new AppUser();
		BeanUtils.copyProperties(account, result, new String[]{"roles"});
		//populate app roles
		result.getAuthorities().addAll(account.getRoles());
		return result;
	}

	@Override
	public AppUser findAccountByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		username = username.trim().toLowerCase();
		AppUser account = appUserRepository.findByUsername(username);
		if (account == null) {
			return null;
		}

		AppUser result = new AppUser();
		BeanUtils.copyProperties(account, result, new String[]{"roles"});
		//populate app roles
		result.getAuthorities().addAll(account.getRoles());
		return result;
	}

	@Override
	public AppUser createAccount(SaveAppUserRequest saveAppUserRequest, AppRole... appRoles) {
		if (StringUtils.isEmpty(saveAppUserRequest.getUsername())) {
			throw new AppUserException("用户名不能为空");
		}
		checkUser(saveAppUserRequest);

		AppUser userByPhone = appUserRepository.findByCellphone(saveAppUserRequest.getCellphone());
		if (userByPhone != null) {
			throw new AppUserException(String.format("重复的联系电话'%s'!", saveAppUserRequest.getCellphone()));
		}

		AppUser existedUser = findByUsername(saveAppUserRequest.getUsername());
		if (existedUser != null) {
			throw new AppUserException(String.format("重复的用户名'%s'!", saveAppUserRequest.getUsername()));
		}

		//		AppUser userByEmail = userRepository.findByEmail(saveAppUserRequest.getEmail());
		//		if (userByEmail != null) {
		//			throw new AppUserException(String.format("重复的电子邮箱'%s'!", saveAppUserRequest.getEmail()));
		//		}

		String password = saveAppUserRequest.getPassword();
		if (StringUtils.isEmpty(password)) {
			logger.warn(String.format("The password not provided for user[username=%s], we will generate a random one ",
									  saveAppUserRequest.getUsername()));
			password = UUID.randomUUID().toString().replace("-", "");
		}
		if (password.length() < 8) {
			throw new AppUserPasswordException("新设置的密码长度不能少于8位");
		}

		String passwordHash = passwordEncoder.encode(password);


		AppUser user = new AppUser();

		user.setUsername(saveAppUserRequest.getUsername());
		//if the pass-in role is null or empty , the default role is assigned
		if (appRoles == null || appRoles.length == 0) {
			appRoles = new AppRole[]{AppRole.ROLE_USER};
		}
		user.setRoles(Arrays.asList(appRoles));
		user.setPassword(passwordHash);
		user.setCellphone(saveAppUserRequest.getCellphone());
		user.setDisplayName(saveAppUserRequest.getDisplayName());
		user.setEmail(saveAppUserRequest.getEmail());
		user.setGender(saveAppUserRequest.getGender());
		user.setBirthday(saveAppUserRequest.getBirthday());
		user.setProvince(saveAppUserRequest.getProvince());
		user.setCity(saveAppUserRequest.getCity());
		user.setAvatarId(saveAppUserRequest.getAvatarId());
		user.setAvatarUrl(saveAppUserRequest.getAvatarUrl());
		user.setSignature(saveAppUserRequest.getSignature());
		user.setEnabled(true);
		user.setCreatedAt(new Date());
		user.setTags(resolveUserTags(saveAppUserRequest));

		user = appUserRepository.save(user);

		return user;
	}

	@Override
	public AppUser updateAccount(String userId, SaveAppUserRequest request) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppUserException("用户id不能为空");
		}
		AppUser existedUser = findById(userId);
		if (existedUser == null) {
			throw new AppUserNotFoundException(userId);
		}

		//		ValidationUtils.validateEmail(request.getEmail());
		//		AppUser userByEmail = userRepository.findByEmail(request.getEmail());
		//		if (userByEmail != null && !userByEmail.getId().equals(userId)) {
		//			throw new AppUserException(String.format("重复的电子邮箱'%s'!", request.getEmail()));
		//		}

		existedUser.setDisplayName(request.getDisplayName());
		existedUser.setEmail(request.getEmail());
		existedUser.setGender(request.getGender());
		existedUser.setBirthday(request.getBirthday());
		existedUser.setProvince(request.getProvince());
		existedUser.setCity(request.getCity());
		existedUser.setSignature(request.getSignature());
		existedUser.setModifiedAt(new Date());

		existedUser.setTags(resolveUserTags(existedUser));

		return appUserRepository.save(existedUser);
	}

	@Override
	public AppUser changeUserProfile(String userId, ChangeUserProfileRequest request) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppUserException("用户id不能为空");
		}
		AppUser existedUser = findById(userId);
		if (existedUser == null) {
			throw new AppUserNotFoundException(userId);
		}

		ValidationUtils.validateEmail(request.getEmail());
		AppUser userByEmail = appUserRepository.findByEmail(request.getEmail());
		if (userByEmail != null && !userByEmail.getId().equals(userId)) {
			throw new AppUserException(String.format("重复的电子邮箱'%s'!", request.getEmail()));
		}

		existedUser.setDisplayName(request.getDisplayName());
		existedUser.setEmail(request.getEmail());
		existedUser.setGender(request.getGender());
		existedUser.setBirthday(request.getBirthday());
		existedUser.setProvince(request.getProvince());
		existedUser.setCity(request.getCity());
		existedUser.setSignature(request.getSignature());
		existedUser.setModifiedAt(new Date());

		existedUser.setTags(resolveUserTags(existedUser));

		return appUserRepository.save(existedUser);
	}


	@Override
	public AppUser changeUserAvatar(String userId, String avatarId, String avatarUrl) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppUserException("用户id不能为空");
		}
		AppUser existedUser = findById(userId);
		if (existedUser == null) {
			throw new AppUserNotFoundException(userId);
		}

		existedUser.setAvatarId(avatarId);
		existedUser.setAvatarId(avatarUrl);
		return appUserRepository.save(existedUser);
	}

	@Override
	public AppUser enable(String userId) {
		AppUser user = appUserRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setEnabled(true);
		return appUserRepository.save(user);
	}

	@Override
	public AppUser disable(String userId) {
		AppUser user = appUserRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setEnabled(false);
		return appUserRepository.save(user);
	}

	@Override
	public AppUser lock(String userId) {
		AppUser user = appUserRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setLocked(true);
		return appUserRepository.save(user);
	}

	@Override
	public AppUser unlock(String userId) {
		AppUser user = appUserRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setLocked(false);
		return appUserRepository.save(user);
	}


	@Override
	public AppUser changePassword(String userId, String oldPassword, String newPassword) {
		AppUser user = appUserRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}

		if (StringUtils.isEmpty(oldPassword)) {
			throw new AppUserPasswordException("原密码不能为空");
		}

		if (StringUtils.isEmpty(newPassword)) {
			throw new AppUserPasswordException("新设置的密码不能为空");
		}

		if (newPassword.length() < 8) {
			throw new AppUserPasswordException("新设置的密码长度不能少于8位");
		}

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new AppUserPasswordException("原密码错误");
		}

		if (newPassword.equals(oldPassword)) {
			throw new AppUserPasswordException("新设置的密码和旧密码不能相同");
		}

		String passwordHash = passwordEncoder.encode(newPassword);
		user.setPassword(passwordHash);
		return appUserRepository.save(user);
	}

	@Override
	public AppUser changePassword(String userId, String newPassword) {
		AppUser user = appUserRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}

		if (StringUtils.isEmpty(newPassword)) {
			throw new AppUserPasswordException("新设置的密码不能为空");
		}

		if (newPassword.length() < 8) {
			throw new AppUserPasswordException("新设置的密码长度不能少于8位");
		}

		String passwordHash = passwordEncoder.encode(newPassword);
		user.setPassword(passwordHash);
		return appUserRepository.save(user);
	}

	@Override
	public AppUser findById(String userId) {
		return appUserRepository.findById(userId);
	}

	@Override
	public AppUser findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		username = username.trim().toLowerCase();
		return appUserRepository.findByUsername(username);
	}

	@Override
	public Page<AppUser> listUsers(AppUserQueryRequest userQueryRequest) {
		return appUserRepository.queryPage(userQueryRequest);
	}

	@Override
	public Page<AppUser> findUsersByRole(AppRole appRole, PageQueryRequest queryRequest) {
		return appUserRepository.findByRolesContaining(appRole,
													   new PageRequest(queryRequest.getStart(),
																	   queryRequest.getLimit()));
	}

	//********private part************
	private void checkUser(AbstractUserRequest request) {
		ValidationUtils.validateCellphone(request.getCellphone());
		ValidationUtils.validateEmail(request.getEmail());
	}

	private List<String> resolveUserTags(AppUser appUser) {

		List<String> tagList = new ArrayList<>();
		if (appUser.getGender() != null) {
			tagList.add(appUser.getGender().name());
		}
		if (appUser.getCity() != null) {
			tagList.add(appUser.getCity());
		}
		if (appUser.getProvince() != null) {
			tagList.add(appUser.getProvince());
		}

		return tagList;
	}

	private List<String> resolveUserTags(SaveAppUserRequest saveAppUserRequest) {
		List<String> tagList = new ArrayList<>();
		if (saveAppUserRequest != null) {
			if (saveAppUserRequest.getGender() != null) {
				tagList.add(saveAppUserRequest.getGender().name());
			}
			if (saveAppUserRequest.getCity() != null) {
				tagList.add(saveAppUserRequest.getCity());
			}
			if (saveAppUserRequest.getProvince() != null) {
				tagList.add(saveAppUserRequest.getProvince());
			}
		}

		return tagList;
	}

}
