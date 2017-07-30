package in.clouthink.daas.sbb.storage.dto;

import in.clouthink.daas.fss.mongodb.repository.FileObjectQueryParameter;

import java.util.Date;

/**
 */
public class DefaultFileObjectQueryParameter implements FileObjectQueryParameter {

	private int start = 0;

	private int limit = 20;

	private String category;

	private String code;

	private String bizId;

	private String uploadedBy;

	private Date uploadedAtFrom;

	private Date uploadedAtTo;

	private String sortedBy;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	@Override
	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	@Override
	public Date getUploadedAtFrom() {
		return uploadedAtFrom;
	}

	public void setUploadedAtFrom(Date uploadedAtFrom) {
		this.uploadedAtFrom = uploadedAtFrom;
	}

	@Override
	public Date getUploadedAtTo() {
		return uploadedAtTo;
	}

	public void setUploadedAtTo(Date uploadedAtTo) {
		this.uploadedAtTo = uploadedAtTo;
	}

	public String getSortedBy() {
		return sortedBy;
	}

	public void setSortedBy(String sortedBy) {
		this.sortedBy = sortedBy;
	}
}
