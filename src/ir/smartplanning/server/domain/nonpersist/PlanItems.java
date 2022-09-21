package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;

public class PlanItems implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -571048697986588213L;
	private Long defultPlanId;
	private byte dayOfWeek;
	private byte order;
	private Long studyFeedbackId;
	private Integer testNo;
	private Integer duration;
	private String studyFeedbackTopics;
	private String defultPlanTopics;//		mainDefultIds','#sud','#super',' ;
	private Byte incorrectNo;
	private Byte correctNo;
	private long moduleId;
	private String recommendation;
	private boolean general;

	public PlanItems() {
	}

	public Byte getCorrectNo() {
		return correctNo;
	}
	
	public void setCorrectNo(Byte correctNo) {
		this.correctNo = correctNo;
	}
	
	public boolean isGeneral() {
		return general;
	}

	public void setGeneral(boolean general) {
		this.general = general;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public Long getDefultPlanId() {
		return defultPlanId;
	}

	public void setDefultPlanId(Long defultPlanId) {
		this.defultPlanId = defultPlanId;
	}

	public byte getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(byte dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public byte getOrder() {
		return order;
	}

	public void setOrder(byte order) {
		this.order = order;
	}

	public Long getStudyFeedbackId() {
		return studyFeedbackId;
	}

	public void setStudyFeedbackId(Long studyFeedbackId) {
		this.studyFeedbackId = studyFeedbackId;
	}

	public Integer getTestNo() {
		return testNo;
	}

	public void setTestNo(Integer testNo) {
		this.testNo = testNo;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getStudyFeedbackTopics() {
		return studyFeedbackTopics;
	}

	public void setStudyFeedbackTopics(String studyFeedbackTopics) {
		this.studyFeedbackTopics = studyFeedbackTopics;
	}

	public String getDefultPlanTopics() {
		return defultPlanTopics;
	}

	public void setDefultPlanTopics(String defultPlanTopics) {
		this.defultPlanTopics = defultPlanTopics;
	}

	public Byte getIncorrectNo() {
		return incorrectNo;
	}

	public void setIncorrectNo(Byte incorrectNo) {
		this.incorrectNo = incorrectNo;
	}

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

}
