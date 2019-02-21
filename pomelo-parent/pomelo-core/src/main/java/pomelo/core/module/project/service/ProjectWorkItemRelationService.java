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
import pomelo.core.module.project.persistence.entity.ProjectWorkItemRelation;
import pomelo.core.module.project.persistence.repo.ProjectWorkItemRelationRepository;
import pomelo.core.module.project.service.interfaces.IProjectWorkItemRelationService;
import pomelo.core.module.project.view.IProjectWorkItemRelation;

@Service
public class ProjectWorkItemRelationService implements IProjectWorkItemRelationService {

	private final Log logger = LogFactory.getLog(ProjectWorkItemRelationService.class);

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	ProjectWorkItemRelationRepository relationRep;

	private Specification<ProjectWorkItemRelation> getQueryClause(IProjectWorkItemRelation view) {
		return new Specification<ProjectWorkItemRelation>() {
			@Override
			public Predicate toPredicate(Root<ProjectWorkItemRelation> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				Long from = null;
				ProjectWorkItemRelation entity = BeanUtils.transform(view, ProjectWorkItemRelation.class);
				if (entity != null) {
					from = entity.getFrom();
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

				if (from == null) {
					Predicate likePredicate = builder.equal(root.get("from"), from);
					restrictions.add(likePredicate);
				}

				query.where(builder.and(restrictions.toArray(new Predicate[restrictions.size()])));
				return query.getGroupRestriction();
			}
		};
	}

	@Override
	public Collection<ProjectWorkItemRelation> query(IProjectWorkItemRelation view) {
		return relationRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<ProjectWorkItemRelation> query(IPage<IProjectWorkItemRelation> pageView, Pageable pageable) {
		if (pageable == null) {
			pageable = pageView.getPageable();
		}
		return relationRep.findAll(getQueryClause(pageView.getObject()), pageable);
	}

	@Override
	public Collection<ProjectWorkItemRelation> saveOne(IProjectWorkItemRelation view) {
		return this.saveOne(BeanUtils.transform(view, ProjectWorkItemRelation.class));
	}

	@Override
	public Collection<ProjectWorkItemRelation> saveOne(ProjectWorkItemRelation entity) {
		Assert.notNull(entity, null);
		Assert.notNull(entity.getFrom(), null);
		Assert.notNull(entity.getTo(), null);
		Assert.notNull(entity.getRelation(), null);
		Collection<ProjectWorkItemRelation> entities = new ArrayList<>();
		ProjectWorkItemRelation entityReverse = new ProjectWorkItemRelation(entity.getTo(), entity.getFrom(),
				entity.getRelation().getReverse());
		ProjectWorkItemRelation _entity = relationRep.findByFromAndRelation(entity.getFrom(), entity.getRelation());
		if (_entity != null) {
			ProjectWorkItemRelation _entityReverse = relationRep.findByFromAndRelation(entity.getTo(),
					entity.getRelation().getReverse());
			Assert.notNull(_entityReverse, null);
			entity.setId(_entity.getId());
			entityReverse.setId(_entityReverse.getId());
			entity.setVersion(_entity.getVersion());
			entityReverse.setVersion(_entityReverse.getVersion());
		}
		entities.add(entity);
		entities.add(entityReverse);
		// save reverse
		return (Collection<ProjectWorkItemRelation>) relationRep.save(entities);
	}

	@Override
	public Collection<ProjectWorkItemRelation> save(Collection<ProjectWorkItemRelation> entities) {
		List<ProjectWorkItemRelation> result = new ArrayList<ProjectWorkItemRelation>();
		entities.stream().forEach(entity -> result.addAll(saveOne(entity)));
		return result;
	}

}
