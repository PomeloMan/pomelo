package pomeloman.springboot.core.system.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.springboot.common.AbstractView;
import pomeloman.springboot.core.system.persistence.model.Authority;

public class IAuthority extends AbstractView<Authority> {

	public IAuthority(Authority authority) {
		setEntity(authority);
	}

	public IAuthority() {
	}

	public static Collection<Authority> transform(Collection<IAuthority> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
