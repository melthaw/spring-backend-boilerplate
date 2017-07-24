package in.clouthink.daas.sbb.shared.domain.request.impl;

import in.clouthink.daas.sbb.shared.domain.request.PublishableQueryRequest;
import io.swagger.annotations.ApiModel;

@ApiModel
public class PublishableQueryParameter extends PageQueryParameter implements PublishableQueryRequest {

	private Boolean published;

	public PublishableQueryParameter() {
	}

	public PublishableQueryParameter(int start, int limit) {
		super(start, limit);
	}

	@Override
	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

}
