package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.clouthink.daas.sbb.account.domain.model.Gender;
import in.clouthink.daas.sbb.account.domain.request.ChangeUserProfileRequest;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("修改当前用户资料")
public class ChangeMyProfileParameter implements ChangeUserProfileRequest {

	private String displayName;

	private String cellphone;

	private String email;

	private Gender gender;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	private String province;

	private String city;

	private String signature;

	@Override
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
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
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
