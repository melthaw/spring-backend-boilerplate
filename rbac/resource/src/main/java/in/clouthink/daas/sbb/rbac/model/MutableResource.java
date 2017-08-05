package in.clouthink.daas.sbb.rbac.model;

import java.util.List;
import java.util.Map;

/**
 * @author dz
 */
public interface MutableResource extends Resource {

	void setVirtual(boolean virtual);

	void setOpen(boolean open);

	void setCode(String code);

	void setName(String name);

	void setPatterns(List<String> patterns);

	void setActions(List<Action> actions);

	void setMetadata(Map<String,Object> metadata);

}
