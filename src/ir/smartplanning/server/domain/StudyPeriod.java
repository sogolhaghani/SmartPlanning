package ir.smartplanning.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "StudyPeriod", schema = "BI", catalog = "SmartPlanning")
public class StudyPeriod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6635862034277841499L;
	
	private long id;
	private byte grade;
	private Date startDate;
	private Date endDate;
	private byte weeksOrder;
	private Major major;
	
	public StudyPeriod() {
		// TODO Auto-generated constructor stub
	}

	public StudyPeriod(long id, byte grade, Date startDate, Date endDate,
			byte weeksOrder, Major major) {
		super();
		this.id = id;
		this.grade = grade;
		this.startDate = startDate;
		this.endDate = endDate;
		this.weeksOrder = weeksOrder;
		this.major = major;
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

	@Column(name = "Grade", unique = false, nullable = false)
	public byte getGrade() {
		return grade;
	}

	public void setGrade(byte grade) {
		this.grade = grade;
	}

	@Column(name = "StartDate", unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "EndDate", unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "WeeksOrder", unique = false, nullable = false)
	public byte getWeeksOrder() {
		return weeksOrder;
	}

	public void setWeeksOrder(byte weeksOrder) {
		this.weeksOrder = weeksOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MajorID", nullable = false)
	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}
	
	
	
}
