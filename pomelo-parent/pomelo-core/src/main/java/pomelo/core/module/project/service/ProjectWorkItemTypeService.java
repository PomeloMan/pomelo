package pomelo.core.module.project.service;

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

import pomelo.core.common.util.DateUtil;
import pomelo.core.module.project.persistence.entity.ProjectWorkItemType;
import pomelo.core.module.project.persistence.repo.ProjectWorkItemTypeRepository;
import pomelo.core.module.project.service.interfaces.IProjectWorkItemTypeService;
import pomelo.core.module.project.view.IProjectWorkItemType;

@Service
public class ProjectWorkItemTypeService implements IProjectWorkItemTypeService {

	private final Log logger = LogFactory.getLog(ProjectWorkItemTypeService.class);

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	ProjectWorkItemTypeRepository typeRep;

	private Specification<ProjectWorkItemType> getQueryClause(IProjectWorkItemType view) {
		return new Specification<ProjectWorkItemType>() {
			@Override
			public Predicate toPredicate(Root<ProjectWorkItemType> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				String name = null;
				ProjectWorkItemType entity = view.getEntity();
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
	public Collection<ProjectWorkItemType> query(IProjectWorkItemType view) {
		return typeRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<ProjectWorkItemType> query(IProjectWorkItemType view, Pageable pageable) {
		if (pageable == null) {
			pageable = view.getPageable();
		}
		return typeRep.findAll(getQueryClause(view), pageable);
	}

	@Override
	public ProjectWorkItemType saveOne(IProjectWorkItemType view) {
		return this.saveOne(view.getEntity());
	}

	@Override
	public ProjectWorkItemType saveOne(ProjectWorkItemType entity) {
		Assert.notNull(entity, null);
		return typeRep.save(entity);
	}

	@Override
	public Collection<ProjectWorkItemType> save(Collection<ProjectWorkItemType> entities) {
		return (Collection<ProjectWorkItemType>) typeRep.save(entities);
	}

}
