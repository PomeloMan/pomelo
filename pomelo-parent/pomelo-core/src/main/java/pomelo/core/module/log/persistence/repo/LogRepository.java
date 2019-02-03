package pomelo.core.module.log.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomelo.core.module.log.persistence.entity.Log;

public interface LogRepository extends CrudRepository<Log, String>, JpaSpecificationExecutor<Log> {

}
