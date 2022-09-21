package ir.smartplanning.server.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Major", schema = "BI", catalog = "SmartPlanning")
public class Major implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3925216160997043228L;
	private long id;
	private String name;
	private Set<User> users = new HashSet<User>(0);
	private Set<StudyPeriod> studyPeriods=new HashSet<StudyPeriod>(0);

	public Major() {
	}

	public Major(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Major(long id, String name, Set<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "Name", unique = false, nullable = false, length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="major",fetch=FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@OneToMany(mappedBy="major",fetch=FetchType.LAZY)
	public Set<StudyPeriod> getStudyPeriods() {
		return studyPeriods;
	}
	
	public void setStudyPeriods(Set<StudyPeriod> studyPeriods) {
		this.studyPeriods = studyPeriods;
	}

}
