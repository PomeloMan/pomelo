package pomelo.core.module.system.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomelo.core.module.system.persistence.entity.Role;

public interface RoleRepository extends CrudRepository<Role, String>, JpaSpecificationExecutor<Role> {

}
