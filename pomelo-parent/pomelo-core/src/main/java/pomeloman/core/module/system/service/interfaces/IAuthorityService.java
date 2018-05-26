package pomeloman.core.module.system.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.core.module.system.persistence.model.Authority;
import pomeloman.core.module.system.view.IAuthority;

/**
 * @ClassName IAuthorityService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IAuthorityService {

	/**
	 * @param view
	 * @return
	 */
	Collection<Authority> query(IAuthority view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<Authority> query(IAuthority view, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	Authority saveOne(IAuthority view);

	/**
	 * @param authority
	 * @return
	 */
	@Transactional
	Authority saveOne(Authority authority);

	/**
	 * @param authorities
	 * @return
	 */
	@Transactional
	Collection<Authority> save(Collection<Authority> authorities);
}
