package in.clouthink.daas.sbb.account.domain.model;

import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "UserRoleRelationships")
public class UserRoleRelationship extends StringIdentifier {

	@Indexed
	@DBRef
	private ExtRole role;

	@Indexed
	@DBRef
	private User user;

	private Date createdAt;

	public ExtRole getRole() {
		return role;
	}

	public void setRole(ExtRole role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
