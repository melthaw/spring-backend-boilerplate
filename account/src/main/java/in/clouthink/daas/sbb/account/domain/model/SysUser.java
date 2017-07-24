package in.clouthink.daas.sbb.account.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document(collection = "SysUsers")
public class SysUser extends StringIdentifier implements UserDetails {

	public static final String ADMINISTRATOR = "administrator";

	public static boolean isAdministrator(AppUser appUser) {
		return ADMINISTRATOR.equals(appUser.getUsername());
	}

	// 用户帐号名
	@Indexed(unique = true)
	private String username;

	// 联系电话
	@Indexed(unique = true)
	private String cellphone;

	// 电子邮箱
	@Indexed(unique = true)
	private String email;

	// 显示名
	private String displayName;

	private Gender gender;

	private Date birthday;

	@JsonIgnore
	private String password;

	private boolean expired = false;

	private boolean locked = false;

	private boolean enabled = true;

	private boolean deleted = false;

	private Date deletedAt;

	@Transient
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	private List<SysRole> roles = new ArrayList<SysRole>();

	private Date createdAt;

	private Date modifiedAt;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username != null) {
			username = username.trim().toLowerCase();
		}
		this.username = username;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		if (cellphone != null) {
			cellphone = cellphone.trim();
		}
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null) {
			email = email.trim();
		}
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public void addRole(SysRole role) {
		if (this.roles.contains(role)) {
			return;
		}
		this.roles.add(role);
	}

	public void removeRole(SysRole role) {
		this.roles.remove(role);
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !expired;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

}
