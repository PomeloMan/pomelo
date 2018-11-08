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
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemGroup;
import pomeloman.core.module.project.persistence.repo.ProjectWorkItemGroupRepository;
import pomeloman.core.module.project.service.interfaces.IProjectWorkItemGroupService;
import pomeloman.core.module.project.view.IProjectWorkItemGroup;

@Service
public class ProjectWorkItemGroupService implements IProjectWorkItemGroupService {

	private final Log logger = LogFactory.getLog(ProjectWorkItemGroupService.class);

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	ProjectWorkItemGroupRepository workItemGroupRep;

	private Specification<ProjectWorkItemGroup> getQueryClause(IProjectWorkItemGroup view) {
		return new Specification<ProjectWorkItemGroup>() {
			@Override
			public Predicate toPredicate(Root<ProjectWorkItemGroup> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				ProjectWorkItemGroup entity = view.getEntity();
				if (entity != null) {

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

				query.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
				return query.getGroupRestriction();
			}
		};
	}

	@Override
	public Collection<ProjectWorkItemGroup> query(IProjectWorkItemGroup view) {
		return workItemGroupRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<ProjectWorkItemGroup> query(IProjectWorkItemGroup view, Pageable pageable) {
		if (pageable == null) {
			pageable = view.getPageable();
		}
		return workItemGroupRep.findAll(getQueryClause(view), pageable);
	}

	@Override
	public ProjectWorkItemGroup saveOne(IProjectWorkItemGroup view) {
		return this.saveOne(view.getEntity());
	}

	@Override
	public ProjectWorkItemGroup saveOne(ProjectWorkItemGroup entity) {
		Assert.notNull(entity, null);
		return workItemGroupRep.save(entity);
	}

	@Override
	public Collection<ProjectWorkItemGroup> save(Collection<ProjectWorkItemGroup> entities) {
		return (Collection<ProjectWorkItemGroup>) workItemGroupRep.save(entities);
	}

}
