package in.clouthink.daas.sbb.attachment.service.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.domain.model.AttachmentDownloadHistory;
import in.clouthink.daas.sbb.attachment.domain.request.AttachmentQueryRequest;
import in.clouthink.daas.sbb.attachment.domain.request.SaveAttachmentRequest;
import in.clouthink.daas.sbb.attachment.exception.AttachmentException;
import in.clouthink.daas.sbb.attachment.exception.AttachmentNotFoundException;
import in.clouthink.daas.sbb.attachment.repository.AttachmentDownloadHistoryRepository;
import in.clouthink.daas.sbb.attachment.repository.AttachmentRepository;
import in.clouthink.daas.sbb.attachment.service.AttachmentService;
import in.clouthink.daas.sbb.shared.domain.request.PageQueryRequest;
import in.clouthink.daas.fss.spi.FileObjectService;
import in.clouthink.daas.fss.spi.MutableFileObjectService;
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
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Autowired
	private AttachmentDownloadHistoryRepository attachmentDownloadHistoryRepository;

	@Autowired
	private FileObjectService fileObjectService;

	@Override
	public Page<Attachment> listAttachments(AttachmentQueryRequest request) {
		return attachmentRepository.queryPage(request);
	}

	@Override
	public Attachment findAttachmentById(String id) {
		return attachmentRepository.findById(id);
	}

	@Override
	public Attachment createAttachment(SaveAttachmentRequest request, User user) {
		checkSaveAttachmentRequest(request);

		Attachment attachment = new Attachment();
		attachment.setCategory(request.getCategory());
		attachment.setTitle(request.getTitle());
		attachment.setSummary(request.getSummary());
		attachment.setFileObjectId(request.getFileObjectId());
		attachment.setCreatedAt(new Date());
		attachment.setCreatedBy(user);
		return attachmentRepository.save(attachment);
	}

	@Override
	public void updateAttachment(String id, SaveAttachmentRequest request, User user) {
		checkSaveAttachmentRequest(request);

		Attachment attachment = attachmentRepository.findById(id);
		if (attachment == null) {
			throw new AttachmentNotFoundException(id);
		}
		attachment.setCategory(request.getCategory());
		attachment.setTitle(request.getTitle());
		attachment.setSummary(request.getSummary());
		attachment.setFileObjectId(request.getFileObjectId());
		attachment.setModifiedAt(new Date());
		attachment.setModifiedBy(user);
		attachmentRepository.save(attachment);
	}

	@Override
	public void deleteAttachment(String id, User user) {
		Attachment attachment = attachmentRepository.findById(id);
		if (attachment == null) {
			throw new AttachmentNotFoundException(id);
		}

		if (attachment.isPublished()) {
			throw new AttachmentException("不能删除已发布的附件!");
		}

		attachmentRepository.delete(attachment);
		if (!StringUtils.isEmpty(attachment.getFileObjectId())) {
			try {
				if (fileObjectService instanceof MutableFileObjectService) {
					((MutableFileObjectService) fileObjectService).deleteById(attachment.getFileObjectId());
				}
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	public void markAttachmentAsDownloaded(Attachment attachment, User user) {
		AttachmentDownloadHistory attachmentDownloadHistory = attachmentDownloadHistoryRepository.findByAttachmentAndDownloadedBy(
				attachment,
				user);
		if (attachmentDownloadHistory == null) {
			attachmentDownloadHistory = new AttachmentDownloadHistory();
			attachmentDownloadHistory.setAttachment(attachment);
			attachmentDownloadHistory.setDownloadedBy(user);
			attachmentDownloadHistory.setDownloadedAt(new Date());
		}
		attachmentDownloadHistory.setLatestDownloadedAt(new Date());
		attachmentDownloadHistoryRepository.save(attachmentDownloadHistory);
		int downloadCounter = attachmentDownloadHistoryRepository.countByAttachment(attachment);
		attachmentRepository.updateDownloadCounter(attachment.getId(), downloadCounter);
	}

	@Override
	public boolean isAttachmentDownloadedByUser(Attachment attachment, User user) {
		return attachmentDownloadHistoryRepository.findByAttachmentAndDownloadedBy(attachment, user) != null;
	}

	@Override
	public int countAttachmentDownloadHistory(Attachment attachment) {
		return attachmentDownloadHistoryRepository.countByAttachment(attachment);
	}

	@Override
	public void publishAttachment(String id, User user) {
		Attachment attachment = attachmentRepository.findById(id);
		if (attachment == null) {
			throw new AttachmentNotFoundException(id);
		}
		if (attachment.getFileObjectId() == null) {
			throw new AttachmentException("请上传附件后再发布!");
		}
		attachment.setPublished(true);
		attachment.setPublishedAt(new Date());
		attachment.setPublishedBy(user);
		attachmentRepository.save(attachment);
	}

	@Override
	public void unpublishAttachment(String id, User user) {
		Attachment attachment = attachmentRepository.findById(id);
		if (attachment == null) {
			throw new AttachmentNotFoundException(id);
		}
		attachment.setPublished(false);
		attachmentRepository.save(attachment);
	}

	@Override
	public Page<AttachmentDownloadHistory> listDownloadHistory(String id, PageQueryRequest queryRequest) {
		Attachment attachment = attachmentRepository.findById(id);
		if (attachment == null) {
			throw new AttachmentNotFoundException(id);
		}
		return attachmentDownloadHistoryRepository.findByAttachment(attachment,
																	new PageRequest(queryRequest.getStart(),
																					queryRequest.getLimit(),
																					new Sort(Sort.Direction.DESC,
																							 "latestDownloadedAt")));
	}

	private void checkSaveAttachmentRequest(SaveAttachmentRequest request) {
		if (StringUtils.isEmpty(request.getTitle())) {
			throw new AttachmentException("标题不能为空");
		}
	}
}
