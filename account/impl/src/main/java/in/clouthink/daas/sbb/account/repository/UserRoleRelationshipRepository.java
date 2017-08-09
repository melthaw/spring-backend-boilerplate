package in.clouthink.daas.sbb.account.repository;

import in.clouthink.daas.sbb.account.domain.model.AppRole;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.account.domain.model.UserRoleRelationship;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author dz
 */
public interface UserRoleRelationshipRepository extends AbstractRepository<UserRoleRelationship> {

	Page<UserRoleRelationship> findByRole(AppRole role, Pageable pageable);

	Page<UserRoleRelationship> findByUser(User user, Pageable pageable);

	List<UserRoleRelationship> findListByUser(User user);

	UserRoleRelationship findByUserAndRole(User user, AppRole role);

	UserRoleRelationship findFirstByRole(AppRole appRole);

}
