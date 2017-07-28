package in.clouthink.daas.sbb.news.domain.request;

import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;

import java.util.Date;

/**
 *
 */
public interface NewsQueryRequest extends DateRangedQueryRequest {
    
    String getTitle();
    
    String getCategory();
    
    Boolean getPublished();
    
    Date getCreatedAtBegin();
    
    Date getCreatedAtEnd();
    
    News.NewsType getNewsType();
    
}
