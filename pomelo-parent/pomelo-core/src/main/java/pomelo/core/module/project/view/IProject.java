package pomelo.core.module.project.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomelo.core.common.AbstractView;
import pomelo.core.module.project.persistence.entity.Project;

public class IProject extends AbstractView<Project> {

	public IProject(Project entity) {
		setEntity(entity);
	}

	public IProject() {
	}

	public static Collection<Project> transform(Collection<IProject> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
