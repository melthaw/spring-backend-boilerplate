package in.clouthink.daas.sbb.rbac.rest.controller;

import in.clouthink.daas.sbb.rbac.impl.model.TypedRole;
import in.clouthink.daas.sbb.rbac.rest.dto.GrantResourceParameter;
import in.clouthink.daas.sbb.rbac.rest.dto.PrivilegedResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.support.PermissionRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("授权管理")
@RestController
@RequestMapping("/api/permissions")
public class PermissionRestController {

	@Autowired
	private PermissionRestSupport permissionRestSupport;

	@ApiOperation(value = "获取指定角色的授权资源(完整资源树,通过授权标记位区分是否已授权)")
	@RequestMapping(value = "roles/{typedRoleCode}/resources", method = RequestMethod.GET)
	public List<PrivilegedResourceWithChildren> listGrantedResources(@ApiParam(name = "typedRoleCode", value = "format: [enum RoleType:Role#code]") @PathVariable String typedRoleCode) {
		return permissionRestSupport.listGrantedResources(typedRoleCode);
	}

	@ApiOperation(value = "获取授权的角色列表")
	@RequestMapping(value = "resources/{resourceCode}/roles", method = RequestMethod.GET)
	public List<TypedRole> listGrantedRoles(@PathVariable String resourceCode) {
		return permissionRestSupport.listGrantedRoles(resourceCode);
	}

	@ApiOperation(value = "给指定的资源授权")
	@RequestMapping(value = "/roles/{typedRoleCode}/resources", method = RequestMethod.POST)
	public void grantResourcesToRoles(@ApiParam(name = "typedRoleCode", value = "format: [enum RoleType:Role#code]") @PathVariable String typedRoleCode,
									  @RequestBody GrantResourceParameter grantResourceParameter) {
		permissionRestSupport.grantResourcesToRole(typedRoleCode, grantResourceParameter);
	}

	@ApiOperation(value = "取消指定的资源授权(角色编码以逗号区分,角色编码格式'TYPE:ROLE_CODE',其中TYPE的值为SYS或者APP")
	@RequestMapping(value = "/roles/{typedRoleCode}/resources/{resourceCode}", method = RequestMethod.DELETE)
	public void revokeResourcesFromRoles(@ApiParam(name = "typedRoleCode", value = "format: [enum RoleType:Role#code]") @PathVariable String typedRoleCode,
										 @PathVariable String resourceCode) {
		permissionRestSupport.revokeResourcesFromRole(typedRoleCode, resourceCode);
	}

}
