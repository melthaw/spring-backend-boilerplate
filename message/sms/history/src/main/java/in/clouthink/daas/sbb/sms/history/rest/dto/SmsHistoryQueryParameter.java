package in.clouthink.daas.sbb.sms.history.rest.dto;


import in.clouthink.daas.sbb.shared.domain.request.impl.DateRangedQueryParameter;
import in.clouthink.daas.sbb.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.sms.history.domain.request.SmsHistoryQueryRequest;
import io.swagger.annotations.ApiModel;

@ApiModel
public class SmsHistoryQueryParameter extends DateRangedQueryParameter implements SmsHistoryQueryRequest {

	private String cellphone;

	private String category;

	private SmsHistory.SmsStatus status;

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public SmsHistory.SmsStatus getStatus() {
		return status;
	}

	public void setStatus(SmsHistory.SmsStatus status) {
		this.status = status;
	}
}
