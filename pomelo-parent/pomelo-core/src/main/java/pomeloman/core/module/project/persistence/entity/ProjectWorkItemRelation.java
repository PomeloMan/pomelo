package pomeloman.core.module.project.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import pomeloman.core.module.project.enums.Relation;
import pomeloman.core.module.system.persistence.VersionEntity;

@Entity
public class ProjectWorkItemRelation extends VersionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "`from`")
	private Long from;
	@Column(name = "`to`")
	private Long to;
	private Relation relation;

	public ProjectWorkItemRelation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectWorkItemRelation(Long from, Long to, Relation relation) {
		super();
		this.from = from;
		this.to = to;
		this.relation = relation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

}
