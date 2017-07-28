package in.clouthink.daas.sbb.news.rest.dto;

import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.domain.request.SaveNewsRequest;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class SaveNewsParameter implements SaveNewsRequest {

	private String title;

	private String category;

	private String summary;

	private String content;

	private News.NewsType newsType;

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public News.NewsType getNewsType() {
		return newsType;
	}

	public void setNewsType(News.NewsType newsType) {
		this.newsType = newsType;
	}
}
