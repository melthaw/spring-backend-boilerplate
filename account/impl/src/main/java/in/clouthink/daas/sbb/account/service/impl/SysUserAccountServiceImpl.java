package in.clouthink.daas.sbb.account.service.impl;

import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.request.AbstractUserRequest;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveSysUserRequest;
import in.clouthink.daas.sbb.account.domain.request.SysUserQueryRequest;
import in.clouthink.daas.sbb.account.exception.AppUserNotFoundException;
import in.clouthink.daas.sbb.account.exception.AppUserPasswordException;
import in.clouthink.daas.sbb.account.exception.SysUserException;
import in.clouthink.daas.sbb.account.repository.SysUserRepository;
import in.clouthink.daas.sbb.account.repository.SysUserRoleRelationshipRepository;
import in.clouthink.daas.sbb.account.service.SysUserAccountService;
import in.clouthink.daas.sbb.shared.DomainConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
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
public class SysUserAccountServiceImpl implements SysUserAccountService {

	private static final Log logger = LogFactory.getLog(SysUserAccountServiceImpl.class);

	private PasswordEncoder passwordEncoder = new StandardPasswordEncoder("bbt2016@bbt.com.cn");

	@Autowired
	private SysUserRepository userRepository;

	@Autowired
	private SysUserRoleRelationshipRepository userRoleRelationshipRepository;

	@Override
	public SysUser findAccountByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		username = username.trim().toLowerCase();
		SysUser account = userRepository.findByUsername(username);
		if (account == null) {
			return null;
		}

