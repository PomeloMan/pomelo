package pomeloman.core.module.system.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.system.persistence.entity.Role;

public class IRole extends AbstractView<Role> {

	public IRole() {
	}

	public IRole(Role entity) {
		setEntity(entity);
	}

	public static Collection<Role> transform(Collection<IRole> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
