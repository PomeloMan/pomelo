package pomeloman.core.module.project.service;

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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pomeloman.core.common.util.DateUtil;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemProcess;
import pomeloman.core.module.project.persistence.repo.ProjectWorkItemProcessRepository;
import pomeloman.core.module.project.service.interfaces.IProjectWorkItemProcessService;
import pomeloman.core.module.project.view.IProjectWorkItemProcess;

@Service
public class ProjectWorkItemProcessService implements IProjectWorkItemProcessService {

	private final Log logger = LogFactory.getLog(ProjectWorkItemProcessService.class);

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	ProjectWorkItemProcessRepository processRep;

	private Specification<ProjectWorkItemProcess> getQueryClause(IProjectWorkItemProcess view) {
		return new Specification<ProjectWorkItemProcess>() {
			@Override
			public Predicate toPredicate(Root<ProjectWorkItemProcess> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				String name = null;
				ProjectWorkItemProcess entity = view.getEntity();
				if (entity != null) {
					name = entity.getName();
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
	public Collection<ProjectWorkItemProcess> query(IProjectWorkItemProcess view) {
		return processRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<ProjectWorkItemProcess> query(IProjectWorkItemProcess view, Pageable pageable) {
		if (pageable == null) {
			pageable = view.getPageable();
		}
		return processRep.findAll(getQueryClause(view), pageable);
	}

	@Override
	public ProjectWorkItemProcess saveOne(IProjectWorkItemProcess view) {
		return this.saveOne(view.getEntity());
	}

	@Override
	public ProjectWorkItemProcess saveOne(ProjectWorkItemProcess entity) {
		Assert.notNull(entity, null);
		return processRep.save(entity);
	}

	@Override
	public Collection<ProjectWorkItemProcess> save(Collection<ProjectWorkItemProcess> entities) {
		return (Collection<ProjectWorkItemProcess>) processRep.save(entities);
	}

}
