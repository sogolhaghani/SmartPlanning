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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "StudyFeedback", schema = "BI", catalog = "SmartPlanning")
public class StudyFeedback implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2099428077976746152L;
	private long id;
	private User student;
	private StudyPeriod studyPeriod;
	private byte dayOfWeek;
	private Integer testNo;
	private Integer duration;
	private Byte inCorrectNo;
	private byte order;
	private Byte correctNo;
	private Set<StudyFeedbackTopics> studyFeedbackTopics = new HashSet<StudyFeedbackTopics>(
			0);

	public StudyFeedback() {
	}

	public StudyFeedback(long id, User student, StudyPeriod studyPeriod,
			byte dayOfWeek, Integer testNo, Integer duration,
			Byte inCorrectNo,byte order, Byte correctNo) {
		super();
		this.id = id;
		this.student = student;
		this.studyPeriod = studyPeriod;
		this.dayOfWeek = dayOfWeek;
		this.testNo = testNo;
		this.duration = duration;
		this.inCorrectNo = inCorrectNo;
		this.order=order;
		this.correctNo=correctNo;
	}

	public StudyFeedback(long id, User student, StudyPeriod studyPeriod,
			byte dayOfWeek, Integer testNo, Integer duration,
			Set<StudyFeedbackTopics> studyFeedbackTopics,byte order, Byte correctNo) {
		super();
		this.id = id;
		this.student = student;
		this.studyPeriod = studyPeriod;
		this.dayOfWeek = dayOfWeek;
		this.testNo = testNo;
		this.duration = duration;
		this.studyFeedbackTopics = studyFeedbackTopics;
		this.order=order;
		this.correctNo=correctNo;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudentID", nullable = false)
	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudyPeriodID", nullable = false)
	public StudyPeriod getStudyPeriod() {
		return studyPeriod;
	}

	public void setStudyPeriod(StudyPeriod studyPeriod) {
		this.studyPeriod = studyPeriod;
	}

	@Column(name = "DayOfWeek", unique = false, nullable = false)
	public byte getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(byte dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Column(name = "TestNo", unique = false, nullable = true)
	public Integer getTestNo() {
		return testNo;
	}

	public void setTestNo(Integer testNo) {
		this.testNo = testNo;
	}

	@Column(name = "InCorrectNo", unique = false, nullable = true)
	public Byte getInCorrectNo() {
		return inCorrectNo;
	}

	public void setInCorrectNo(Byte inCorrectNo) {
		this.inCorrectNo = inCorrectNo;
	}

	@Column(name = "Duration", unique = false, nullable = true)
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	
	@OneToMany(mappedBy = "studyFeedback", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	public Set<StudyFeedbackTopics> getStudyFeedbackTopics() {
		return studyFeedbackTopics;
	}

	public void setStudyFeedbackTopics(
			Set<StudyFeedbackTopics> studyFeedbackTopics) {
		this.studyFeedbackTopics = studyFeedbackTopics;
	}

	@Column(name = "[Order]", unique = false, nullable = true)
	public byte getOrder() {
		return order;
	}
	
	public void setOrder(byte order) {
		this.order = order;
	}
	
	@Column(name = "CorrectNo", unique = false, nullable = true)
	public Byte getCorrectNo() {
		return correctNo;
	}
	
	public void setCorrectNo(Byte correctNo) {
		this.correctNo = correctNo;
	}
}
