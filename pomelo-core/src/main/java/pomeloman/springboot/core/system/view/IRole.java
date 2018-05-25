package pomeloman.springboot.core.system.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.springboot.common.AbstractView;
import pomeloman.springboot.core.system.persistence.model.Role;

public class IRole extends AbstractView<Role> {

	public IRole() {
	}

	public IRole(Role role) {
		setEntity(role);
	}

	public static Collection<Role> transform(Collection<IRole> views) {
		return views.stream().map((view) -> view.getEntity())
				.collect(Collectors.toList());
	}
}
