package in.clouthink.daas.sbb.rbac.rest.controller;

import in.clouthink.daas.sbb.rbac.rest.dto.GrantResourceParameter;
import in.clouthink.daas.sbb.rbac.rest.dto.ResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.dto.TypedRoleSummary;
import in.clouthink.daas.sbb.rbac.rest.support.PermissionRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	@RequestMapping(value = "roles/{roleCode}/resources", method = RequestMethod.GET)
	public List<ResourceWithChildren> listGrantedResources(@PathVariable String roleCode) {
		return permissionRestSupport.listGrantedResources(roleCode);
	}

	@ApiOperation(value = "获取授权的角色列表")
	@RequestMapping(value = "resources/{resourceCode}/roles", method = RequestMethod.GET)
	public List<TypedRoleSummary> listGrantedRoles(@PathVariable String resourceCode) {
		return permissionRestSupport.listGrantedRoles(resourceCode);
	}

	@ApiOperation(value = "给指定的资源授权")
	@RequestMapping(value = "/roles/{roleCode}/resources", method = RequestMethod.POST)
	public void grantResourcesToRoles(@PathVariable String roleCode,
									  @RequestBody GrantResourceParameter grantResourceParameter) {
		permissionRestSupport.grantResourcesToRole(roleCode, grantResourceParameter);
	}

	@ApiOperation(value = "取消指定的资源授权(角色编码以逗号区分,角色编码格式'TYPE:ROLE_CODE',其中TYPE的值为SYS或者APP")
	@RequestMapping(value = "/roles/{roleCode}/resources/{resourceCode}", method = RequestMethod.DELETE)
	public void revokeResourcesFromRoles(@PathVariable String roleCode, @PathVariable String resourceCode) {
		permissionRestSupport.revokeResourcesFromRole(roleCode, resourceCode);
	}

}
