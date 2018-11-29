package pomelo.core.module.system.view;

import java.util.Collection;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModel;
import pomelo.core.common.AbstractView;
import pomelo.core.module.system.persistence.entity.User;

@ApiModel
public class IUser extends AbstractView<User> {

	public IUser() {
	}

	public IUser(User entity) {
		setEntity(entity);
	}

	public static Collection<User> transform(Collection<IUser> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}

}
