package pomeloman.core.module.project.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItem;

public class IProjectWorkItem extends AbstractView<ProjectWorkItem> {

	public IProjectWorkItem(ProjectWorkItem entity) {
		setEntity(entity);
	}

	public IProjectWorkItem() {
	}

	public static Collection<ProjectWorkItem> transform(Collection<IProjectWorkItem> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
