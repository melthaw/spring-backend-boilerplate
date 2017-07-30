package in.clouthink.daas.sbb.notice.service.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.notice.domain.model.Notice;
import in.clouthink.daas.sbb.notice.domain.model.NoticeReadHistory;
import in.clouthink.daas.sbb.notice.domain.request.NoticeQueryRequest;
import in.clouthink.daas.sbb.notice.domain.request.SaveNoticeRequest;
import in.clouthink.daas.sbb.notice.exception.NoticeException;
import in.clouthink.daas.sbb.notice.exception.NoticeNotFoundException;
import in.clouthink.daas.sbb.notice.repository.NoticeReadHistoryRepository;
import in.clouthink.daas.sbb.notice.repository.NoticeRepository;
import in.clouthink.daas.sbb.notice.service.NoticeService;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
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
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private NoticeReadHistoryRepository noticeReadHistoryRepository;

	@Override
	public Page<Notice> listNotices(NoticeQueryRequest request) {
		return noticeRepository.queryPage(request);
	}

	@Override
	public Notice findNoticeById(String id) {
		return noticeRepository.findById(id);
	}

	@Override
	public Notice createNotice(SaveNoticeRequest request, User user) {
		checkSaveNoticeRequest(request);

		Notice notice = new Notice();
		notice.setCategory(request.getCategory());
		notice.setTitle(request.getTitle());
		notice.setSummary(request.getSummary());
		notice.setContent(request.getContent());
		notice.setCreatedAt(new Date());
		notice.setCreatedBy(user);
		return noticeRepository.save(notice);
	}

	@Override
	public void updateNotice(String id, SaveNoticeRequest request, User user) {
		checkSaveNoticeRequest(request);

		Notice notice = noticeRepository.findById(id);
		if (notice == null) {
			throw new NoticeNotFoundException();
		}
		notice.setCategory(request.getCategory());
		notice.setTitle(request.getTitle());
		notice.setSummary(request.getSummary());
		notice.setContent(request.getContent());
		notice.setModifiedAt(new Date());
		notice.setModifiedBy(user);
		noticeRepository.save(notice);
	}

	@Override
	public void deleteNotice(String id, User user) {
		noticeRepository.delete(id);
	}

	@Override
	public void markNoticeAsRead(Notice notice, User user) {
		NoticeReadHistory noticeReadHistory = noticeReadHistoryRepository.findByNoticeAndReadBy(notice, user);
		if (noticeReadHistory == null) {
			noticeReadHistory = new NoticeReadHistory();
			noticeReadHistory.setNotice(notice);
			noticeReadHistory.setReadBy(user);
			noticeReadHistory.setReadAt(new Date());
		}
		noticeReadHistory.setLatestReadAt(new Date());
		noticeReadHistory = noticeReadHistoryRepository.save(noticeReadHistory);
		int readCounter = noticeReadHistoryRepository.countByNotice(notice);
		noticeRepository.updateReadCounter(notice.getId(), readCounter);
	}

	@Override
	public boolean isNoticeReadByUser(Notice notice, User user) {
		return noticeReadHistoryRepository.findByNoticeAndReadBy(notice, user) != null;
	}

	@Override
	public int countNoticeReadHistory(Notice notice) {
		return noticeReadHistoryRepository.countByNotice(notice);
	}

	@Override
	public void publishNotice(String id, User user) {
		Notice notice = noticeRepository.findById(id);
		if (notice == null) {
			throw new NoticeNotFoundException();
		}
		notice.setPublished(true);
		notice.setPublishedAt(new Date());
		notice.setPublishedBy(user);
		noticeRepository.save(notice);
	}

	@Override
	public void unpublishNotice(String id, User user) {
		Notice notice = noticeRepository.findById(id);
		if (notice == null) {
			throw new NoticeNotFoundException();
		}
		notice.setPublished(false);
		noticeRepository.save(notice);
	}

	@Override
	public Page<NoticeReadHistory> listReadHistory(String id, PageQueryRequest queryRequest) {
		Notice notice = noticeRepository.findById(id);
		if (notice == null) {
			throw new NoticeNotFoundException();
		}
		return noticeReadHistoryRepository.findByNotice(notice,
														new PageRequest(queryRequest.getStart(),
																		queryRequest.getLimit(),
																		new Sort(Sort.Direction.DESC, "latestReadAt")));
	}

	private void checkSaveNoticeRequest(SaveNoticeRequest request) {
		if (StringUtils.isEmpty(request.getTitle())) {
			throw new NoticeException("标题不能为空");
		}

		if (StringUtils.isEmpty(request.getContent())) {
			throw new NoticeException("内容不能为空");
		}
	}

}
