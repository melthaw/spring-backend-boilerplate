package in.clouthink.daas.sbb.account.rest.dto;

import in.clouthink.daas.sbb.account.domain.model.Gender;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.shared.util.DateTimeUtils;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@ApiModel("用户摘要信息")
public class UserSummary {

	static void convert(User user, UserSummary result) {
		BeanUtils.copyProperties(user, result, "expired", "locked");
		if (user.getBirthday() != null) {
			result.setAge(DateTimeUtils.howOldAreYou(user.getBirthday()));
		}
	}

	public static UserSummary from(User user) {
		if (user == null) {
			return null;
		}
		UserSummary result = new UserSummary();
		convert(user, result);
		return result;
	}

	private String id;

	private String displayName;

	private String cellphone;

	private String username;

	private String avatarId;

	private String avatarUrl;

	private Gender gender;

	private Date birthday;

	private Integer age;

	private String province;

	private String city;

	private String signature;

	private boolean enabled;

	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
