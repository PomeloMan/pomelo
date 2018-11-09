package pomeloman.core.module.project.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import pomeloman.core.module.system.persistence.DefaultEntity;

@Entity
public class ProjectWorkItemType extends DefaultEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	@ManyToOne
	private ProjectWorkItemProcess process;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjectWorkItemProcess getProcess() {
		return process;
	}

	public void setProcess(ProjectWorkItemProcess process) {
		this.process = process;
	}

}
