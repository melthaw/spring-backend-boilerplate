package in.clouthink.daas.sbb.rbac.spi;

import in.clouthink.daas.sbb.rbac.model.Action;
import in.clouthink.daas.sbb.rbac.model.Resource;

/**
 * @author dz
 */
public interface ActionDecision {

	Action[] decide(String resourceCode, String userId);

	Action[] decide(Resource resource, String userId);

}
