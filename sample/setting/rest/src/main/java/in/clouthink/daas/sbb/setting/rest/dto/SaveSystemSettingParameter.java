package in.clouthink.daas.sbb.setting.rest.dto;


import in.clouthink.daas.sbb.setting.domain.request.SaveSystemSettingRequest;

/**
 * @author dz
 */
public class SaveSystemSettingParameter implements SaveSystemSettingRequest {

	private String name;

	private String contactEmail;

	private String contactPhone;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Override
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}
