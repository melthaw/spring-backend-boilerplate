package in.clouthink.daas.sbb.attachment.rest.support.impl;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.domain.model.AttachmentDownloadHistory;
import in.clouthink.daas.sbb.attachment.domain.request.DownloadAttachmentEvent;
import in.clouthink.daas.sbb.attachment.event.DownloadAttachmentEventObject;
import in.clouthink.daas.sbb.attachment.service.AttachmentService;
import in.clouthink.daas.sbb.attachment.rest.dto.*;
import in.clouthink.daas.sbb.attachment.rest.support.AttachmentRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import in.clouthink.daas.sbb.shared.util.ImageUtils;
import in.clouthink.daas.edm.Edms;
import in.clouthink.daas.fss.core.*;
import in.clouthink.daas.fss.repackage.org.apache.commons.io.FilenameUtils;
import in.clouthink.daas.fss.rest.FileStorageRestSupport;
import in.clouthink.daas.fss.rest.UploadFileRequest;
import in.clouthink.daas.fss.spi.FileObjectService;
import in.clouthink.daas.fss.spi.FileStorageService;
import in.clouthink.daas.fss.util.HttpMultipartUtils;
import in.clouthink.daas.fss.util.IOUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class AttachmentRestSupportImpl implements AttachmentRestSupport, InitializingBean {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private FileObjectService fileObjectService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private FileStorageRestSupport fileStorageRestSupport;

	@Override
	public Page<AttachmentSummary> listAttachment(AttachmentQueryParameter queryRequest) {
		Page<Attachment> attachmentPage = attachmentService.listAttachments(queryRequest);
		return new PageImpl<>(attachmentPage.getContent()
											.stream()
											.map(AttachmentSummary::from)
											.collect(Collectors.toList()),
							  new PageRequest(queryRequest.getStart(), queryRequest.getLimit()),
							  attachmentPage.getTotalElements());
	}

	@Override
	public AttachmentDetail getAttachmentDetail(String id) {
		Attachment attachment = attachmentService.findAttachmentById(id);

		Object fileObject = null;
		return AttachmentDetail.from(attachment, fileObject);
	}

	@Override
	public String createAttachment(SaveAttachmentParameter request, User user) {
		return attachmentService.createAttachment(request, user).getId();
	}

	@Override
	public void updateAttachment(String id, SaveAttachmentParameter request, User user) {
		attachmentService.updateAttachment(id, request, user);
	}

	@Override
	public void deleteAttachment(String id, User user) {
		attachmentService.deleteAttachment(id, user);
	}

	@Override
	public void publishAttachment(String id, User user) {
		attachmentService.publishAttachment(id, user);
	}

	@Override
	public void unpublishAttachment(String id, User user) {
		attachmentService.unpublishAttachment(id, user);
	}

	@Override
	public Page<DownloadSummary> listDownloadHistory(String id, PageQueryParameter queryParameter) {
		Page<AttachmentDownloadHistory> downloadHistoryPage = attachmentService.listDownloadHistory(id, queryParameter);
		return new PageImpl<>(downloadHistoryPage.getContent()
												 .stream()
												 .map(DownloadSummary::from)
												 .collect(Collectors.toList()),
							  new PageRequest(queryParameter.getStart(), queryParameter.getLimit()),
							  downloadHistoryPage.getTotalElements());
	}

	@Override
	public void downloadAttachment(String id, User user, HttpServletResponse response) throws IOException {
		Attachment attachment = attachmentService.findAttachmentById(id);
		if (attachment == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		if (StringUtils.isEmpty(attachment.getFileObjectId())) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Edms.getEdm("attachment")
			.dispatch(DownloadAttachmentEvent.EVENT_NAME, new DownloadAttachmentEventObject(attachment, user));
		String filename = attachment.getTitle();
//		if (StringUtils.isEmpty(filename)) {
			fileStorageRestSupport.downloadById(attachment.getFileObjectId(), response);
//		}
//		else {
//			fileStorageRestSupport.downloadByFilename(filename, response);
//		}
	}

	@Override
	public FileObject uploadAvatar(UploadFileRequest uploadFileRequest,
								   HttpServletRequest request,
								   HttpServletResponse response) throws IOException {
		validate(uploadFileRequest);

		MultipartFile multipartFile = HttpMultipartUtils.resolveMultipartFile(request);
		if (multipartFile == null || multipartFile.isEmpty()) {
			throw new FileStorageException("The multipart of http request is required.");
		}

		FileStorageRequest fileStorageRequest = buildFileStorageRequest(multipartFile, uploadFileRequest);

		File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
		multipartFile.transferTo(tempFile);
		File resizedTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");

		ImageUtils.zoomBySquare(tempFile, resizedTempFile, 128, true);

		return doUpload(fileStorageRequest, resizedTempFile);
	}

	private FileObject doUpload(FileStorageRequest fileStorageRequest, File resizedTempFile)
			throws FileNotFoundException {
		InputStream is = new FileInputStream(resizedTempFile);
		try {
			FileStorage fileStorage = fileStorageService.store(is, fileStorageRequest);
			return fileStorage.getFileObject();
		}
		finally {
			IOUtils.close(is);
			FileUtils.deleteQuietly(resizedTempFile);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(fileObjectService);
		Assert.notNull(fileStorageService);
		Assert.notNull(fileStorageRestSupport);
	}

	//#################################################
	// private method
	//#################################################

	private void validate(UploadFileRequest uploadFileRequest) {
		if (StringUtils.isEmpty(uploadFileRequest.getName())) {
			throw new FileStorageException("上传附件的名字不能为空.");
		}
		if (StringUtils.isEmpty(uploadFileRequest.getUploadedBy())) {
			throw new FileStorageException("上传附件的用户不能为空.");
		}
	}


	private FileStorageRequest buildFileStorageRequest(MultipartFile multipartFile,
													   UploadFileRequest uploadFileRequest) {
		DefaultFileStorageRequest result = new DefaultFileStorageRequest();
		result.setCategory(uploadFileRequest.getCategory());
		result.setCode(uploadFileRequest.getCode());
		result.setName(uploadFileRequest.getName());
		result.setDescription(uploadFileRequest.getDescription());
		result.setBizId(uploadFileRequest.getBizId());
		result.setUploadedBy(uploadFileRequest.getUploadedBy());
		result.setAttributes(uploadFileRequest.getAttributes());

		String originalFileName = multipartFile.getOriginalFilename();
		result.setOriginalFilename(originalFileName);

		String prettyFilename = uploadFileRequest.getPrettyFilename();
		if (StringUtils.isEmpty(prettyFilename)) {
			prettyFilename = uploadFileRequest.getName();
		}

		if (StringUtils.isEmpty(prettyFilename)) {
			prettyFilename = originalFileName;
		}
		else {
			String originalSuffix = FilenameUtils.getExtension(originalFileName);
			String prettySuffix = FilenameUtils.getExtension(prettyFilename);
			if (originalSuffix != null) {
				if (prettySuffix == null || !prettySuffix.equalsIgnoreCase(originalSuffix)) {
					prettyFilename += '.' + originalSuffix;
				}
			}
		}
		result.setPrettyFilename(prettyFilename);

		String contentType = uploadFileRequest.getContentType();
		if (StringUtils.isEmpty(contentType)) {
			// Fixed ContentType from IE
			contentType = multipartFile.getContentType();
			if ("image/pjpeg".equals(contentType) || "image/jpg".equals(contentType)) {
				contentType = "image/jpeg";
			}
			else if ("image/x-png".equals(contentType)) {
				contentType = "image/png";
			}
		}
		result.setContentType(contentType);
		result.setSize(multipartFile.getSize());

		return result;
	}

}
