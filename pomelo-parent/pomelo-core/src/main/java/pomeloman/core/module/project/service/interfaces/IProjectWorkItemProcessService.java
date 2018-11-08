package pomeloman.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.core.module.project.persistence.entity.ProjectWorkItemProcess;
import pomeloman.core.module.project.view.IProjectWorkItemProcess;

/**
 * @ClassName IProjectWorkItemWorkItemService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IProjectWorkItemProcessService {

	/**
	 * @param view
	 * @return
	 */
	Collection<ProjectWorkItemProcess> query(IProjectWorkItemProcess view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<ProjectWorkItemProcess> query(IProjectWorkItemProcess view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	ProjectWorkItemProcess saveOne(IProjectWorkItemProcess view);

	/**
	 * @param ProjectWorkItem
	 * @return
	 */
	@Transactional
	ProjectWorkItemProcess saveOne(ProjectWorkItemProcess entity);

	/**
	 * @param ProjectWorkItems
	 * @return
	 */
	@Transactional
	Collection<ProjectWorkItemProcess> save(Collection<ProjectWorkItemProcess> entities);
}
