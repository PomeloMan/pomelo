package pomeloman.springboot.core.system.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.springboot.common.AbstractView;
import pomeloman.springboot.core.system.persistence.model.User;

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
