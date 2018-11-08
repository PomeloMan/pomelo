package pomeloman.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.core.module.project.persistence.entity.ProjectWorkItemType;
import pomeloman.core.module.project.view.IProjectWorkItemType;

/**
 * @ClassName IProjectWorkItemTypeService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IProjectWorkItemTypeService {

	/**
	 * @param view
	 * @return
	 */
	Collection<ProjectWorkItemType> query(IProjectWorkItemType view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<ProjectWorkItemType> query(IProjectWorkItemType view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	ProjectWorkItemType saveOne(IProjectWorkItemType view);

	/**
	 * @param ProjectWorkItem
	 * @return
	 */
	@Transactional
	ProjectWorkItemType saveOne(ProjectWorkItemType entity);

	/**
	 * @param ProjectWorkItems
	 * @return
	 */
	@Transactional
	Collection<ProjectWorkItemType> save(Collection<ProjectWorkItemType> entities);
}
