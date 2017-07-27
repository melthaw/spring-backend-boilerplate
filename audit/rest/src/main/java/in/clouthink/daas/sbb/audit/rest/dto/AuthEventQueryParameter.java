package in.clouthink.daas.sbb.audit.rest.dto;


import in.clouthink.daas.sbb.audit.domain.request.AuthEventQueryRequest;
import in.clouthink.daas.sbb.shared.domain.request.impl.DateRangedQueryParameter;
import io.swagger.annotations.ApiModel;

@ApiModel
public class AuthEventQueryParameter extends DateRangedQueryParameter implements AuthEventQueryRequest {

	private String realm;

	private String username;

	private Boolean succeed;

	@Override
	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Boolean getSucceed() {
		return succeed;
	}

	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}
}
