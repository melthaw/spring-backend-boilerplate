package in.clouthink.daas.sbb.setting.rest.dto;

import in.clouthink.daas.sbb.setting.domain.model.SystemSetting;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author dz
 */
public class SystemSettingSummary {

	public static void convert(SystemSetting fromObj, SystemSettingSummary result) {
		BeanUtils.copyProperties(fromObj, result);

		if (fromObj.getCreatedBy() != null) {
			result.setCreatedById(fromObj.getCreatedBy().getId());
			result.setCreatedByName(fromObj.getCreatedBy().getUsername());
		}
		result.setCreatedAt(fromObj.getCreatedAt());

		if (fromObj.getModifiedBy() != null) {
			result.setModifiedById(fromObj.getModifiedBy().getId());
			result.setModifiedByName(fromObj.getModifiedBy().getUsername());
		}
		result.setModifiedAt(fromObj.getModifiedAt());

	}

	public static SystemSettingSummary from(SystemSetting fromObj) {
		if (fromObj == null) {
			return null;
		}
		SystemSettingSummary result = new SystemSettingSummary();
		convert(fromObj, result);
		return result;
	}

	private String createdById;
	private String createdByName;
	private Date createdAt;

	private String modifiedById;
	private String modifiedByName;
	private Date modifiedAt;


	private String name;

	private String fileObjectId;

	private String contactEmail;

	private String contactPhone;

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileObjectId() {
		return fileObjectId;
	}

	public void setFileObjectId(String fileObjectId) {
		this.fileObjectId = fileObjectId;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}
