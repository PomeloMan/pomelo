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
import pomeloman.core.module.system.persistence.entity.Authority;
import pomeloman.core.module.system.persistence.repo.AuthorityRepository;
import pomeloman.core.module.system.service.interfaces.IAuthorityService;
import pomeloman.core.module.system.view.IAuthority;

@Service
public class AuthorityService implements IAuthorityService {

	private final Log logger = LogFactory.getLog(AuthorityService.class);

	@Autowired
	AuthorityRepository authorityRep;

	private Specification<Authority> getQueryClause(IAuthority view) {
		return new Specification<Authority>() {
			@Override
			public Predicate toPredicate(Root<Authority> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				String name = null;
				Authority authority = view.getEntity();
				if (authority != null) {
					name = authority.getName();
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
				return query.getGroupRestriction();
			}
		};
	}

	@Override
	public Collection<Authority> query(IAuthority view) {
		return authorityRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<Authority> query(IAuthority view, Pageable pageable) {
		if (pageable == null) {
			pageable = view.getPageable();
		}
		return authorityRep.findAll(getQueryClause(view), pageable);
	}

	@Override
	public Authority saveOne(IAuthority view) {
		return this.saveOne(view.getEntity());
	}

	@Override
	public Authority saveOne(Authority entity) {
		Assert.notNull(entity, null);
		Assert.notNull(entity.getName(), null);
		Authority _entity = authorityRep.findOne(entity.getName());
		if (_entity != null) {
			entity.setVersion(_entity.getVersion());
		}
		return authorityRep.save(entity);
	}

	@Override
	public Collection<Authority> save(Collection<Authority> entities) {
		List<Authority> result = new ArrayList<Authority>();
		entities.stream().forEach(entity -> result.add(saveOne(entity)));
		return result;
	}

}
