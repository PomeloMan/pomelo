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
import pomeloman.springboot.core.system.persistence.model.Authority;
import pomeloman.springboot.core.system.persistence.repo.AuthorityRepository;
import pomeloman.springboot.core.system.service.interfaces.IAuthorityService;
import pomeloman.springboot.core.system.view.IAuthority;

@Service
public class AuthorityService implements IAuthorityService {

	private final Log logger = LogFactory.getLog(AuthorityService.class);

	@Autowired
	AuthorityRepository authorityRep;

	private Specification<Authority> getQueryClause(IAuthority iauthority) {
		return new Specification<Authority>() {
			@Override
			public Predicate toPredicate(Root<Authority> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				if (iauthority == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(iauthority);
					}
					return null;
				}

				String search = iauthority.getSearch();

				String name = null;
				Authority authority = iauthority.getEntity();
				if (authority != null) {
					name = authority.getName();
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
				return query.getGroupRestriction();
			}
		};
	}

	@Override
	public Collection<Authority> query(IAuthority iauthority) {
		return authorityRep.findAll(getQueryClause(iauthority));
	}

	@Override
	public Page<Authority> query(IAuthority iauthority, Pageable pageable) {
		if (pageable == null) {
			pageable = iauthority.getPageable();
		}
		return authorityRep.findAll(getQueryClause(iauthority), pageable);
	}

	@Override
	public Authority saveOne(IAuthority iauthority) {
		return this.saveOne(iauthority.getEntity());
	}

	public Authority saveOne(Authority authority) {
		Assert.notNull(authority, null);
		synchronized (authority) {
			return authorityRep.save(authority);
		}
	}

	@Override
	public Collection<Authority> save(Collection<Authority> authorities) {
		List<Authority> result = new ArrayList<Authority>();
		authorities.stream().forEach(a -> result.add(saveOne(a)));
		return result;
	}

}
