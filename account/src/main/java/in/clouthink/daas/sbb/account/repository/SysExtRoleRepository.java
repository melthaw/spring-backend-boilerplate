package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.SysExtRole;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

/**
 * @author dz
 */

public interface SysExtRoleRepository extends AbstractRepository<SysExtRole> {

	SysExtRole findByCode(String code);

	SysExtRole findByName(String name);

}