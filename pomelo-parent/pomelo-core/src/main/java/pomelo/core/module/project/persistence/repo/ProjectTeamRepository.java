package pomelo.core.module.project.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomelo.core.module.project.persistence.entity.ProjectTeam;

public interface ProjectTeamRepository
		extends CrudRepository<ProjectTeam, Integer>, JpaSpecificationExecutor<ProjectTeam> {

}