		SysUser result = new SysUser();
		BeanUtils.copyProperties(account, result, new String[]{"roles"});
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
	public SysUser createAccount(SaveSysUserRequest saveUserRequest, SysRole... sysRoles) {

		if (StringUtils.isEmpty(saveUserRequest.getUsername())) {
			throw new SysUserException("用户名不能为空");
		}
		checkUser(saveUserRequest);

		SysUser existedUser = findByUsername(saveUserRequest.getUsername());
		if (existedUser != null) {
			throw new SysUserException(String.format("重复的用户名'%s'!", saveUserRequest.getUsername()));
		}

		SysUser userByPhone = userRepository.findByCellphone(saveUserRequest.getCellphone());
		if (userByPhone != null) {
			throw new SysUserException(String.format("重复的联系电话'%s'!", saveUserRequest.getCellphone()));
		}

		SysUser userByEmail = userRepository.findByEmail(saveUserRequest.getEmail());
		if (userByEmail != null) {
			throw new SysUserException(String.format("重复的电子邮箱'%s'!", saveUserRequest.getEmail()));
		}

		String password = saveUserRequest.getPassword();
		if (StringUtils.isEmpty(password)) {
			logger.warn(String.format("The password not provided for user[username=%s], we will generate a random one ",
									  saveUserRequest.getUsername()));
			password = UUID.randomUUID().toString().replace("-", "");
		}
		if (password.length() < 8) {
			throw new AppUserPasswordException("新设置的密码长度不能少于8位");
		}

		String passwordHash = passwordEncoder.encode(password);

		SysUser user = new SysUser();

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
	public SysUser updateAccount(String userId, SaveSysUserRequest request) {
		if (StringUtils.isEmpty(userId)) {
			throw new SysUserException("用户id不能为空");
		}
		SysUser existedUser = findById(userId);
		if (existedUser == null) {
			throw new AppUserNotFoundException(userId);
		}

		checkUser(request);

		SysUser userByPhone = userRepository.findByCellphone(request.getCellphone());
		if (userByPhone != null && !userByPhone.getId().equals(userId)) {
			throw new SysUserException(String.format("重复的联系电话'%s'!", request.getCellphone()));
		}

		SysUser userByEmail = userRepository.findByEmail(request.getEmail());
		if (userByEmail != null && !userByEmail.getId().equals(userId)) {
			throw new SysUserException(String.format("重复的电子邮箱'%s'!", request.getEmail()));
		}

		existedUser.setEmail(request.getEmail());
		existedUser.setCellphone(request.getCellphone());
		existedUser.setGender(request.getGender());
		existedUser.setBirthday(request.getBirthday());
		existedUser.setModifiedAt(new Date());

		return userRepository.save(existedUser);
	}

	@Override
	public SysUser changeUserProfile(String userId, ChangeUserProfileRequest request) {
		if (StringUtils.isEmpty(userId)) {
			throw new SysUserException("用户id不能为空");
		}
		SysUser existedUser = findById(userId);
		if (existedUser == null) {
			throw new AppUserNotFoundException(userId);
		}

		checkUser(request);

		SysUser userByPhone = userRepository.findByCellphone(request.getCellphone());
		if (userByPhone != null && !userByPhone.getId().equals(userId)) {
			throw new SysUserException(String.format("重复的联系电话'%s'!", request.getCellphone()));
		}

		SysUser userByEmail = userRepository.findByEmail(request.getEmail());
		if (userByEmail != null && !userByEmail.getId().equals(userId)) {
			throw new SysUserException(String.format("重复的电子邮箱'%s'!", request.getEmail()));
		}

		existedUser.setEmail(request.getEmail());
		existedUser.setCellphone(request.getCellphone());
		existedUser.setGender(request.getGender());
		existedUser.setBirthday(request.getBirthday());
		existedUser.setModifiedAt(new Date());

		return userRepository.save(existedUser);
	}

	@Override
	public SysUser enable(String userId) {
		SysUser user = userRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setEnabled(true);
		return userRepository.save(user);
	}

	@Override
	public SysUser disable(String userId) {
		SysUser user = userRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setEnabled(false);
		return userRepository.save(user);
	}

	@Override
	public SysUser lock(String userId) {
		SysUser user = userRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setLocked(true);
		return userRepository.save(user);
	}

	@Override
	public SysUser unlock(String userId) {
		SysUser user = userRepository.findById(userId);
		if (user == null) {
			throw new AppUserNotFoundException(userId);
		}
		user.setLocked(false);
		return userRepository.save(user);
	}

	@Override
	public SysUser changePassword(String userId, String oldPassword, String newPassword) {
		SysUser user = userRepository.findById(userId);
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
		return userRepository.save(user);
	}

	@Override
	public SysUser changePassword(String userId, String newPassword) {
		SysUser user = userRepository.findById(userId);
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
		return userRepository.save(user);
	}

	@Override
	public SysUser findById(String userId) {
		return userRepository.findById(userId);
	}

	@Override
	public SysUser findByUsername(String username) {
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
		SysUser user = userRepository.findByUsername(username);
		if (user == null) {
			throw new AppUserNotFoundException(username);
		}
		//TODO send passcode to reset password
	}

	@Override
	public void deleteUser(String userId, SysUser byWho) {
		SysUser user = userRepository.findById(userId);
		if (user == null) {
			return;
		}

		//		if (!"administrator".equals(byWho.getUsername())) {
		//			throw new UserException("只有超级管理员能彻底删除用户!");
		//		}

		throw new UnsupportedOperationException("目前不支持彻底删除用户!");
	}

	@Override
	public Page<SysUser> listUsers(SysUserQueryRequest userQueryRequest) {
		return userRepository.queryPage(userQueryRequest);
	}

	@Override
	public Page<SysUser> listUsersByRole(SysRole role, SysUserQueryRequest userQueryRequest) {
		return userRepository.queryPage(role, userQueryRequest);
	}

	@Override
	public Page<SysUser> listArchivedUsers(SysUserQueryRequest userQueryRequest) {
		return userRepository.queryArchivedUsers(userQueryRequest);
	}

	@Override
	public void archiveAccount(String userId, SysUser byWho) {
		SysUser user = userRepository.findById(userId);
		if (user == null) {
			return;
		}

		if ("administrator".equals(user.getUsername())) {
			throw new SysUserException("不能删除超级管理员");
		}

		if (byWho == null ||
			(!byWho.getAuthorities().contains(SysRole.ROLE_MGR) &&
			 !byWho.getAuthorities().contains(SysRole.ROLE_ADMIN))) {
			throw new SysUserException("只有管理员和超级管理员能删除已经创建的用户!");
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
			throw new SysUserException("联系电话不能为空");
		}
		if (StringUtils.isEmpty(request.getEmail())) {
			throw new SysUserException("邮箱不能为空");
		}
		if (!DomainConstants.VALID_CELLPHONE_REGEX.matcher(request.getCellphone()).matches()) {
			throw new SysUserException("联系电话格式错误,请输入手机号码");
		}
		if (!StringUtils.isEmpty(request.getEmail()) &&
			!DomainConstants.VALID_EMAIL_REGEX.matcher(request.getEmail()).matches()) {
			throw new SysUserException("电子邮箱格式错误");
		}
	}

}
