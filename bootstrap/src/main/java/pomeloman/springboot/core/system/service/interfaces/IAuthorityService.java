package pomeloman.springboot.core.system.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.springboot.core.system.persistence.model.Authority;
import pomeloman.springboot.core.system.view.IAuthority;

/**
 * @ClassName IAuthorityService.java
 * @Description TODO
 * @author PomeloMan
 */
@Transactional(readOnly = true)
public interface IAuthorityService {

	/**
	 * @param iauthority
	 * @return
	 */
	Collection<Authority> query(IAuthority iauthority);

	/**
	 * @param iauthority
	 * @param pageable
	 * @return
	 */
	Page<Authority> query(IAuthority iauthority, Pageable pageable);

	/**
	 * @param iauthority
	 * @return
	 */
	@Transactional
	Authority saveOne(IAuthority iauthority);

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
