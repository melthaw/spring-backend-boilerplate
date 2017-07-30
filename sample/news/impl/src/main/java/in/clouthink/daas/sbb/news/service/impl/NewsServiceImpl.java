package in.clouthink.daas.sbb.news.service.impl;

import in.clouthink.daas.fss.mongodb.model.FileObject;
import in.clouthink.daas.fss.mongodb.repository.FileObjectRepository;
import in.clouthink.daas.fss.spi.FileObjectService;
import in.clouthink.daas.fss.spi.MutableFileObjectService;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.news.domain.model.News;
import in.clouthink.daas.sbb.news.domain.model.NewsReadHistory;
import in.clouthink.daas.sbb.news.domain.request.NewsQueryRequest;
import in.clouthink.daas.sbb.news.domain.request.SaveNewsRequest;
import in.clouthink.daas.sbb.news.exception.NewsAttachmentException;
import in.clouthink.daas.sbb.news.exception.NewsException;
import in.clouthink.daas.sbb.news.exception.NewsNotFoundException;
import in.clouthink.daas.sbb.news.repository.NewsReadHistoryRepository;
import in.clouthink.daas.sbb.news.repository.NewsRepository;
import in.clouthink.daas.sbb.news.service.NewsService;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 *
 */
@Service
public class NewsServiceImpl implements NewsService {

	private static final Log logger = LogFactory.getLog(NewsServiceImpl.class);

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private NewsReadHistoryRepository newsReadHistoryRepository;

	@Autowired
	private FileObjectRepository fileObjectRepository;

	@Autowired
	private FileObjectService fileObjectService;

	@Override
	public Page<News> listNews(NewsQueryRequest request) {
		return newsRepository.queryPage(request);
	}

	@Override
	public News findNewsById(String id) {
		return newsRepository.findById(id);
	}

	@Override
	public News createNews(SaveNewsRequest request, User user) {
		checkSaveNewsRequest(request);

		News news = new News();
		news.setCategory(request.getCategory());
		news.setTitle(request.getTitle());
		news.setSummary(request.getSummary());
		news.setContent(request.getContent());
		news.setNewsType(request.getNewsType());
		news.setCreatedAt(new Date());
		news.setCreatedBy(user);
		return newsRepository.save(news);
	}

	@Override
	public void updateNews(String id, SaveNewsRequest request, User user) {
		checkSaveNewsRequest(request);

		News news = newsRepository.findById(id);
		if (news == null) {
			throw new NewsNotFoundException();
		}
		news.setCategory(request.getCategory());
		news.setTitle(request.getTitle());
		news.setSummary(request.getSummary());
		news.setContent(request.getContent());
		news.setNewsType(request.getNewsType());
		news.setModifiedAt(new Date());
		news.setModifiedBy(user);
		newsRepository.save(news);
	}

	@Override
	public void deleteNews(String id, User user) {
		newsRepository.delete(id);
	}

	@Override
	public void markNewsAsRead(News news, User user) {
		NewsReadHistory newsReadHistory = newsReadHistoryRepository.findByNewsAndReadBy(news, user);
		if (newsReadHistory == null) {
			newsReadHistory = new NewsReadHistory();
			newsReadHistory.setNews(news);
			newsReadHistory.setReadBy(user);
			newsReadHistory.setReadAt(new Date());
		}
		newsReadHistory.setLatestReadAt(new Date());
		newsReadHistory = newsReadHistoryRepository.save(newsReadHistory);
		int readCounter = newsReadHistoryRepository.countByNews(news);
		newsRepository.updateReadCounter(news.getId(), readCounter);
	}

	@Override
	public boolean isNewsReadBySysUser(News news, User user) {
		return newsReadHistoryRepository.findByNewsAndReadBy(news, user) != null;
	}

	public int countNewsReadHistory(News news) {
		return newsReadHistoryRepository.countByNews(news);
	}

	@Override
	public void publishNews(String id, User user) {
		News news = newsRepository.findById(id);
		if (news == null) {
			throw new NewsNotFoundException();
		}
		if (news.getNewsType() == News.NewsType.IMAGE) {
			Page<FileObject> fileObjects = fileObjectRepository.findByBizId(news.getId(), new PageRequest(0, 1));
			if (fileObjects.getContent() == null || fileObjects.getContent().isEmpty()) {
				throw new NewsAttachmentException("图片新闻必须上传图片。");
			}
		}
		news.setPublished(true);
		news.setPublishedAt(new Date());
		news.setPublishedBy(user);
		newsRepository.save(news);
	}

	@Override
	public void unpublishNews(String id, User user) {
		News news = newsRepository.findById(id);
		if (news == null) {
			throw new NewsNotFoundException();
		}
		news.setPublished(false);
		newsRepository.save(news);
	}

	@Override
	public Page<NewsReadHistory> listReadHistory(String id, PageQueryRequest queryRequest) {
		News news = newsRepository.findById(id);
		if (news == null) {
			throw new NewsNotFoundException();
		}
		return newsReadHistoryRepository.findByNews(news,
													new PageRequest(queryRequest.getStart(),
																	queryRequest.getLimit(),
																	new Sort(Sort.Direction.DESC, "latestReadAt")));
	}

	@Override
	public String getImageFileObjectId(News news) {
		if (news == null) {
			return null;
		}
		Page<FileObject> fileObjects = fileObjectRepository.findByBizId(news.getId(), new PageRequest(0, 1));
		if (fileObjects.getContent() != null && !fileObjects.getContent().isEmpty()) {
			return fileObjects.getContent().get(0).getId();
		}
		return null;
	}

	@Override
	public void deleteAttachment(String id, String fileId, User user) {
		News news = newsRepository.findById(id);
		if (news == null) {
			throw new NewsNotFoundException();
		}

		if (!news.isPublished()) {
			in.clouthink.daas.fss.core.FileObject fileObject = fileObjectService.findById(fileId);
			String uploadedBy = fileObject.getUploadedBy();
			if (!user.getUsername().equals(uploadedBy) &&
				!user.getUsername().equals(news.getCreatedBy().getUsername())) {
				throw new NewsAttachmentException("只能删除自己上传的附件.");
			}
			try {
				if (fileObjectService instanceof MutableFileObjectService) {
					((MutableFileObjectService) fileObjectService).deleteById(fileId);
				}
			}
			catch (Exception e) {
			}
			return;
		}
		else {
			throw new NewsAttachmentException("该新闻已发布,不能删除附件.");
		}

	}

	private void checkSaveNewsRequest(SaveNewsRequest request) {
		if (StringUtils.isEmpty(request.getTitle())) {
			throw new NewsException("标题不能为空");
		}

		if (StringUtils.isEmpty(request.getContent())) {
			throw new NewsException("内容不能为空");
		}
	}

}
