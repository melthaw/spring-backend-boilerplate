package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.ExtRole;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

/**
 * @author dz
 */

public interface ExtRoleRepository extends AbstractRepository<ExtRole> {

	ExtRole findByCode(String code);

	ExtRole findByName(String name);

}