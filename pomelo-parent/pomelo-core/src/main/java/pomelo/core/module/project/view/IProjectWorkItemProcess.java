package pomelo.core.module.project.view;

import pomelo.core.module.project.persistence.entity.ProjectWorkItemProcess;

public class IProjectWorkItemProcess extends ProjectWorkItemProcess {

	private static final long serialVersionUID = 1L;

	private String search;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
