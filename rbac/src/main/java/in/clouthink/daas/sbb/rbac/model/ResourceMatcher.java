package in.clouthink.daas.sbb.rbac.model;

/**
 * @author dz
 */
public interface ResourceMatcher {

	boolean matched(Resource resource);

}
