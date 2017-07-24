package in.clouthink.daas.sbb.dashboard.rest.controller;

import in.clouthink.daas.sbb.dashboard.rest.dto.ResourceSummary;
import in.clouthink.daas.sbb.dashboard.rest.dto.TypedRoleSummary;
import in.clouthink.daas.sbb.dashboard.rest.support.ResourceRoleRelationshipRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@Api("角色管理")
@RestController
@RequestMapping("/api/resources")
public class ResourceRoleRelationshipRestController {

	@Autowired
	private ResourceRoleRelationshipRestSupport resourceRoleRelationshipRestSupport;

	@ApiOperation(value = "获取资源列表")
	@RequestMapping(method = RequestMethod.GET)
	public List<ResourceSummary> listResourceSummaries() {
		return resourceRoleRelationshipRestSupport.listResourceSummaries();
	}

	@ApiOperation(value = "获取指定角色可访问的资源列表")
	@RequestMapping(value = "/byRole/{roleCode}", method = RequestMethod.GET)
	public List<ResourceSummary> listResourceSummariesByRole(@PathVariable String roleCode) {
		return resourceRoleRelationshipRestSupport.listResourceSummariesByRole(roleCode);
	}

	@ApiOperation(value = "获取授权的角色列表")
	@RequestMapping(value = "/{code}/roles", method = RequestMethod.GET)
	public List<TypedRoleSummary> listGrantedRoles(@PathVariable String code) {
		return resourceRoleRelationshipRestSupport.listGrantedRoles(code);
	}

	@ApiOperation(value = "给指定的资源授权")
	@RequestMapping(value = "/{code}/roles", method = RequestMethod.POST)
	public void grantRolesToResource(@PathVariable String code, @RequestBody String[] typedRoleCodes) {
		resourceRoleRelationshipRestSupport.grantRolesToResource(code, typedRoleCodes);
	}

	@ApiOperation(value = "取消指定的资源授权(角色编码以逗号区分,角色编码格式'TYPE:ROLE_CODE',其中TYPE的值为SYS或者APP")
	@RequestMapping(value = "/{code}/roles", method = RequestMethod.DELETE)
	public void revokeRolesFromResource(@PathVariable String code, String[] typedRoleCodes) {
		String[] typedRoleCodeArray = typedRoleCodes;
		resourceRoleRelationshipRestSupport.revokeRolesFromResource(code, typedRoleCodeArray);
	}

}
