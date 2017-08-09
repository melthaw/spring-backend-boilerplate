package in.clouthink.daas.sbb.setting.domain.model;

import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 */
@Document(collection = "SystemSettings")
public class SystemSetting extends StringIdentifier {

	public static void onCreate(SystemSetting target, User byWho) {
		target.setCreatedAt(new Date());
		target.setCreatedBy(byWho);
		target.setModifiedAt(new Date());
		target.setModifiedBy(byWho);
	}

	public static void onModify(SystemSetting target, User byWho) {
		target.setModifiedAt(new Date());
		target.setModifiedBy(byWho);
	}

	private String name;

	private String contactEmail;

	private String contactPhone;

	private User createdBy;

	private Date createdAt;

	private User modifiedBy;

	private Date modifiedAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
}
