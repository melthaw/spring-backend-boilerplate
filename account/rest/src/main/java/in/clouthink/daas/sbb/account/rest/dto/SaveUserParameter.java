package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.clouthink.daas.sbb.account.domain.model.Gender;
import in.clouthink.daas.sbb.account.domain.request.SaveUserRequest;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("保存用户信息")
public class SaveUserParameter implements SaveUserRequest {

	private String cellphone;

	private String username;

	private String password;

	private String displayName;

	private String province;

	private String city;

	private String email;

	private Gender gender;

	private String avatarId;

	private String avatarUrl;

	private String signature;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	@Override
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
