package pomeloman.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.core.module.project.persistence.entity.ProjectWorkItem;
import pomeloman.core.module.project.view.IProjectWorkItem;

/**
 * @ClassName IProjectWorkItemService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IProjectWorkItemService {

	/**
	 * @param view
	 * @return
	 */
	Collection<ProjectWorkItem> query(IProjectWorkItem view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<ProjectWorkItem> query(IProjectWorkItem view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	ProjectWorkItem saveOne(IProjectWorkItem view);

	/**
	 * @param project
	 * @return
	 */
	@Transactional
	ProjectWorkItem saveOne(ProjectWorkItem entity);

	/**
	 * @param projects
	 * @return
	 */
	@Transactional
	Collection<ProjectWorkItem> save(Collection<ProjectWorkItem> entities);
}
