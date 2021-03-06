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

import pomelo.core.common.IPage;
import pomelo.core.common.util.BeanUtils;
import pomelo.core.common.util.DateUtil;
import pomelo.core.module.project.persistence.entity.ProjectWorkItem;
import pomelo.core.module.project.persistence.repo.ProjectWorkItemRepository;
import pomelo.core.module.project.service.interfaces.IProjectWorkItemRelationService;
import pomelo.core.module.project.service.interfaces.IProjectWorkItemService;
import pomelo.core.module.project.view.IProjectWorkItem;

@Service
public class ProjectWorkItemService implements IProjectWorkItemService {

	private final Log logger = LogFactory.getLog(ProjectWorkItemService.class);

	@Autowired
	ConfigurableApplicationContext context;
	@Autowired
	ProjectWorkItemRepository workItemRep;
	@Autowired
	IProjectWorkItemRelationService relationService;

	private Specification<ProjectWorkItem> getQueryClause(IProjectWorkItem view) {
		return new Specification<ProjectWorkItem>() {
			@Override
			public Predicate toPredicate(Root<ProjectWorkItem> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				String name = null;
				ProjectWorkItem entity = BeanUtils.transform(view, ProjectWorkItem.class);
				if (entity != null) {
					name = entity.getTitle();
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
	public Collection<ProjectWorkItem> query(IProjectWorkItem view) {
		return workItemRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<ProjectWorkItem> query(IPage<IProjectWorkItem> pageView, Pageable pageable) {
		if (pageable == null) {
			pageable = pageView.getPageable();
		}
		return workItemRep.findAll(getQueryClause(pageView.getObject()), pageable);
	}

	@Override
	public ProjectWorkItem saveOne(IProjectWorkItem view) {
		return this.saveOne(BeanUtils.transform(view, ProjectWorkItem.class));
	}

	@Override
	public ProjectWorkItem saveOne(ProjectWorkItem entity) {
		Assert.notNull(entity, null);
		ProjectWorkItem _entity = workItemRep.findOne(entity.getId());
		if (_entity != null) {
			entity.setVersion(_entity.getVersion());
		}
		return workItemRep.save(entity);
	}

	@Override
	public Collection<ProjectWorkItem> save(Collection<ProjectWorkItem> entities) {
		List<ProjectWorkItem> result = new ArrayList<ProjectWorkItem>();
		entities.stream().forEach(entity -> result.add(saveOne(entity)));
		return result;
	}

}
