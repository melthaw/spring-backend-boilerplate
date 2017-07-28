package in.clouthink.daas.sbb.notice.domain.request;

import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;

import java.util.Date;

/**
 *
 */
public interface NoticeQueryRequest extends DateRangedQueryRequest {
    
    String getTitle();
    
    String getCategory();
    
    Boolean getPublished();

    Date getCreatedAtBegin();

    Date getCreatedAtEnd();

}
