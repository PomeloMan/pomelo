package pomeloman.core.module.project.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemProcess;

public class IProjectWorkItemProcess extends AbstractView<ProjectWorkItemProcess> {

	public IProjectWorkItemProcess(ProjectWorkItemProcess entity) {
		setEntity(entity);
	}

	public IProjectWorkItemProcess() {
	}

	public static Collection<ProjectWorkItemProcess> transform(Collection<IProjectWorkItemProcess> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
