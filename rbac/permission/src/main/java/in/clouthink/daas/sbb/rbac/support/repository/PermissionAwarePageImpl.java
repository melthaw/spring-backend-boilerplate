package in.clouthink.daas.sbb.rbac.support.repository;

import in.clouthink.daas.sbb.rbac.model.Action;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Extends the spring data PageImpl
 *
 * @author dz
 */
public class PermissionAwarePageImpl<T> extends PageImpl<T> {

	private Action[] grantedActions;

	public PermissionAwarePageImpl(List<T> content, Pageable pageable, long total, Action[] grantedActions) {
		super(content, pageable, total);
		this.grantedActions = grantedActions;
	}

	public PermissionAwarePageImpl(List<T> content, Pageable pageable, long total, List<Action> grantedActions) {
		super(content, pageable, total);
		this.grantedActions = grantedActions.toArray(Action.EMPTY_ACTIONS);
	}

	public Action[] getGrantedActions() {
		return grantedActions;
	}

	public void setGrantedActions(Action[] grantedActions) {
		this.grantedActions = grantedActions;
	}

}
