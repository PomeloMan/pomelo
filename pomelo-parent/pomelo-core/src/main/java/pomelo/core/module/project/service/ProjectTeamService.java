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
import pomelo.core.module.project.persistence.entity.ProjectTeam;
import pomelo.core.module.project.persistence.repo.ProjectTeamRepository;
import pomelo.core.module.project.service.interfaces.IProjectTeamService;
import pomelo.core.module.project.view.IProjectTeam;

@Service
public class ProjectTeamService implements IProjectTeamService {

	private final Log logger = LogFactory.getLog(ProjectTeamService.class);

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
	ProjectTeamRepository workItemGroupRep;

	private Specification<ProjectTeam> getQueryClause(IProjectTeam view) {
		return new Specification<ProjectTeam>() {
			@Override
			public Predicate toPredicate(Root<ProjectTeam> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {

				if (view == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(view);
					}
					return null;
				}

				String search = view.getSearch();

				ProjectTeam entity = BeanUtils.transform(view, ProjectTeam.class);
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
	public Collection<ProjectTeam> query(IProjectTeam view) {
		return workItemGroupRep.findAll(getQueryClause(view));
	}

	@Override
	public Page<ProjectTeam> query(IPage<IProjectTeam> pageView, Pageable pageable) {
		if (pageable == null) {
			pageable = pageView.getPageable();
		}
		return workItemGroupRep.findAll(getQueryClause(pageView.getObject()), pageable);
	}

	@Override
	public ProjectTeam saveOne(IProjectTeam view) {
		return this.saveOne(BeanUtils.transform(view, ProjectTeam.class));
	}

	@Override
	public ProjectTeam saveOne(ProjectTeam entity) {
		Assert.notNull(entity, null);
		return workItemGroupRep.save(entity);
	}

	@Override
	public Collection<ProjectTeam> save(Collection<ProjectTeam> entities) {
		return (Collection<ProjectTeam>) workItemGroupRep.save(entities);
	}

}
