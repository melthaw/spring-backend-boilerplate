package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.AppUser;
import in.clouthink.daas.sbb.account.repository.custom.AppUserRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author dz
 */
public interface AppUserRepository extends AbstractRepository<AppUser>, AppUserRepositoryCustom {

	AppUser findByUsername(String username);

	AppUser findByCellphone(String cellphone);

	AppUser findByEmail(String email);

	Page<AppUser> findByRolesContaining(AppRole appRole, Pageable pageable);

}