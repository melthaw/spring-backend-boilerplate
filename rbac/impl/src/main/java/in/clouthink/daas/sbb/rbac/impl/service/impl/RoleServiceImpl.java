package in.clouthink.daas.sbb.rbac.impl.service.impl;

import com.google.common.collect.Lists;
import in.clouthink.daas.sbb.account.domain.model.*;
import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.account.domain.request.SaveRoleRequest;
import in.clouthink.daas.sbb.account.domain.request.SysUserQueryRequest;
import in.clouthink.daas.sbb.account.exception.AppUserNotFoundException;
import in.clouthink.daas.sbb.account.exception.RoleException;
import in.clouthink.daas.sbb.account.exception.RoleNotFoundException;
import in.clouthink.daas.sbb.account.repository.SysExtRoleRepository;
import in.clouthink.daas.sbb.account.repository.SysUserRepository;
import in.clouthink.daas.sbb.account.repository.SysUserRoleRelationshipRepository;
import in.clouthink.daas.sbb.rbac.model.RoleType;
import in.clouthink.daas.sbb.rbac.impl.repository.ResourceRoleRelationshipRepository;
import in.clouthink.daas.sbb.rbac.impl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private SysExtRoleRepository appRoleRepository;

	@Autowired
	private SysUserRepository userRepository;

	@Autowired
	private SysUserRoleRelationshipRepository relationshipRepository;

	@Autowired
	private ResourceRoleRelationshipRepository resourceRoleRelationshipRepository;

	@Override
	public Page<SysExtRole> listAppRoles(RoleQueryRequest request) {
		// TODO 查询条件
		return appRoleRepository.findAll(new PageRequest(request.getStart(), request.getLimit()));
	}

	@Override
	public List<SysExtRole> listAppRoles() {
		return Lists.newArrayList(appRoleRepository.findAll());
	}

	@Override
	public Page<SysUser> listBindUsers(String id, SysUserQueryRequest request) {
		SysExtRole appRole = findById(id);
		Page<SysUserRoleRelationship> relationships = relationshipRepository.findByRole(appRole,
																						new PageRequest(request.getStart(),
																										request.getLimit()));
		return new PageImpl<>(relationships.getContent()
										   .stream()
										   .map(SysUserRoleRelationship::getUser)
										   .collect(Collectors.toList()),
							  new PageRequest(request.getStart(), request.getLimit()),
							  relationships.getTotalElements());
	}

	@Override
	public SysExtRole findById(String id) {
		return appRoleRepository.findById(id);
	}

	@Override
	public SysExtRole findByCode(String code) {
		if (code == null) {
			return null;
		}
		return appRoleRepository.findByCode(code.toUpperCase());
	}

	@Override
	public SysExtRole createAppRole(SaveRoleRequest request) {
		if (StringUtils.isEmpty(request.getCode())) {
			throw new RoleException("角色编码不能为空");
		}
		if (request.getCode().toUpperCase().startsWith("ROLE_")) {
			throw new RoleException("角色编码不需要以ROLE_作为前缀");
		}
		if (SysExtRole.isIllegal(request.getCode())) {
			throw new RoleException("不能使用内置角色编码");
		}
		if (StringUtils.isEmpty(request.getName())) {
			throw new RoleException("角色名称不能为空");
		}

		String roleCode = "ROLE_" + request.getCode();

		SysExtRole roleByCode = appRoleRepository.findByCode(roleCode);
		if (roleByCode != null) {
			throw new RoleException("角色编码不能重复");
		}

		SysExtRole roleByName = appRoleRepository.findByName(request.getName());
		if (roleByName != null) {
			throw new RoleException("角色名称不能重复");
		}

		SysExtRole appRole = new SysExtRole();
		appRole.setCode(roleCode);
		appRole.setName(request.getName());
		appRole.setDescription(request.getDescription());
		appRole.setCreatedAt(new Date());
		appRole.setModifiedAt(new Date());
		return appRoleRepository.save(appRole);
	}

	@Override
	public void updateAppRole(String id, SaveRoleRequest request) {
		if (StringUtils.isEmpty(request.getName())) {
			throw new RoleException("角色名称不能为空");
		}

		SysExtRole target = findById(id);
		if (target == null) {
			throw new RoleNotFoundException(id);
		}

		SysExtRole roleByName = appRoleRepository.findByName(request.getName());
		if (roleByName != null && !roleByName.getId().equals(target.getId())) {
			throw new RoleException("角色名称不能重复");
		}

		target.setName(request.getName());
		target.setDescription(request.getDescription());
		target.setModifiedAt(new Date());
		appRoleRepository.save(target);
	}

	@Override
	public void deleteAppRole(String id) {
		SysExtRole role = appRoleRepository.findById(id);
		if (role == null) {
			return;
		}
		if (relationshipRepository.findFirstByRole(role) != null) {
			throw new RoleException("该角色下已绑定用户,请解除和用户的关系后再进行删除角色操作");
		}

		if (resourceRoleRelationshipRepository.findFirstByRoleCode(RoleType.APP_ROLE.name() + ":" + role.getCode()) !=
			null) {
			throw new RoleException("该角色已授权访问资源,请收回访问资源的权限后再进行删除角色操作");
		}
		appRoleRepository.delete(role);
	}

	@Override
	public void bindUsers4AppRole(String id, List<String> userIds) {
		SysExtRole appRole = findById(id);
		if (appRole == null) {
			throw new RoleNotFoundException(id);
		}
		userIds.forEach(userId -> {
			SysUser user = userRepository.findById(userId);
			SysUserRoleRelationship relationship = relationshipRepository.findByUserAndRole(user, appRole);
			if (relationship == null) {
				relationship = new SysUserRoleRelationship();
				relationship.setRole(appRole);
				relationship.setUser(user);
				relationship.setCreatedAt(new Date());
				relationshipRepository.save(relationship);
			}
		});
	}

	@Override
	public void unBindUsers4AppRole(String id, List<String> userIds) {
		SysExtRole appRole = findById(id);
		if (appRole == null) {
			throw new RoleNotFoundException(id);
		}
		userIds.forEach(userId -> {
			SysUser user = userRepository.findById(userId);
			if (user == null) {
				throw new AppUserNotFoundException(userId);
			}
			SysUserRoleRelationship relationship = relationshipRepository.findByUserAndRole(user, appRole);
			if (relationship != null) {
				relationshipRepository.delete(relationship);
			}
		});
	}

	@Override
	public void bindUsers4SysRole(String id, List<String> userIds) {
		SysRole role = SysRole.valueOf(id);
		userIds.forEach(userId -> {
			SysUser user = userRepository.findById(userId);
			user.addRole(role);
			userRepository.save(user);
		});
	}

	@Override
	public void unBindUsers4SysRole(String id, List<String> userIds) {
		SysRole role = SysRole.valueOf(id);
		userIds.forEach(userId -> {
			SysUser user = userRepository.findById(userId);
			user.removeRole(role);
			userRepository.save(user);
		});
	}
}
