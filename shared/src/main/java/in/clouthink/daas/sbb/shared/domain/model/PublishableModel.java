package in.clouthink.daas.sbb.shared.domain.model;

import java.util.Date;

public abstract class PublishableModel extends BaseModel {
    
    private boolean published = false;
    
    private Date publishedAt;
    
    private String publishedBy;
    
    public String getPublishedBy() {
        return publishedBy;
    }
    
    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }
    
    public boolean isPublished() {
        return published;
    }
    
    public void setPublished(boolean published) {
        this.published = published;
    }
    
    public Date getPublishedAt() {
        return publishedAt;
    }
    
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
