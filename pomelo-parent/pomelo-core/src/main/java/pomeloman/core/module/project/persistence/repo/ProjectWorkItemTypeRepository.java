package pomeloman.core.module.project.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomeloman.core.module.project.persistence.entity.ProjectWorkItemType;

public interface ProjectWorkItemTypeRepository
		extends CrudRepository<ProjectWorkItemType, Integer>, JpaSpecificationExecutor<ProjectWorkItemType> {

}
