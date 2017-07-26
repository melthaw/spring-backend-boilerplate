package in.clouthink.daas.sbb.account.domain.model;

/**
 * @author dz
 */
public class PasscodeRequest {

	private String cellphone;

	private String category;

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

}
