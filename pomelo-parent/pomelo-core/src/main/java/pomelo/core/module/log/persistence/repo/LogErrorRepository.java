package pomelo.core.module.log.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomelo.core.module.log.persistence.entity.LogError;

public interface LogErrorRepository extends CrudRepository<LogError, String>, JpaSpecificationExecutor<LogError> {

}
