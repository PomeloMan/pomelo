package pomelo.core.module.system.view;

import pomelo.core.module.system.persistence.entity.Authority;

public class IAuthority extends Authority {

	private static final long serialVersionUID = 1L;

	private String search;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
