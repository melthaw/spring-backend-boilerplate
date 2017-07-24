package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.account.repository.custom.SysUserRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

/**
 * @author dz
 */
public interface SysUserRepository extends AbstractRepository<SysUser>, SysUserRepositoryCustom {

	SysUser findByUsername(String username);

	SysUser findByCellphone(String cellphone);

	SysUser findByEmail(String email);

}