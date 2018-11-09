package pomeloman.core.module.project.persistence.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import pomeloman.core.module.project.enums.Relation;
import pomeloman.core.module.project.persistence.entity.ProjectWorkItemRelation;

public interface ProjectWorkItemRelationRepository
		extends CrudRepository<ProjectWorkItemRelation, Long>, JpaSpecificationExecutor<ProjectWorkItemRelation> {

	ProjectWorkItemRelation findByFromAndRelation(Long id, Relation r);

	Collection<ProjectWorkItemRelation> findByFrom(Long id);

	Collection<ProjectWorkItemRelation> findByTo(Long id);
}
