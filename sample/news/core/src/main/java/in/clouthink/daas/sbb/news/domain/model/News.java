package in.clouthink.daas.sbb.news.domain.model;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 新闻
 */
@Document(collection = "News")
public class News extends StringIdentifier {
    
    @Indexed
    private String title;
    
    private String category;
    
    private String summary;
    
    private String content;
    
    @Indexed
    private NewsType newsType;
    
    @Indexed
    @DBRef
    private SysUser createdBy;
    
    @Indexed
    private Date createdAt;
    
    @DBRef
    private SysUser modifiedBy;
    
    @Indexed
    private Date modifiedAt;
    
    @Indexed
    private boolean published = false;
    
    private int readCounter;
    
    @Indexed
    @DBRef
    private SysUser publishedBy;
    
    @Indexed
    private Date publishedAt;
    
    public enum NewsType {
        NORMAL, IMAGE
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public SysUser getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(SysUser createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public SysUser getModifiedBy() {
        return modifiedBy;
    }
    
    public void setModifiedBy(SysUser modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    public Date getModifiedAt() {
        return modifiedAt;
    }
    
    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    public boolean isPublished() {
        return published;
    }
    
    public void setPublished(boolean published) {
        this.published = published;
    }
    
    public int getReadCounter() {
        return readCounter;
    }
    
    public void setReadCounter(int readCounter) {
        this.readCounter = readCounter;
    }
    
    public SysUser getPublishedBy() {
        return publishedBy;
    }
    
    public void setPublishedBy(SysUser publishedBy) {
        this.publishedBy = publishedBy;
    }
    
    public Date getPublishedAt() {
        return publishedAt;
    }
    
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    public NewsType getNewsType() {
        return newsType;
    }
    
    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}
