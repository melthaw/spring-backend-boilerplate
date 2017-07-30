package in.clouthink.daas.sbb.account.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("保存排序申请")
public class SaveSortParameter {

	private String sorting;

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
}
