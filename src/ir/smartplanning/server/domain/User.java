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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "[User]", schema = "BI", catalog = "SmartPlanning", uniqueConstraints = { @UniqueConstraint(columnNames = "UserName") })
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8181422060847891252L;

	private long id;
	private String name;
	private String family;
	private String userName;
	private byte[] password;
	private byte grade;
	private boolean sex;
	private Major major;
	private Set<StudyFeedback> studyFeedbacks=new HashSet<StudyFeedback>();

	public User() {
	}

	public User(long id, String name, String family, String userName,
			byte[] password, byte grade, boolean sex, Major major) {
		super();
		this.id = id;
		this.name = name;
		this.family = family;
		this.userName = userName;
		this.password = password;
		this.grade = grade;
		this.sex = sex;
		this.major = major;
	}

	public User(long id, String name, String family, String userName,
			byte[] password, byte grade, boolean sex, Major major,Set<StudyFeedback> studyFeedbacks) {
		super();
		this.id = id;
		this.name = name;
		this.family = family;
		this.userName = userName;
		this.password = password;
		this.grade = grade;
		this.sex = sex;
		this.major = major;
		this.studyFeedbacks=studyFeedbacks;
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

	@Column(name = "Family", unique = false, nullable = false, length = 30)
	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	@Column(name = "UserName", unique = true, nullable = false, length = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "Password", unique = false)
	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	@Column(name = "Grade", unique = false, nullable = false)
	public byte getGrade() {
		return grade;
	}

	public void setGrade(byte grade) {
		this.grade = grade;
	}

	@Column(name = "Sex", nullable = false)
	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MajorID", nullable = false)
	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}
	
	@OneToMany(mappedBy="student",fetch=FetchType.LAZY)
	public Set<StudyFeedback> getStudyFeedbacks() {
		return studyFeedbacks;
	}
	
	public void setStudyFeedbacks(Set<StudyFeedback> studyFeedbacks) {
		this.studyFeedbacks = studyFeedbacks;
	}

}
