package pomeloman.core.module.system.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.system.persistence.entity.Authority;

public class IAuthority extends AbstractView<Authority> {

	public IAuthority(Authority entity) {
		setEntity(entity);
	}

	public IAuthority() {
	}

	public static Collection<Authority> transform(Collection<IAuthority> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
