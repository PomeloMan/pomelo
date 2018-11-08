package pomeloman.core.module.project.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import pomeloman.core.module.system.persistence.DefaultEntity;

@Entity
public class ProjectWorkItemProcess extends DefaultEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
