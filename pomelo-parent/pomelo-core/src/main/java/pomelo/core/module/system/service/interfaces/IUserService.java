package pomelo.core.module.system.service.interfaces;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import pomelo.core.common.IPage;
import pomelo.core.module.system.persistence.entity.User;
import pomelo.core.module.system.view.IUser;

@Transactional(readOnly = true)
public interface IUserService extends UserDetailsService {

	/**
	 * @param username
	 * @return
	 */
	User findOne(String username);

	/**
	 * 悲观锁，此查询方法会执行数据库行锁
	 * 执行此方法需要@Transactional(readOnly = false)
	 * {@link UserRepository.findOneForUpdate()}
	 * @param username
	 * @return
	 */
	@Transactional
	User findOneForUpdate(String username);

	/**
	 * @param view
	 * @return
	 */
	Collection<User> query(IUser view);

	/**
	 * @param view
	 * @param pageable
	 * @return
	 */
	Page<User> query(IPage<IUser> pageView, Pageable pageable);

	/**
	 * @param view
	 * @return
	 */
	@Transactional
	User saveOne(IUser view);

	/**
	 * @param user
	 * @return
	 */
	@Transactional
	User saveOne(User entity);

	/**
	 * @param users
	 * @return
	 */
	@Transactional
	Collection<User> save(Collection<User> entities);
}
