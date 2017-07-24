package in.clouthink.daas.sbb.account.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document(collection = "AppUsers")
@CompoundIndexes({@CompoundIndex(name = "user_username_usertype", def = "{username : 1, userType : 1}")})
public class AppUser extends StringIdentifier implements UserDetails {

	public static boolean hasRole(AppUser appUser, AppRole appRole) {
		return (appUser.getRoles().contains(appRole) || appUser.getAuthorities().contains(appRole));
	}

	// 用户帐号名 (same as cellphone)
	@Indexed(unique = true)
	private String username;

	// 用户帐号名
	@Indexed(unique = true)
	private String cellphone;

	// 显示名
	@Indexed
	private String displayName;

	// 电子邮箱
	//	@Indexed(unique = true)
	private String email;

	// 头像对应的附件图片id
	private String avatarId;

	// 头像对应的附件图片url
	private String avatarUrl;

	private Gender gender;

	private String province;

	private String city;

	private String signature;

	private Date birthday;

	@JsonIgnore
	private String password;

	private boolean hideUserType;

	private boolean expired = false;

	private boolean locked = false;

	private boolean enabled = true;

	private boolean deleted = false;

	private List<String> tags = new ArrayList<>();

	@Transient
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	private List<AppRole> roles = new ArrayList<AppRole>();

	private Date createdAt;

	private Date modifiedAt;

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public boolean isHideUserType() {
		return hideUserType;
	}

	public void setHideUserType(boolean hideUserType) {
		this.hideUserType = hideUserType;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(List<AppRole> roles) {
		this.roles = roles;
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

	public void addRole(AppRole role) {
		if (this.roles.contains(role)) {
			return;
		}
		this.roles.add(role);
	}

	public void removeRole(AppRole role) {
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
