package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.SysExtRole;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.domain.model.SysUserRoleRelationship;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author dz
 */
public interface SysUserRoleRelationshipRepository extends AbstractRepository<SysUserRoleRelationship> {

	Page<SysUserRoleRelationship> findByRole(SysExtRole role, Pageable pageable);

	Page<SysUserRoleRelationship> findByUser(SysUser user, Pageable pageable);

	List<SysUserRoleRelationship> findListByUser(SysUser user);

	SysUserRoleRelationship findByUserAndRole(SysUser user, SysExtRole role);

	SysUserRoleRelationship findFirstByRole(SysExtRole appRole);

}
