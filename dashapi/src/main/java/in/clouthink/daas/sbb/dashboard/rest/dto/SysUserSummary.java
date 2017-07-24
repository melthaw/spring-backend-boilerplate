package in.clouthink.daas.sbb.dashboard.rest.dto;

import in.clouthink.daas.sbb.account.domain.model.Gender;
import in.clouthink.daas.sbb.account.domain.model.SysUser;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 *
 */
@ApiModel
public class SysUserSummary {

	static void convert(SysUser user, SysUserSummary result) {
		BeanUtils.copyProperties(user, result, "expired", "locked");
	}

	public static SysUserSummary from(SysUser user) {
		if (user == null) {
			return null;
		}
		SysUserSummary result = new SysUserSummary();
		convert(user, result);
		return result;
	}

	private String id;

	private String username;

	private String cellphone;

	private String email;

	private Gender gender;

	private Date birthday;

	private boolean enabled;

	private Date createdAt;

	private Date deletedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
}
