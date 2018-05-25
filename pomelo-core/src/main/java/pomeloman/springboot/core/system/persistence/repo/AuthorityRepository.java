package pomeloman.springboot.core.system.persistence.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomeloman.springboot.core.system.persistence.model.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, String>, JpaSpecificationExecutor<Authority> {

}
