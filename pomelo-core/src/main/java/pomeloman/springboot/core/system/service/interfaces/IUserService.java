package pomeloman.springboot.core.system.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import pomeloman.springboot.core.system.persistence.model.User;
import pomeloman.springboot.core.system.view.IUser;

@Transactional(readOnly = true)
public interface IUserService extends UserDetailsService {

	/**
	 * @param iuser
	 * @return
	 */
	Collection<User> query(IUser iuser);

	/**
	 * @param iuser
	 * @param pageable
	 * @return
	 */
	Page<User> query(IUser iuser, Pageable pageable);

	/**
	 * @param iuser
	 * @return
	 */
	@Transactional
	User saveOne(IUser iuser);

	/**
	 * @param user
	 * @return
	 */
	@Transactional
	User saveOne(User user);

	/**
	 * @param users
	 * @return
	 */
	@Transactional
	Collection<User> save(Collection<User> users);
}
