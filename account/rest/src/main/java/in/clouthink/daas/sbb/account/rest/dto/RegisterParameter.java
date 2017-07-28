package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.clouthink.daas.sbb.account.domain.model.Gender;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * @author dz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class RegisterParameter {

	private String cellphone;

	private String displayName;

	private String passcode;

	private String password;

	private Gender gender;

	private String province;

	private String city;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	private boolean helpOtherEnabled;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isHelpOtherEnabled() {
		return helpOtherEnabled;
	}

	public void setHelpOtherEnabled(boolean helpOtherEnabled) {
		this.helpOtherEnabled = helpOtherEnabled;
	}

}
