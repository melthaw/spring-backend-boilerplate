package in.clouthink.daas.sbb.passcode.model;

import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author dz
 */
@Document(collection = "Passcodes")
@CompoundIndexes({@CompoundIndex(name = "ci_passcode_cellphone_category", def = "{'cellphone': 1, 'category': 1}")})
public class Passcode extends StringIdentifier {

	private String cellphone;

	private String category;

	private String passcode;

	private long whenToExpire;

	private long whenToNew;

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

	public long getWhenToExpire() {
		return whenToExpire;
	}

	public void setWhenToExpire(long whenToExpire) {
		this.whenToExpire = whenToExpire;
	}

	public long getWhenToNew() {
		return whenToNew;
	}

	public void setWhenToNew(long whenToNew) {
		this.whenToNew = whenToNew;
	}

}
