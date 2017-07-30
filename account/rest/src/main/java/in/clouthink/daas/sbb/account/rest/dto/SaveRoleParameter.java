package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.clouthink.daas.sbb.account.domain.request.SaveRoleRequest;
import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("保存角色申请")
public class SaveRoleParameter implements SaveRoleRequest {

	private String code;

	private String name;

	private String description;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null) {
			name = name.trim();
		}
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if (code != null) {
			code = code.trim().toUpperCase();
		}
		this.code = code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
