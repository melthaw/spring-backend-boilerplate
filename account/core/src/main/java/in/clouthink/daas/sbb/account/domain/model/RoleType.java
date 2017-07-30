package in.clouthink.daas.sbb.account.domain.model;

/**
 * The role type def.
 *
 * @author dz
 */
public enum RoleType {

	//系统角色（内置,如果是spring security默认实现,则以ROLE_作为前缀）
	SYS_ROLE,

	//扩展角色（扩展角色,可自定义,是否需要前缀由provider决定）
	EXT_ROLE

}
