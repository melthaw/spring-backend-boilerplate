package in.clouthink.daas.sbb.account.domain.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 系统内置角色
 */
public enum SysRole implements GrantedAuthority {

	// 系统 - 普通用户
	ROLE_USER("普通用户") {
		@Override
		public String getAuthority() {
			return "ROLE_USER";
		}
	},
	// 系统 - 管理员
	ROLE_MGR("管理员") {
		@Override
		public String getAuthority() {
			return "ROLE_MGR";
		}
	},
	// 系统 - 超级管理员
	ROLE_ADMIN("超级管理员") {
		@Override
		public String getAuthority() {
			return "ROLE_ADMIN";
		}
	};

	private final String displayName;

	SysRole(String value) {
		displayName = value;
	}

	public String getCode() {
		return name();
	}

	public String getDisplayName() {
		return displayName;
	}

}
