package pomelo.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomelo.core.module.project.persistence.entity.Project;
import pomelo.core.module.project.view.IProject;

/**
 * @ClassName IProjectService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IProjectService {

	/**
	 * @param view
	 * @return
	 */
	Collection<Project> query(IProject view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<Project> query(IProject view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	Project saveOne(IProject view);

	/**
	 * @param project
	 * @return
	 */
	@Transactional
	Project saveOne(Project entity);

	/**
	 * @param projects
	 * @return
	 */
	@Transactional
	Collection<Project> save(Collection<Project> entities);
}
