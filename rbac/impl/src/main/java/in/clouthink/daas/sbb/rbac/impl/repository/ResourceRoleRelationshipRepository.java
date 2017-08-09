package in.clouthink.daas.sbb.rbac.impl.repository;

import in.clouthink.daas.sbb.rbac.impl.model.ResourceRoleRelationship;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;

import java.util.List;

/**
 *
 */
public interface ResourceRoleRelationshipRepository extends AbstractRepository<ResourceRoleRelationship> {

	ResourceRoleRelationship findByResourceCodeAndRoleCode(String resourceCode, String roleCode);

	List<ResourceRoleRelationship> findListByResourceCode(String resourceCode);

	List<ResourceRoleRelationship> findListByRoleCode(String roleCode);

}
