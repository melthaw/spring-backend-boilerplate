package in.clouthink.daas.sbb.audit.repository;


import in.clouthink.daas.sbb.audit.domain.model.AggregationType;
import in.clouthink.daas.sbb.audit.domain.model.AuditEventAggregation;
import in.clouthink.daas.sbb.shared.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuditEventAggregationRepository extends AbstractRepository<AuditEventAggregation> {

	AuditEventAggregation findByRealmAndAggregationTypeAndAggregationKey(String realm,
																		 AggregationType type,
																		 String key);

	List<AuditEventAggregation> findByRealmAndAggregationTypeAndAggregationKeyLike(String realm,
																				   AggregationType type,
																				   String key);

	Page<AuditEventAggregation> findPageByRealmAndAggregationTypeOrderByAggregationKeyDesc(String realm,
																						   AggregationType type,
																						   Pageable pageable);

}
