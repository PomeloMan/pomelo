package pomeloman.springboot.core.system.persistence.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pomeloman.springboot.core.system.persistence.model.Authority;
import pomeloman.springboot.core.system.persistence.model.User;

public interface UserRepository extends CrudRepository<User, String>, JpaSpecificationExecutor<User> {

	User findByUsername(String username);

	@Query("select distinct a from User u join u.roles r join r.authorities a where u.username = :username")
	Set<Authority> findAuthoriesByUsername(@Param("username") String username);
}
