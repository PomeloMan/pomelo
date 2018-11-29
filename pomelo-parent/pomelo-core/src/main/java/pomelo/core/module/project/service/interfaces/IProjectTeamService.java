package pomelo.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomelo.core.module.project.persistence.entity.ProjectTeam;
import pomelo.core.module.project.view.IProjectTeam;

/**
 * @ClassName IProjectTeamService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IProjectTeamService {

	/**
	 * @param view
	 * @return
	 */
	Collection<ProjectTeam> query(IProjectTeam view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<ProjectTeam> query(IProjectTeam view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	ProjectTeam saveOne(IProjectTeam view);

	/**
	 * @param project
	 * @return
	 */
	@Transactional
	ProjectTeam saveOne(ProjectTeam entity);

	/**
	 * @param projects
	 * @return
	 */
	@Transactional
	Collection<ProjectTeam> save(Collection<ProjectTeam> entities);
}
