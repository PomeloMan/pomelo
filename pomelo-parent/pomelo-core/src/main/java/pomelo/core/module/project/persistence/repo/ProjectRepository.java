package pomelo.core.module.project.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomelo.core.module.project.persistence.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>, JpaSpecificationExecutor<Project> {

}
