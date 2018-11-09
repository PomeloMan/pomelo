package pomeloman.core.module.project.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemRelation;

public class IProjectWorkItemRelation extends AbstractView<ProjectWorkItemRelation> {

	public IProjectWorkItemRelation(ProjectWorkItemRelation entity) {
		setEntity(entity);
	}

	public IProjectWorkItemRelation() {
	}

	public static Collection<ProjectWorkItemRelation> transform(Collection<IProjectWorkItemRelation> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
