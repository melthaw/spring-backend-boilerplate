package in.clouthink.daas.sbb.news.rest.controller;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.news.rest.dto.*;
import in.clouthink.daas.sbb.news.rest.support.NoticeRestSupport;
import in.clouthink.daas.sbb.security.SecurityContexts;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Api("通知发布管理")
@RestController
@RequestMapping("/api")
public class NoticeRestController {

	@Autowired
	private NoticeRestSupport noticeRestSupport;

	@ApiOperation(value = "通知列表,支持分页,支持动态查询(按名称,分类查询)")
	@RequestMapping(value = "/notices", method = RequestMethod.GET)
	public Page<NoticeSummary> listNoticeSummaryPage(NoticeQueryParameter queryRequest) {
		return noticeRestSupport.listNotice(queryRequest);
	}

	@ApiOperation(value = "查看通知详情")
	@RequestMapping(value = "/notices/{id}", method = RequestMethod.GET)
	public NoticeDetail getNoticeDetail(@PathVariable String id) {
		return noticeRestSupport.getNoticeDetail(id);
	}

	@ApiOperation(value = "创建通知")
	@RequestMapping(value = "/notices", method = RequestMethod.POST)
	public String createNotice(@RequestBody SaveNoticeParameter request) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		return noticeRestSupport.createNotice(request, user);
	}

	@ApiOperation(value = "修改通知（已发布的不能修改）")
	@RequestMapping(value = "/notices/{id}", method = RequestMethod.POST)
	public void updateNew(@PathVariable String id, @RequestBody SaveNoticeParameter request) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		noticeRestSupport.updateNotice(id, request, user);
	}

	@ApiOperation(value = "删除通知（已发布的不能删除）")
	@RequestMapping(value = "/notices/{id}", method = RequestMethod.DELETE)
	public void deleteNotice(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		noticeRestSupport.deleteNotice(id, user);
	}

	@ApiOperation(value = "发布通知（重复发布自动忽略）")
	@RequestMapping(value = "/notices/{id}/publish", method = RequestMethod.POST)
	public void publishNotice(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		noticeRestSupport.publishNotice(id, user);
	}

	@ApiOperation(value = "取消发布通知（重复取消自动忽略）")
	@RequestMapping(value = "/notices/{id}/unpublish", method = RequestMethod.POST)
	public void unpublishNotice(@PathVariable String id) {
		SysUser user = (SysUser) SecurityContexts.getContext().requireUser();
		noticeRestSupport.unpublishNotice(id, user);
	}

	@ApiOperation(value = "查看通知的阅读历史记录")
	@RequestMapping(value = "/notices/{id}/readHistory", method = RequestMethod.GET)
	public Page<ReadSummary> listReadHistory(@PathVariable String id, PageQueryParameter queryParameter) {
		return noticeRestSupport.listReadHistory(id, queryParameter);
	}

}
