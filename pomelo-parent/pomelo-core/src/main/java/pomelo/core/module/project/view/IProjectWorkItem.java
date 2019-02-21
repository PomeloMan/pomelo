package pomelo.core.module.project.view;

import pomelo.core.module.project.persistence.entity.ProjectWorkItem;

public class IProjectWorkItem extends ProjectWorkItem {

	private static final long serialVersionUID = 1L;

	private String search;
	IProjectWorkItemRelation relation;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public IProjectWorkItemRelation getRelation() {
		return relation;
	}

	public void setRelation(IProjectWorkItemRelation relation) {
		this.relation = relation;
	}

}
