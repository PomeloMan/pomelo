package pomelo.core.module.system.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomelo.core.common.IPage;
import pomelo.core.module.system.persistence.entity.Role;
import pomelo.core.module.system.view.IRole;

/**
 * @ClassName IRoleService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IRoleService {

	/**
	 * @param view
	 * @return
	 */
	Collection<Role> query(IRole view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<Role> query(IPage<IRole> pageView, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	Role saveOne(IRole view);

	/**
	 * @param role
	 * @return
	 */
	@Transactional
	Role saveOne(Role entity);

	/**
	 * @param roles
	 * @return
	 */
	@Transactional
	Collection<Role> save(Collection<Role> entities);
}
