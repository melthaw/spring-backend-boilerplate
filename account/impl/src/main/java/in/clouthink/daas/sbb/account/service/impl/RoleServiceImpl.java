package in.clouthink.daas.sbb.account.service.impl;

import com.google.common.collect.Lists;
import in.clouthink.daas.sbb.account.domain.model.ExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.model.UserRoleRelationship;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveRoleRequest;
import in.clouthink.daas.sbb.account.domain.request.UserQueryRequest;
import in.clouthink.daas.sbb.account.exception.UserNotFoundException;
import in.clouthink.daas.sbb.account.exception.RoleException;
import in.clouthink.daas.sbb.account.exception.RoleNotFoundException;
import in.clouthink.daas.sbb.account.repository.ExtRoleRepository;
import in.clouthink.daas.sbb.account.repository.UserRepository;
import in.clouthink.daas.sbb.account.repository.UserRoleRelationshipRepository;
import in.clouthink.daas.sbb.account.service.RoleService;
import in.clouthink.daas.sbb.account.spi.ExtRoleReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private ExtRoleRepository extRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRelationshipRepository relationshipRepository;

	@Autowired
	private List<ExtRoleReference> roleReferenceList;

	@Override
	public Page<ExtRole> listAppRoles(RoleQueryRequest request) {
		// TODO 查询条件
		return extRoleRepository.findAll(new PageRequest(request.getStart(), request.getLimit()));
	}

	@Override
	public List<ExtRole> listAppRoles() {
		return Lists.newArrayList(extRoleRepository.findAll());
	}

	@Override
	public Page<User> listBindUsers(String id, UserQueryRequest request) {
		ExtRole appRole = findById(id);
		Page<UserRoleRelationship> relationships = relationshipRepository.findByRole(appRole,
																					 new PageRequest(request.getStart(),
																										request.getLimit()));
		return new PageImpl<>(relationships.getContent()
										   .stream()
										   .map(UserRoleRelationship::getUser)
										   .collect(Collectors.toList()),
							  new PageRequest(request.getStart(), request.getLimit()),
							  relationships.getTotalElements());
	}

	@Override
	public ExtRole findById(String id) {
		return extRoleRepository.findById(id);
	}

	@Override
	public ExtRole findByCode(String code) {
		if (code == null) {
			return null;
		}
		return extRoleRepository.findByCode(code.toUpperCase());
	}

	@Override
	public ExtRole createAppRole(SaveRoleRequest request) {
		if (StringUtils.isEmpty(request.getCode())) {
			throw new RoleException("角色编码不能为空");
		}
		if (request.getCode().toUpperCase().startsWith("ROLE_")) {
			throw new RoleException("角色编码不需要以ROLE_作为前缀");
		}
		if (ExtRole.isIllegal(request.getCode())) {
			throw new RoleException("不能使用内置角色编码");
		}
		if (StringUtils.isEmpty(request.getName())) {
			throw new RoleException("角色名称不能为空");
		}

		String roleCode = "ROLE_" + request.getCode();

		ExtRole roleByCode = extRoleRepository.findByCode(roleCode);
		if (roleByCode != null) {
			throw new RoleException("角色编码不能重复");
		}

		ExtRole roleByName = extRoleRepository.findByName(request.getName());
		if (roleByName != null) {
			throw new RoleException("角色名称不能重复");
		}

		ExtRole appRole = new ExtRole();
		appRole.setCode(roleCode);
		appRole.setName(request.getName());
		appRole.setDescription(request.getDescription());
		appRole.setCreatedAt(new Date());
		appRole.setModifiedAt(new Date());
		return extRoleRepository.save(appRole);
	}

	@Override
	public void updateAppRole(String id, SaveRoleRequest request) {
		if (StringUtils.isEmpty(request.getName())) {
			throw new RoleException("角色名称不能为空");
		}

		ExtRole target = findById(id);
		if (target == null) {
			throw new RoleNotFoundException(id);
		}

		ExtRole roleByName = extRoleRepository.findByName(request.getName());
		if (roleByName != null && !roleByName.getId().equals(target.getId())) {
			throw new RoleException("角色名称不能重复");
		}

		target.setName(request.getName());
		target.setDescription(request.getDescription());
		target.setModifiedAt(new Date());
		extRoleRepository.save(target);
	}

	@Override
	public void deleteAppRole(String id) {
		ExtRole role = extRoleRepository.findById(id);
		if (role == null) {
			return;
		}
		if (relationshipRepository.findFirstByRole(role) != null) {
			throw new RoleException("该角色下已绑定用户,请解除和用户的关系后再进行删除角色操作");
		}

		if (roleReferenceList != null) {
			roleReferenceList.forEach(ref -> {
				if (ref.hasReference(role)) {
					throw new ValidationException("该数据已经被其他数据引用,不能删除");
				}
			});
		}

		extRoleRepository.delete(role);
	}

	@Override
	public void bindUsers4AppRole(String id, List<String> userIds) {
		ExtRole appRole = findById(id);
		if (appRole == null) {
			throw new RoleNotFoundException(id);
		}
		userIds.forEach(userId -> {
			User user = userRepository.findById(userId);
			UserRoleRelationship relationship = relationshipRepository.findByUserAndRole(user, appRole);
			if (relationship == null) {
				relationship = new UserRoleRelationship();
				relationship.setRole(appRole);
				relationship.setUser(user);
				relationship.setCreatedAt(new Date());
				relationshipRepository.save(relationship);
			}
		});
	}

	@Override
	public void unBindUsers4AppRole(String id, List<String> userIds) {
		ExtRole appRole = findById(id);
		if (appRole == null) {
			throw new RoleNotFoundException(id);
		}
		userIds.forEach(userId -> {
			User user = userRepository.findById(userId);
			if (user == null) {
				throw new UserNotFoundException(userId);
			}
			UserRoleRelationship relationship = relationshipRepository.findByUserAndRole(user, appRole);
			if (relationship != null) {
				relationshipRepository.delete(relationship);
			}
		});
	}

	@Override
	public void bindUsers4SysRole(String id, List<String> userIds) {
		SysRole role = SysRole.valueOf(id);
		userIds.forEach(userId -> {
			User user = userRepository.findById(userId);
			user.addRole(role);
			userRepository.save(user);
		});
	}

	@Override
	public void unBindUsers4SysRole(String id, List<String> userIds) {
		SysRole role = SysRole.valueOf(id);
		userIds.forEach(userId -> {
			User user = userRepository.findById(userId);
			user.removeRole(role);
			userRepository.save(user);
		});
	}
}
