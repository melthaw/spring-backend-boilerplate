package in.clouthink.daas.sbb.rbac.rest.controller;

import in.clouthink.daas.sbb.rbac.rest.dto.PrivilegedResourceWithChildren;
import in.clouthink.daas.sbb.rbac.rest.support.ResourceRestSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("资源列表")
@RestController
@RequestMapping("/api/resources")
public class ResourceRestController {

	@Autowired
	private ResourceRestSupport resourceRestSupport;

	@ApiOperation(value = "获取资源列表(不包括open)")
	@RequestMapping(method = RequestMethod.GET)
	public List<PrivilegedResourceWithChildren> listResources() {
		return resourceRestSupport.listResources();
	}

}
