package in.clouthink.daas.sbb.shared.domain.request.impl;


import in.clouthink.daas.sbb.shared.domain.request.DateRangedQueryRequest;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
public class DateRangedQueryParameter extends PageQueryParameter implements DateRangedQueryRequest {

	private Date beginDate;

	private Date endDate;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
