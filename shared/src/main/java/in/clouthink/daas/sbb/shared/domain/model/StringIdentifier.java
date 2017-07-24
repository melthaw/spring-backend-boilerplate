package in.clouthink.daas.sbb.shared.domain.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public abstract class StringIdentifier implements Serializable {
    
    public static final String DEFAULT_ID = "1";
    
    @Id
    String id;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
