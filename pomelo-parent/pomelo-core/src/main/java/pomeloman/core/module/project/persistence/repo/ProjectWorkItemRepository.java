package pomeloman.core.module.project.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomeloman.core.module.project.persistence.entity.ProjectWorkItem;

public interface ProjectWorkItemRepository
		extends CrudRepository<ProjectWorkItem, Long>, JpaSpecificationExecutor<ProjectWorkItem> {

}
