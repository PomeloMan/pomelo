package pomeloman.core.module.system.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomeloman.core.module.system.persistence.model.Role;

public interface RoleRepository extends CrudRepository<Role, String>, JpaSpecificationExecutor<Role> {

}
