package pomeloman.springboot.core.system.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.springboot.core.system.persistence.model.Role;
import pomeloman.springboot.core.system.view.IRole;

/**
 * @ClassName IRoleService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IRoleService {

	/**
	 * @param irole
	 * @return
	 */
	Collection<Role> query(IRole irole);

	/**
	 * @param irole
	 * @param pageable
	 * @return
	 */
	Page<Role> query(IRole irole, Pageable pageable);

	/**
	 * @param irole
	 * @return
	 */
	@Transactional
	Role saveOne(IRole irole);

	/**
	 * @param role
	 * @return
	 */
	@Transactional
	Role saveOne(Role role);

	/**
	 * @param roles
	 * @return
	 */
	@Transactional
	Collection<Role> save(Collection<Role> roles);
}
