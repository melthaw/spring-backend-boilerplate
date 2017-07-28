package in.clouthink.daas.sbb.shared.repository;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeyifeng on 16/6/2.
 */
public class QueryBuilder {

    List<Criteria> criterias = new ArrayList<>();

    List<Criteria> andCriterias = new ArrayList<>();

    public QueryBuilder add(Criteria criteria) {
        criterias.add(criteria);
        return this;
    }

    public QueryBuilder and(Criteria criteria) {
        andCriterias.add(criteria);
        return this;
    }

    public Query build() {
        Query query = new Query();
        if (!criterias.isEmpty()) {
            for (Criteria criteria : criterias) {
                query.addCriteria(criteria);
            }
        }
        if (!andCriterias.isEmpty()) {
            Criteria[] conditions = new Criteria[andCriterias.size()];
            andCriterias.toArray(conditions);
            Criteria criteria = new Criteria().andOperator(conditions);
            query.addCriteria(criteria);
        }
        return query;
    }
}
