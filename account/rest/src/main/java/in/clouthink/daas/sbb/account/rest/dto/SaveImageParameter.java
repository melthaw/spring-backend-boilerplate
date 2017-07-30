package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.clouthink.daas.sbb.account.domain.request.SaveImageRequest;
import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("保存图片申请")
public class SaveImageParameter implements SaveImageRequest {

	private String id;

	private String url;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
