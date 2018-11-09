package pomeloman.core.module.project.view;

import java.util.Collection;
import java.util.stream.Collectors;

import pomeloman.core.common.AbstractView;
import pomeloman.core.module.project.persistence.entity.ProjectTeam;

public class IProjectTeam extends AbstractView<ProjectTeam> {

	public IProjectTeam(ProjectTeam entity) {
		setEntity(entity);
	}

	public IProjectTeam() {
	}

	public static Collection<ProjectTeam> transform(Collection<IProjectTeam> views) {
		return views.stream().map((view) -> view.getEntity()).collect(Collectors.toList());
	}
}
