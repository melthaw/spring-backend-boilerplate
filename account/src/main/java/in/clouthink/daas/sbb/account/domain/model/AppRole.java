package in.clouthink.daas.sbb.account.domain.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 系统内置角色
 *
 * @author dz
 */
public enum AppRole implements GrantedAuthority {

	// 应用 - 普通用户
	ROLE_USER("普通用户") {
		@Override
		public String getAuthority() {
			return "ROLE_USER";
		}
	},
	// 应用 - 运营用户
	ROLE_OPERATION("运营用户") {
		@Override
		public String getAuthority() {
			return "ROLE_OPERATION";
		}
	},
	// 应用 - 志愿者
	ROLE_VOLUNTEER("志愿者") {
		@Override
		public String getAuthority() {
			return "ROLE_VOLUNTEER";
		}
	};

	private final String displayName;

	AppRole(String value) {
		displayName = value;
	}

	public String getCode() {
		return name();
	}

	public String getDisplayName() {
		return displayName;
	}

}
