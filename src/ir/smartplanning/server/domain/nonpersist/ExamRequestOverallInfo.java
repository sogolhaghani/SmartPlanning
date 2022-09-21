package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;
import java.util.Date;

public class ExamRequestOverallInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5327951678853851360L;

	private Long examRequestTypeID;
	private Long examSubjectTypeID;
	private long examRequestID;
	private String examName;
	private int questionCount1;
	private int questionCount2;
	private Date startDateTime;
	private Date endDateTime1;
	private Date endDateTime2;
	private Long seriNo;
	private Date currentDate;
	private Boolean hasMultipleParticipants;
	private boolean wasAtExam = false;
	private Byte bookletType;

	public ExamRequestOverallInfo() {
	}

	public ExamRequestOverallInfo(Long requestTypeID, Long subjectTypeID,
			long examRequestID, String examName, int questionCount1,
			int questionCount2, Date startDate, Date endDateTime1,
			Date endDateTime2, Boolean HasMultipleParticipants, Long seriNo) {
		this.examRequestTypeID = requestTypeID;
		this.examSubjectTypeID = subjectTypeID;
		this.examName = examName;
		this.questionCount1 = questionCount1;
		this.questionCount2 = questionCount2;
		this.startDateTime = startDate;
		this.endDateTime1 = endDateTime1;
		this.endDateTime2 = endDateTime2;
		this.hasMultipleParticipants = HasMultipleParticipants;
		this.examRequestID = examRequestID;
		this.seriNo = seriNo;

	}

	public boolean isWasAtExam() {
		return wasAtExam;
	}

	public void setWasAtExam(boolean wasAtExam) {
		this.wasAtExam = wasAtExam;
	}

	public Long getExamRequestTypeID() {
		return examRequestTypeID;
	}

	public void setExamRequestTypeID(Long examRequestTypeID) {
		this.examRequestTypeID = examRequestTypeID;
	}

	public Long getExamSubjectTypeID() {
		return examSubjectTypeID;
	}

	public void setExamSubjectTypeID(Long examSubjectTypeID) {
		this.examSubjectTypeID = examSubjectTypeID;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String name) {
		this.examName = name;
	}

	public int getQuestionCount() {
		return questionCount1 + questionCount2;
	}

	public int getQuestionCount1() {
		return questionCount1;
	}

	public void setQuestionCount1(int questionCount1) {
		this.questionCount1 = questionCount1;

	}

	public int getQuestionCount2() {
		return questionCount2;
	}

	public void setQuestionCount2(int questionCount2) {
		this.questionCount2 = questionCount2;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime1() {
		return endDateTime1;
	}

	public void setEndDateTime1(Date endDateTime1) {
		this.endDateTime1 = endDateTime1;
	}

	public Date getEndDateTime2() {
		return endDateTime2;
	}

	public void setEndDateTime2(Date endDateTime2) {
		this.endDateTime2 = endDateTime2;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Boolean getHasMultipleParticipants() {
		return hasMultipleParticipants;
	}

	public void setHasMultipleParticipants(Boolean hasMultipleParticipants) {
		this.hasMultipleParticipants = hasMultipleParticipants;
	}

	public long getExamRequestID() {
		return examRequestID;
	}

	public void setExamRequestID(long examRequestID) {
		this.examRequestID = examRequestID;
	}

	public Long getSeriNo() {
		return seriNo;
	}

	public void setSeriNo(Long seriNo) {
		this.seriNo = seriNo;
	}

	public byte getCurrentBookletNo() {
		if (this.getCurrentDate().getTime() < this.getEndDateTime1().getTime()) {
			return 1;
		} else {
			if (this.getCurrentDate().getTime() < this.getEndDateTime2()
					.getTime()) {
				if (this.getStartDateTime().getTime() == this.getEndDateTime1()
						.getTime())
					return 1;

				return 2;
			}
			return -1;
		}

	}

	public int getBookletQuestionCountIncludingReadingQuestionCount(
			int bookletNo) {
		if (bookletNo == 1) {
			if (questionCount1 == 0) {
				return questionCount2;
			} else
				return questionCount1;
		} else {
			return questionCount2;
		}

	}

	public Byte getBookletType() {
		return bookletType;
	}

	public void setBookletType(Byte bookletType) {
		this.bookletType = bookletType;
	}

}
