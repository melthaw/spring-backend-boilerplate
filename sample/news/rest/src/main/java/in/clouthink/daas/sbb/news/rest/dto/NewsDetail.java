package in.clouthink.daas.sbb.news.rest.dto;

import in.clouthink.daas.sbb.news.domain.model.News;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class NewsDetail extends NewsSummary {
    
    public static NewsDetail from(News news) {
        if (news == null) {
            return null;
        }
        NewsDetail result = new NewsDetail();
        convert(news, result);
        result.setContent(news.getContent());
        return result;
    }
    
    public static NewsDetail from(News news, int readCounter) {
        if (news == null) {
            return null;
        }
        NewsDetail result = from(news);
        result.setReadCounter(readCounter);
        return result;
    }
    
    private String publishedById;
    
    private String publishedByName;
    
    private String content;
    
    public String getPublishedById() {
        return publishedById;
    }
    
    public void setPublishedById(String publishedById) {
        this.publishedById = publishedById;
    }
    
    public String getPublishedByName() {
        return publishedByName;
    }
    
    public void setPublishedByName(String publishedByName) {
        this.publishedByName = publishedByName;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
}
