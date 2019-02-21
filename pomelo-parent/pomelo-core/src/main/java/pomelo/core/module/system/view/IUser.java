package pomelo.core.module.system.view;

import io.swagger.annotations.ApiModel;
import pomelo.core.module.system.persistence.entity.User;

@ApiModel
public class IUser extends User {

	private static final long serialVersionUID = 1L;

	private String search;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
