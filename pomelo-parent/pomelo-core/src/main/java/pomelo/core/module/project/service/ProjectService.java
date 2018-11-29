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
import pomelo.core.module.project.persistence.entity.Project;
import pomelo.core.module.project.persistence.repo.ProjectRepository;
import pomelo.core.module.project.service.interfaces.IProjectService;
import pomelo.core.module.project.view.IProject;

@Service
public class ProjectService implements IProjectService {

	private final Log logger = LogFactory.getLog(ProjectService.class);

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	ProjectRepository projectRep;

	private Specification<Project> getQueryClause(IProject view) {
		return new Specification<Project>() {
			@Override
			public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				String name = null;
				Project entity = view.getEntity();
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
	public Collection<Project> query(IProject view) {
		return projectRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<Project> query(IProject view, Pageable pageable) {
		if (pageable == null) {
			pageable = view.getPageable();
		}
		return projectRep.findAll(getQueryClause(view), pageable);
	}

	@Override
	public Project saveOne(IProject view) {
		return this.saveOne(view.getEntity());
	}

	@Override
	public Project saveOne(Project entity) {
		Assert.notNull(entity, null);
		Project _entity = projectRep.findOne(entity.getId());
		if (_entity != null) {
			entity.setVersion(_entity.getVersion());
		}
		return projectRep.save(entity);
	}

	@Override
	public Collection<Project> save(Collection<Project> entities) {
		List<Project> result = new ArrayList<Project>();
		entities.stream().forEach(entity -> result.add(saveOne(entity)));
		return result;
	}

}
