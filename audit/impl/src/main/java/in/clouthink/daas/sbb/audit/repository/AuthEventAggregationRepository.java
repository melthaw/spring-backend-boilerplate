package in.clouthink.daas.sbb.audit.repository;


import in.clouthink.daas.sbb.audit.domain.model.AggregationType;
import in.clouthink.daas.sbb.audit.domain.model.AuthEventAggregation;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthEventAggregationRepository extends AbstractRepository<AuthEventAggregation> {

	AuthEventAggregation findByRealmAndAggregationTypeAndAggregationKey(String realm, AggregationType type, String key);

	List<AuthEventAggregation> findByRealmAndAggregationTypeAndAggregationKeyLike(String realm,
																				  AggregationType type,
																				  String key);

	Page<AuthEventAggregation> findPageByRealmAndAggregationTypeOrderByAggregationKeyDesc(String realm,
																						  AggregationType type,
																						  Pageable pageable);

}
