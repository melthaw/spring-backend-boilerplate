package in.clouthink.daas.sbb.account.domain.model;

/**
 * The role type def.
 *
 * @author dz
 */
public enum RoleType {

	//系统角色（系统内置角色,不可修改,如果是spring security默认实现,则以ROLE_作为前缀）
	SYS_ROLE,

	//应用角色（应用扩展角色,用户可自定义,是否需要前缀由provider决定）
	APP_ROLE

}
