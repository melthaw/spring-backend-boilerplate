package in.clouthink.daas.sbb.news.rest.controller;

import in.clouthink.daas.sbb.news.rest.support.NewsRestSupport;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.news.rest.dto.*;
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
@Api("新闻发布管理")
@RestController
@RequestMapping("/api")
public class NewsRestController {

	@Autowired
	private NewsRestSupport newsRestSupport;

	@ApiOperation(value = "新闻列表,支持分页,支持动态查询(按名称,分类查询)")
	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public Page<NewsSummary> listNewsSummaryPage(NewsQueryParameter queryRequest) {
		return newsRestSupport.listNews(queryRequest);
	}

	@ApiOperation(value = "查看新闻详情")
	@RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
	public NewsDetail getNewsDetail(@PathVariable String id) {
		return newsRestSupport.getNewsDetail(id);
	}

	@ApiOperation(value = "创建新闻")
	@RequestMapping(value = "/news", method = RequestMethod.POST)
	public String createNews(@RequestBody SaveNewsParameter request) {
		User user = (User)SecurityContexts.getContext().requireUser();
		return newsRestSupport.createNews(request, user);
	}

	@ApiOperation(value = "修改新闻（已发布的不能修改）")
	@RequestMapping(value = "/news/{id}", method = RequestMethod.POST)
	public void updateNew(@PathVariable String id, @RequestBody SaveNewsParameter request) {
		User user = (User) SecurityContexts.getContext().requireUser();
		newsRestSupport.updateNews(id, request, user);
	}

	@ApiOperation(value = "删除新闻（已发布的不能删除）")
	@RequestMapping(value = "/news/{id}", method = RequestMethod.DELETE)
	public void deleteNews(@PathVariable String id) {
		User user = (User) SecurityContexts.getContext().requireUser();
		newsRestSupport.deleteNews(id, user);
	}

	@ApiOperation(value = "发布新闻（重复发布自动忽略）")
	@RequestMapping(value = "/news/{id}/publish", method = RequestMethod.POST)
	public void publishNews(@PathVariable String id) {
		User user = (User) SecurityContexts.getContext().requireUser();
		newsRestSupport.publishNews(id, user);
	}

	@ApiOperation(value = "取消发布新闻（重复取消自动忽略）")
	@RequestMapping(value = "/news/{id}/unpublish", method = RequestMethod.POST)
	public void unpublishNews(@PathVariable String id) {
		User user = (User) SecurityContexts.getContext().requireUser();
		newsRestSupport.unpublishNews(id, user);
	}

	@ApiOperation(value = "查看新闻的阅读历史记录")
	@RequestMapping(value = "/news/{id}/readHistory", method = RequestMethod.GET)
	public Page<ReadSummary> listReadHistory(@PathVariable String id, PageQueryParameter queryParameter) {
		return newsRestSupport.listReadHistory(id, queryParameter);
	}

	@ApiOperation(value = "删除新闻附件")
	@RequestMapping(value = "/news/{id}/files/{fileId}", method = RequestMethod.DELETE)
	public void deletePaperAttachment(@PathVariable String id, @PathVariable String fileId) {
		User user = (User) SecurityContexts.getContext().requireUser();
		newsRestSupport.deleteAttachment(id, fileId, user);
	}

}
