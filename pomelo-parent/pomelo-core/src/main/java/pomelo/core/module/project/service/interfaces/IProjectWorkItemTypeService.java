package pomelo.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomelo.core.common.IPage;
import pomelo.core.module.project.persistence.entity.ProjectWorkItemType;
import pomelo.core.module.project.view.IProjectWorkItemType;

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
	Page<ProjectWorkItemType> query(IPage<IProjectWorkItemType> view, Pageable pageable);

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
