package in.clouthink.daas.sbb.account.domain.model;

import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "SysUserRoleRelationships")
public class SysUserRoleRelationship extends StringIdentifier {

	@Indexed
	@DBRef
	private SysExtRole role;

	@Indexed
	@DBRef
	private SysUser user;

	private Date createdAt;

	public SysExtRole getRole() {
		return role;
	}

	public void setRole(SysExtRole role) {
		this.role = role;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
