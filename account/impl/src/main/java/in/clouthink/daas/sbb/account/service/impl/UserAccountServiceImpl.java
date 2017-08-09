package in.clouthink.daas.sbb.account.service.impl;

import in.clouthink.daas.sbb.account.AccountPasswordConfigurationProperties;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.request.AbstractUserRequest;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveUserRequest;
import in.clouthink.daas.sbb.account.domain.request.UserQueryRequest;
import in.clouthink.daas.sbb.account.exception.UserException;
import in.clouthink.daas.sbb.account.exception.UserNotFoundException;
import in.clouthink.daas.sbb.account.exception.UserPasswordException;
import in.clouthink.daas.sbb.account.repository.UserRepository;
import in.clouthink.daas.sbb.account.repository.UserRoleRelationshipRepository;
import in.clouthink.daas.sbb.account.service.AccountService;
import in.clouthink.daas.sbb.shared.DomainConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class UserAccountServiceImpl implements AccountService, InitializingBean {

	private static final Log logger = LogFactory.getLog(UserAccountServiceImpl.class);

	@Autowired
	private AccountPasswordConfigurationProperties accountConfigurationProperties;

	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRelationshipRepository userRoleRelationshipRepository;

	@Override
	public User findAccountByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		username = username.trim().toLowerCase();
		User account = userRepository.findByUsername(username);
		if (account == null) {
			return null;
		}

		User result = new User();
		BeanUtils.copyProperties(account, result, "roles");
		//populate sys roles
		result.getAuthorities().addAll(account.getRoles());
		//populate app role
		result.getAuthorities()
			  .addAll(userRoleRelationshipRepository.findListByUser(account)
													.stream()
													.map(relationship -> relationship.getRole())
													.collect(Collectors.toList()));
		return result;
	}

	@Override
	public User createAccount(SaveUserRequest saveUserRequest, SysRole... sysRoles) {

		if (StringUtils.isEmpty(saveUserRequest.getUsername())) {
			throw new UserException("用户名不能为空");
		}
		checkUser(saveUserRequest);

		User existedUser = findByUsername(saveUserRequest.getUsername());
		if (existedUser != null) {
			throw new UserException(String.format("重复的用户名'%s'!", saveUserRequest.getUsername()));
		}

		User userByPhone = userRepository.findByCellphone(saveUserRequest.getCellphone());
		if (userByPhone != null) {
			throw new UserException(String.format("重复的联系电话'%s'!", saveUserRequest.getCellphone()));
		}

		User userByEmail = userRepository.findByEmail(saveUserRequest.getEmail());
		if (userByEmail != null) {
			throw new UserException(String.format("重复的电子邮箱'%s'!", saveUserRequest.getEmail()));
		}

		String password = saveUserRequest.getPassword();
		if (StringUtils.isEmpty(password)) {
			logger.warn(String.format("The password not provided for user[username=%s], we will generate a random one ",
									  saveUserRequest.getUsername()));
			password = UUID.randomUUID().toString().replace("-", "");
		}
		if (password.length() < 8) {
			throw new UserPasswordException("新设置的密码长度不能少于8位");
		}

		String passwordHash = passwordEncoder.encode(password);

		User user = new User();

		user.setUsername(saveUserRequest.getUsername());
		//if the pass-in role is null or empty , the default role is assigned
		if (sysRoles == null || sysRoles.length == 0) {
			sysRoles = new SysRole[]{SysRole.ROLE_USER};
		}
		user.setRoles(Arrays.asList(sysRoles));
		user.setPassword(passwordHash);
		user.setCellphone(saveUserRequest.getCellphone());
		user.setEmail(saveUserRequest.getEmail());
		user.setGender(saveUserRequest.getGender());
		user.setBirthday(saveUserRequest.getBirthday());
		user.setEnabled(true);

		user.setCreatedAt(new Date());


		return userRepository.save(user);
	}

	@Override
	public User updateAccount(String userId, SaveUserRequest request) {
		if (StringUtils.isEmpty(userId)) {
			throw new UserException("用户id不能为空");
		}
		User existedUser = findById(userId);
		if (existedUser == null) {
			throw new UserNotFoundException(userId);
		}

		checkUser(request);

		User userByPhone = userRepository.findByCellphone(request.getCellphone());
		if (userByPhone != null && !userByPhone.getId().equals(userId)) {
			throw new UserException(String.format("重复的联系电话'%s'!", request.getCellphone()));
		}

		User userByEmail = userRepository.findByEmail(request.getEmail());
		if (userByEmail != null && !userByEmail.getId().equals(userId)) {
			throw new UserException(String.format("重复的电子邮箱'%s'!", request.getEmail()));
		}

		existedUser.setEmail(request.getEmail());
		existedUser.setCellphone(request.getCellphone());
		existedUser.setGender(request.getGender());
		existedUser.setBirthday(request.getBirthday());
		existedUser.setModifiedAt(new Date());

		return userRepository.save(existedUser);
	}

	@Override
	public User changeUserProfile(String userId, ChangeUserProfileRequest request) {
		if (StringUtils.isEmpty(userId)) {
			throw new UserException("用户id不能为空");
		}
		User existedUser = findById(userId);
		if (existedUser == null) {
			throw new UserNotFoundException(userId);
		}

		checkUser(request);

		User userByPhone = userRepository.findByCellphone(request.getCellphone());
		if (userByPhone != null && !userByPhone.getId().equals(userId)) {
			throw new UserException(String.format("重复的联系电话'%s'!", request.getCellphone()));
		}

		User userByEmail = userRepository.findByEmail(request.getEmail());
		if (userByEmail != null && !userByEmail.getId().equals(userId)) {
			throw new UserException(String.format("重复的电子邮箱'%s'!", request.getEmail()));
		}

		existedUser.setEmail(request.getEmail());
		existedUser.setCellphone(request.getCellphone());
		existedUser.setGender(request.getGender());
		existedUser.setBirthday(request.getBirthday());
		existedUser.setModifiedAt(new Date());

		return userRepository.save(existedUser);
	}


	@Override
	public User changeUserAvatar(String userId, String avatarId, String avatarUrl) {
		if (StringUtils.isEmpty(userId)) {
			throw new UserException("用户id不能为空");
		}
		User existedUser = findById(userId);
		if (existedUser == null) {
			throw new UserNotFoundException(userId);
		}

		existedUser.setAvatarId(avatarId);
		existedUser.setAvatarUrl(avatarUrl);
		return userRepository.save(existedUser);
	}

	@Override
	public User enable(String userId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		user.setEnabled(true);
		return userRepository.save(user);
	}

	@Override
	public User disable(String userId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		user.setEnabled(false);
		return userRepository.save(user);
	}

	@Override
	public User lock(String userId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		user.setLocked(true);
		return userRepository.save(user);
	}

	@Override
	public User unlock(String userId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		user.setLocked(false);
		return userRepository.save(user);
	}

	@Override
	public User changePassword(String userId, String oldPassword, String newPassword) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}

		if (StringUtils.isEmpty(oldPassword)) {
			throw new UserPasswordException("原密码不能为空");
		}

		if (StringUtils.isEmpty(newPassword)) {
			throw new UserPasswordException("新设置的密码不能为空");
		}

		if (newPassword.length() < 8) {
			throw new UserPasswordException("新设置的密码长度不能少于8位");
		}

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new UserPasswordException("原密码错误");
		}

		if (newPassword.equals(oldPassword)) {
			throw new UserPasswordException("新设置的密码和旧密码不能相同");
		}

		String passwordHash = passwordEncoder.encode(newPassword);
		user.setPassword(passwordHash);
		return userRepository.save(user);
	}

	@Override
	public User changePassword(String userId, String newPassword) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}

		if (StringUtils.isEmpty(newPassword)) {
			throw new UserPasswordException("新设置的密码不能为空");
		}

		if (newPassword.length() < 8) {
			throw new UserPasswordException("新设置的密码长度不能少于8位");
		}

		String passwordHash = passwordEncoder.encode(newPassword);
		user.setPassword(passwordHash);
		return userRepository.save(user);
	}

	@Override
	public User findById(String userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		username = username.trim().toLowerCase();
		return userRepository.findByUsername(username);
	}

	@Override
	public void forgetPassword(String username) {
		if (StringUtils.isEmpty(username)) {
			return;
		}
		username = username.trim().toLowerCase();
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundException(username);
		}
		//TODO send passcode to reset password
	}

	@Override
	public void deleteUser(String userId, User byWho) {
		User user = userRepository.findById(userId);
		if (user == null) {
			return;
		}

		//		if (!"administrator".equals(byWho.getUsername())) {
		//			throw new UserException("只有超级管理员能彻底删除用户!");
		//		}

		throw new UnsupportedOperationException("目前不支持彻底删除用户!");
	}

	@Override
	public Page<User> listUsers(UserQueryRequest userQueryRequest) {
		return userRepository.queryPage(userQueryRequest);
	}

	@Override
	public Page<User> listUsersByRole(SysRole role, UserQueryRequest userQueryRequest) {
		return userRepository.queryPage(role, userQueryRequest);
	}

	@Override
	public Page<User> listArchivedUsers(UserQueryRequest userQueryRequest) {
		return userRepository.queryArchivedUsers(userQueryRequest);
	}

	@Override
	public void archiveAccount(String userId, User byWho) {
		User user = userRepository.findById(userId);
		if (user == null) {
			return;
		}

		if ("administrator".equals(user.getUsername())) {
			throw new UserException("不能删除超级管理员");
		}

		if (byWho == null ||
			(!byWho.getAuthorities().contains(SysRole.ROLE_MGR) &&
			 !byWho.getAuthorities().contains(SysRole.ROLE_ADMIN))) {
			throw new UserException("只有管理员和超级管理员能删除已经创建的用户!");
		}

		user.setUsername(user.getUsername() + "(已删除)");
		user.setCellphone(user.getCellphone() + "(已删除)");
		user.setEmail(user.getEmail() + "(已删除)");
		user.setEnabled(false);
		user.setLocked(true);
		user.setExpired(true);
		user.setPassword(UUID.randomUUID().toString());
		user.setRoles(new ArrayList<>());
		user.setDeleted(true);
		user.setDeletedAt(new Date());

		userRepository.save(user);
	}

	private void checkUser(AbstractUserRequest request) {
		if (StringUtils.isEmpty(request.getCellphone())) {
			throw new UserException("联系电话不能为空");
		}
		if (StringUtils.isEmpty(request.getEmail())) {
			throw new UserException("邮箱不能为空");
		}
		if (!DomainConstants.VALID_CELLPHONE_REGEX.matcher(request.getCellphone()).matches()) {
			throw new UserException("联系电话格式错误,请输入手机号码");
		}
		if (!StringUtils.isEmpty(request.getEmail()) &&
			!DomainConstants.VALID_EMAIL_REGEX.matcher(request.getEmail()).matches()) {
			throw new UserException("电子邮箱格式错误");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.passwordEncoder = new StandardPasswordEncoder(accountConfigurationProperties.getSalt());
	}
}
