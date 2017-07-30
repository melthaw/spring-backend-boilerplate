package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.repository.custom.UserRepositoryCustom;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

/**
 * @author dz
 */
public interface UserRepository extends AbstractRepository<User>, UserRepositoryCustom {

	User findByUsername(String username);

	User findByCellphone(String cellphone);

	User findByEmail(String email);

}