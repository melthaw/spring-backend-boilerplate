package in.clouthink.daas.sbb.news.rest.dto;


import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.domain.request.ReadNewsEvent;

/**
 */
public class ReadNewsEventObject implements ReadNewsEvent {

	private News news;

	private SysUser user;

	public ReadNewsEventObject(News news, SysUser user) {
		this.news = news;
		this.user = user;
	}

	@Override
	public News getNews() {
		return news;
	}

	@Override
	public SysUser getUser() {
		return user;
	}
}
