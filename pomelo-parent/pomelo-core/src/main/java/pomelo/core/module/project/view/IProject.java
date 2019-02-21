package pomelo.core.module.project.view;

import pomelo.core.module.project.persistence.entity.Project;

public class IProject extends Project {

	private static final long serialVersionUID = 1L;

	private String search;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
