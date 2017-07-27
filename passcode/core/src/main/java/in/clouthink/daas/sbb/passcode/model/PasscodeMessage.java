package in.clouthink.daas.sbb.passcode.model;

/**
 * @author dz
 */
public class PasscodeMessage {

	private String cellphone;

	private String category;

	private String passcode;

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

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

}
