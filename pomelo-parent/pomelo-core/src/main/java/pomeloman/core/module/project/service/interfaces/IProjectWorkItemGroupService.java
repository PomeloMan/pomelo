package pomeloman.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.core.module.project.persistence.entity.ProjectWorkItemGroup;
import pomeloman.core.module.project.view.IProjectWorkItemGroup;

/**
 * @ClassName IProjectWorkItemGroupService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IProjectWorkItemGroupService {

	/**
	 * @param view
	 * @return
	 */
	Collection<ProjectWorkItemGroup> query(IProjectWorkItemGroup view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<ProjectWorkItemGroup> query(IProjectWorkItemGroup view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	ProjectWorkItemGroup saveOne(IProjectWorkItemGroup view);

	/**
	 * @param project
	 * @return
	 */
	@Transactional
	ProjectWorkItemGroup saveOne(ProjectWorkItemGroup entity);

	/**
	 * @param projects
	 * @return
	 */
	@Transactional
	Collection<ProjectWorkItemGroup> save(Collection<ProjectWorkItemGroup> entities);
}
