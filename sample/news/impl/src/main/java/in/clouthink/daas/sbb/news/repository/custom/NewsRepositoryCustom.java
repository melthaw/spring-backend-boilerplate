package in.clouthink.daas.sbb.news.repository.custom;

import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.domain.request.NewsQueryRequest;
import org.springframework.data.domain.Page;

public interface NewsRepositoryCustom {

	Page<News> queryPage(NewsQueryRequest queryRequest);

	void updateReadCounter(String id, int readCounter);

}
