package in.clouthink.daas.sbb.account.rest.dto;

import in.clouthink.daas.sbb.account.domain.request.RoleQueryRequest;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import io.swagger.annotations.ApiModel;

@ApiModel("角色查询参数")
public class RoleQueryParameter extends PageQueryParameter implements RoleQueryRequest {
}
