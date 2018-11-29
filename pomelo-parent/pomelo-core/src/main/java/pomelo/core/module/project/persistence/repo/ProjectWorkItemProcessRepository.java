package pomelo.core.module.project.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomelo.core.module.project.persistence.entity.ProjectWorkItemProcess;

public interface ProjectWorkItemProcessRepository
		extends CrudRepository<ProjectWorkItemProcess, String>, JpaSpecificationExecutor<ProjectWorkItemProcess> {

}
