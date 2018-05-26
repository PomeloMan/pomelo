package pomeloman.core.module.system.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pomeloman.core.common.util.DateUtil;
import pomeloman.core.module.system.enums.Status;
import pomeloman.core.module.system.persistence.model.Authority;
import pomeloman.core.module.system.persistence.model.Role;
import pomeloman.core.module.system.persistence.model.User;
import pomeloman.core.module.system.persistence.repo.UserRepository;
import pomeloman.core.module.system.service.interfaces.IUserService;
import pomeloman.core.module.system.view.IUser;

@Service
public class UserService implements IUserService {

	private final Log logger = LogFactory.getLog(UserService.class);

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	UserRepository userRep;

	private Specification<User> getQueryClause(IUser view) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				String username = null;
				Collection<Role> roles = null;
				User user = view.getEntity();
				if (user != null) {
					username = user.getUsername();
					roles = user.getRoles();
				}

				List<Predicate> restrictions = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(search)) {
					Predicate fuzzyPredicate = null;
					try {
						fuzzyPredicate = builder.equal(root.get("createdDate"),
								DateUtils.parseDate(search, DateUtil.YYYY_MM_DD));
					} catch (ParseException e) {
						fuzzyPredicate = builder.or(builder.equal(root.get("username").as(String.class), search),
								builder.equal(root.get("displayName").as(String.class), search));
					}
					restrictions.add(fuzzyPredicate);
				}
				// add username condition
				if (StringUtils.isNotEmpty(username)) {
					Predicate likePredicate = builder.like(root.get("username"), "%" + username + "%");
					restrictions.add(likePredicate);
				}
				// add role condition
				if (CollectionUtils.isNotEmpty(roles)) {
					Join<Role, User> roleJoin = root.join("roles", JoinType.LEFT); // left outer join users_roles
					Iterator<Role> iterator = roles.iterator();
					In<String> in = builder.in(roleJoin.get("name").as(String.class));
					while (iterator.hasNext()) {
						in.value(iterator.next().getName());
					}
					restrictions.add(in);
				}
				// add where condition
				query.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
				return query.getRestriction();
			}
		};
	}

	/**
	 * {@link UserDetailsService}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRep.findOne(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				user.getStatus().equals(Status.Valid), !user.getStatus().equals(Status.Expired), true,
				!user.getStatus().equals(Status.Invalid), loadUserAuthorities(username));
	}

	/**
	 * {@link loadUserByUsername(String username)}
	 * 
	 * @param username
	 * @return
	 */
	private Collection<? extends GrantedAuthority> loadUserAuthorities(String username) {
		Collection<Authority> authorities = userRep.findAuthoriesByUsername(username);
		if (!authorities.isEmpty()) {
			return authorities.stream().map(auth -> new SimpleGrantedAuthority(auth.getName()))
					.collect(Collectors.toSet());
		}
		return Collections.emptyList();
	}

	@Override
	public User findOne(String username) {
		return userRep.findOne(username);
	}

	@Override
	public User findOneForUpdate(String username) {
		return userRep.findOneForUpdate(username);
	}

	@Override
	public Collection<User> query(IUser view) {
		return userRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<User> query(IUser view, Pageable pageable) {
		if (pageable == null) {
			pageable = view.getPageable();
		}
		return userRep.findAll(getQueryClause(view), pageable);
	}

	@Override
	public User saveOne(IUser view) {
		return this.saveOne(view.getEntity());
	}

	@Override
	public User saveOne(User user) {
		Assert.notNull(user, null);
		Assert.notNull(user.getUsername(), null);
		User _user = userRep.findOne(user.getUsername());
		if (_user == null) {
			_user = new User(user.getUsername());
		}
		user.setVersion(_user.getVersion());
		return userRep.save(user);
	}

	@Override
	public Collection<User> save(Collection<User> users) {
		List<User> result = new ArrayList<User>();
		users.stream().forEach(u -> result.add(saveOne(u)));
		return result;
	}

}
