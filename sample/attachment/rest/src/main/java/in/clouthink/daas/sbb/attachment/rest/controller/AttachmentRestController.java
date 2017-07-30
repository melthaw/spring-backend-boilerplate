package in.clouthink.daas.sbb.attachment.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.attachment.rest.dto.*;
import in.clouthink.daas.sbb.attachment.rest.support.AttachmentRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import in.clouthink.daas.fss.core.FileObject;
import in.clouthink.daas.fss.rest.UploadFileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api("附件发布管理")
@RestController
@RequestMapping("/api")
public class AttachmentRestController {

	@Autowired
	private AttachmentRestSupport attachmentsRestSupport;

	/**
	 * 头像特殊处理,上传后,对头像进行crop和zoom操作
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/attachments/avatar", method = RequestMethod.POST)
	@ResponseBody
	public String uploadAvatar(UploadFileRequest uploadFileRequest,
							   HttpServletRequest request,
							   HttpServletResponse response) throws IOException {
		User user = (User) SecurityContexts.getContext().requireUser();
		uploadFileRequest.setUploadedBy(user.getUsername());
		FileObject fileObject = attachmentsRestSupport.uploadAvatar(uploadFileRequest, request, response);

		return fileObject.getId();
	}

	@ApiOperation(value = "附件列表,支持分页,支持动态查询(按名称,分类查询)")
	@RequestMapping(value = "/attachments", method = RequestMethod.GET)
	public Page<AttachmentSummary> listAttachmentSummaryPage(AttachmentQueryParameter queryRequest) {
		return attachmentsRestSupport.listAttachment(queryRequest);
	}

	@ApiOperation(value = "查看附件详情")
	@RequestMapping(value = "/attachments/{id}", method = RequestMethod.GET)
	public AttachmentDetail getAttachmentDetail(@PathVariable String id) {
		return attachmentsRestSupport.getAttachmentDetail(id);
	}

	@ApiOperation(value = "创建附件（前提已经调用daas-fss上传文件得到文件的metadata,然后和业务数据放到一起）")
	@RequestMapping(value = "/attachments", method = RequestMethod.POST)
	public String createAttachment(@RequestBody SaveAttachmentParameter request) {
		User user = (User) SecurityContexts.getContext().requireUser();
		return attachmentsRestSupport.createAttachment(request, user);
	}

	@ApiOperation(value = "修改附件信息（名称,分类等）,已发布的附件不能修改")
	@RequestMapping(value = "/attachments/{id}", method = RequestMethod.POST)
	public void updateNew(@PathVariable String id, @RequestBody SaveAttachmentParameter request) {
		User user = (User) SecurityContexts.getContext().requireUser();
		attachmentsRestSupport.updateAttachment(id, request, user);
	}

	@ApiOperation(value = "删除附件（已发布的附件不能删除）")
	@RequestMapping(value = "/attachments/{id}", method = RequestMethod.DELETE)
	public void deleteAttachment(@PathVariable String id) {
		User user = (User) SecurityContexts.getContext().requireUser();
		attachmentsRestSupport.deleteAttachment(id, user);
	}

	@ApiOperation(value = "发布附件（重复发布自动忽略）")
	@RequestMapping(value = "/attachments/{id}/publish", method = RequestMethod.POST)
	public void publishAttachment(@PathVariable String id) {
		User user = (User) SecurityContexts.getContext().requireUser();
		attachmentsRestSupport.publishAttachment(id, user);
	}

	@ApiOperation(value = "取消发布附件（重复取消自动忽略）")
	@RequestMapping(value = "/attachments/{id}/unpublish", method = RequestMethod.POST)
	public void unpublishAttachment(@PathVariable String id) {
		User user = (User) SecurityContexts.getContext().requireUser();
		attachmentsRestSupport.unpublishAttachment(id, user);
	}

	@ApiOperation(value = "下载附件")
	@RequestMapping(value = {"/attachments/{id}/download"}, method = RequestMethod.GET)
	public void downloadAttachment(@PathVariable String id, HttpServletResponse response) throws IOException {
		User user = (User) SecurityContexts.getContext().requireUser();
		attachmentsRestSupport.downloadAttachment(id, user, response);
	}

	@ApiOperation(value = "查看附件的下载历史记录")
	@RequestMapping(value = "/attachments/{id}/downloadHistory", method = RequestMethod.GET)
	public Page<DownloadSummary> listDownloadHistory(@PathVariable String id, PageQueryParameter queryParameter) {
		return attachmentsRestSupport.listDownloadHistory(id, queryParameter);
	}

}
