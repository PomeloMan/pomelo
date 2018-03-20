package pomeloman.springboot.core.system.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomeloman.springboot.core.system.persistence.model.Role;

public interface RoleRepository extends CrudRepository<Role, String>,
		JpaSpecificationExecutor<Role> {

}
