package pomeloman.core.module.system.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pomeloman.core.common.util.DateUtil;
import pomeloman.core.module.system.persistence.model.Role;
import pomeloman.core.module.system.persistence.repo.RoleRepository;
import pomeloman.core.module.system.service.interfaces.IRoleService;
import pomeloman.core.module.system.view.IRole;

@Service
public class RoleService implements IRoleService {

	private final Log logger = LogFactory.getLog(RoleService.class);

	@Autowired
	RoleRepository roleRep;

	private Specification<Role> getQueryClause(IRole view) {
		return new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				String name = null;
				Role role = view.getEntity();
				if (role != null) {
					name = role.getName();
				}

				List<Predicate> restrictions = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(search)) {
					Predicate fuzzyPredicate = null;
					try {
						fuzzyPredicate = builder.equal(root.get("createdDate"),
								DateUtils.parseDate(search, DateUtil.YYYY_MM_DD));
					} catch (ParseException e) {
						fuzzyPredicate = builder.like(root.get("name"), "%" + search + "%");
					}
					restrictions.add(fuzzyPredicate);
				}

				if (StringUtils.isNotEmpty(name)) {
					Predicate likePredicate = builder.like(root.get("name"), "%" + name + "%");
					restrictions.add(likePredicate);
				}

				query.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
				return query.getRestriction();
			}
		};
	}

	@Override
	public Collection<Role> query(IRole view) {
		return roleRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<Role> query(IRole view, Pageable pageable) {
		if (pageable == null) {
			pageable = view.getPageable();
		}
		return roleRep.findAll(getQueryClause(view), pageable);
	}

	@Override
	public Role saveOne(IRole view) {
		return this.saveOne(view.getEntity());
	}

	@Override
	public Role saveOne(Role role) {
		Assert.notNull(role, null);
		Assert.notNull(role.getName(), null);
		Role _role = roleRep.findOne(role.getName());
		if (_role == null) {
			_role = new Role(role.getName());
		}
		role.setVersion(_role.getVersion());
		return roleRep.save(role);
	}

	@Override
	public Collection<Role> save(Collection<Role> roles) {
		List<Role> result = new ArrayList<Role>();
		roles.stream().forEach(r -> result.add(saveOne(r)));
		return result;
	}

}
