package in.clouthink.daas.sbb.shared.repository.custom.impl;

import in.clouthink.daas.sbb.shared.repository.custom.AbstractCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class AbstractCustomRepositoryImpl implements AbstractCustomRepository {
    
    @Autowired
    protected MongoTemplate mongoTemplate;
    
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

}
