package pomelo.core.module.project.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomelo.core.common.IPage;
import pomelo.core.module.project.persistence.entity.ProjectWorkItemRelation;
import pomelo.core.module.project.view.IProjectWorkItemRelation;

/**
 * @ClassName IProjectWorkItemRelationService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IProjectWorkItemRelationService {

	/**
	 * @param view
	 * @return
	 */
	Collection<ProjectWorkItemRelation> query(IProjectWorkItemRelation view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<ProjectWorkItemRelation> query(IPage<IProjectWorkItemRelation> view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	Collection<ProjectWorkItemRelation> saveOne(IProjectWorkItemRelation view);

	/**
	 * @param project
	 * @return
	 */
	@Transactional
	Collection<ProjectWorkItemRelation> saveOne(ProjectWorkItemRelation entity);

	/**
	 * @param projects
	 * @return
	 */
	@Transactional
	Collection<ProjectWorkItemRelation> save(Collection<ProjectWorkItemRelation> entities);

}
