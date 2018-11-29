package pomelo.core.module.project.persistence.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import pomelo.core.module.system.enums.Priority;
import pomelo.core.module.system.persistence.VersionEntity;

@Entity
public class ProjectWorkItem extends VersionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	private String description;
	private Priority priority;
	private Date startDate;
	private Date targetDate;
	private String tag;

	@ManyToMany
	private Collection<ProjectTeam> teams;

	public ProjectWorkItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectWorkItem(String title) {
		super();
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Collection<ProjectTeam> getTeams() {
		return teams;
	}

	public void setTeams(Collection<ProjectTeam> teams) {
		this.teams = teams;
	}

}
