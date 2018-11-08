package pomeloman.core.module.project.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemType;

public class IProjectWorkItemType extends AbstractView<ProjectWorkItemType> {

	public IProjectWorkItemType(ProjectWorkItemType entity) {
		setEntity(entity);
	}

	public IProjectWorkItemType() {
	}

	public static Collection<ProjectWorkItemType> transform(Collection<IProjectWorkItemType> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
