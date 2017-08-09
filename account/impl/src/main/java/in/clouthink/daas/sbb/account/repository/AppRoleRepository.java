package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

/**
 * @author dz
 */

public interface AppRoleRepository extends AbstractRepository<AppRole> {

	AppRole findByCode(String code);

	AppRole findByName(String name);

}