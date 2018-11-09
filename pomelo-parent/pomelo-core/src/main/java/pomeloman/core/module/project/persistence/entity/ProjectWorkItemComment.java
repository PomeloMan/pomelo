package pomeloman.core.module.project.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import pomeloman.core.module.system.persistence.DefaultEntity;

@Entity
public class ProjectWorkItemComment extends DefaultEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(columnDefinition = "text")
	private String content;
	@ManyToOne
	private ProjectWorkItem workItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProjectWorkItem getWorkItem() {
		return workItem;
	}

	public void setWorkItem(ProjectWorkItem workItem) {
		this.workItem = workItem;
	}

}
