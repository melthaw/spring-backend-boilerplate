package in.clouthink.daas.sbb.account.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户头像")
public class AvatarSummary {

	@ApiModelProperty("头像对应的图片存储的id")
	private String id;

	@ApiModelProperty("头像对应的图片链接的url")
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
