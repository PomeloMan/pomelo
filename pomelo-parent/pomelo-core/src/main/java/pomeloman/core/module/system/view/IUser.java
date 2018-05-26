package pomeloman.core.module.system.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.system.persistence.model.User;

public class IUser extends AbstractView<User> {

	public IUser() {
	}

	public IUser(User entity) {
		setEntity(entity);
	}

	public static Collection<User> transform(Collection<IUser> views) {
		return views.stream().map((view) -> view.getEntity())
				.collect(Collectors.toList());
	}

}
