package pomeloman.core.module.system.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomeloman.core.module.system.persistence.entity.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, String>, JpaSpecificationExecutor<Authority> {

}
