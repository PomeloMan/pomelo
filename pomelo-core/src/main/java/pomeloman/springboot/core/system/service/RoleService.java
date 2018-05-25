package pomeloman.springboot.core.system.service;

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

import pomeloman.springboot.common.util.IDateUtil;
import pomeloman.springboot.core.system.persistence.model.Role;
import pomeloman.springboot.core.system.persistence.repo.RoleRepository;
import pomeloman.springboot.core.system.service.interfaces.IRoleService;
import pomeloman.springboot.core.system.view.IRole;

@Service
public class RoleService implements IRoleService {

	private final Log logger = LogFactory.getLog(RoleService.class);

	@Autowired
	RoleRepository roleRep;

	private Specification<Role> getQueryClause(IRole irole) {
		return new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				if (irole == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(irole);
					}
					return null;
				}

				String search = irole.getSearch();

				String name = null;
				Role role = irole.getEntity();
				if (role != null) {
					name = role.getName();
				}

				List<Predicate> restrictions = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(search)) {
					Predicate fuzzyPredicate = null;
					try {
						fuzzyPredicate = builder.equal(root.get("createdDate"),
								DateUtils.parseDate(search, IDateUtil.YYYY_MM_DD));
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
	public Collection<Role> query(IRole irole) {
		return roleRep.findAll(getQueryClause(irole));
	}

	@Override
	public Page<Role> query(IRole irole, Pageable pageable) {
		if (pageable == null) {
			pageable = irole.getPageable();
		}
		return roleRep.findAll(getQueryClause(irole), pageable);
	}

	@Override
	public Role saveOne(IRole irole) {
		return this.saveOne(irole.getEntity());
	}

	@Override
	public Role saveOne(Role role) {
		Assert.notNull(role, null);
		synchronized (role) {
			return roleRep.save(role);
		}
	}

	@Override
	public Collection<Role> save(Collection<Role> roles) {
		List<Role> result = new ArrayList<Role>();
		roles.stream().forEach(r -> result.add(saveOne(r)));
		return result;
	}

}
