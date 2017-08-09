package in.clouthink.daas.sbb.account.domain.model;

import in.clouthink.daas.sbb.shared.domain.model.StringIdentifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

/**
 * 应用扩展角色,是系统内置角色（Role）的补充
 */
@Document(collection = "AppRoles")
public class AppRole extends StringIdentifier implements GrantedAuthority {

	//扩展角色不能使用以下编码（内置角色已经占用）
	public static String SYS_ROLE_NAME_ADMIN = "ADMIN";
	public static String SYS_ROLE_NAME_MGR = "MGR";
	public static String SYS_ROLE_NAME_USER = "USER";

	/**
	 * 不能使用系统角色编码
	 *
	 * @param roleCode
	 * @return
	 */
	public static final boolean isIllegal(String roleCode) {
		return SYS_ROLE_NAME_ADMIN.equalsIgnoreCase(roleCode) ||
			   SYS_ROLE_NAME_MGR.equalsIgnoreCase(roleCode) ||
			   SYS_ROLE_NAME_USER.equalsIgnoreCase(roleCode);
	}

	@Indexed(unique = true)
	private String code;

	@Indexed(unique = true)
	private String name;

	private String description;

	private Date createdAt;

	private Date modifiedAt;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String getAuthority() {
		return getCode();
	}
}

