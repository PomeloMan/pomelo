package pomeloman.core.module.project.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemGroup;

public class IProjectWorkItemGroup extends AbstractView<ProjectWorkItemGroup> {

	public IProjectWorkItemGroup(ProjectWorkItemGroup entity) {
		setEntity(entity);
	}

	public IProjectWorkItemGroup() {
	}

	public static Collection<ProjectWorkItemGroup> transform(Collection<IProjectWorkItemGroup> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
