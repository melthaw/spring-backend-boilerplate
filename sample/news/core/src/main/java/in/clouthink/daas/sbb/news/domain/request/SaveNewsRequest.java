package in.clouthink.daas.sbb.news.domain.request;


import in.clouthink.daas.sbb.news.domain.model.News;

/**
 *
 */
public interface SaveNewsRequest {

	String getCategory();

	String getTitle();

	// 不超过140个字
	String getSummary();

	String getContent();

	News.NewsType getNewsType();

}
