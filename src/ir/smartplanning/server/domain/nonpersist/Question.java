package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;

public class Question implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3924621730192899703L;
	private Long id;
	private String question;
	private String answer;
	private Long answerId;
	private Long questionTypeId;
	private String majorName;
	private String moduleName;
	private String topicName;
	private String parentTopicName;
	private Short publishYear;
	private Long origin;
	private Long difficultyLevelId;
	private Long readingQuestionId;
	private String createdByName;
	private Long choiceId1;
	private String choiceDescription1;
	private Long choiceId2;
	private String choiceDescription2;
	private Long choiceId3;
	private String choiceDescription3;
	private Long choiceId4;
	private String choiceDescription4;
	private Long cnt;
	private String practiceStat;
	private int rowNo;
	private Long countQuestionTypeId;
	private Long selectedChoiceId;

	public Question() {

	}

	public Question(Long id, String question, String answer, Long answerId, Long questionTypeId, String majorName, String moduleName, String topicName,
			String parentTopicName, Short publishYear, Long origin, Long difficultyLevelId, Long readingQuestionId, String createdByName, Long choice1,
			String choiceDescription1, Long choice2, String choiceDescription2, Long choice3, String choiceDescription3, Long choice4, String choiceDescription4,int rowNo) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.answerId = answerId;
		this.questionTypeId = questionTypeId;
		this.majorName = majorName;
		this.moduleName = moduleName;
		this.topicName = topicName;
		this.parentTopicName = parentTopicName;
		this.publishYear = publishYear;
		this.origin = origin;
		this.difficultyLevelId = difficultyLevelId;
		this.readingQuestionId = readingQuestionId;
		this.createdByName = createdByName;
		this.choiceId1 = choice1;
		this.choiceDescription1 = choiceDescription1;
		this.choiceId2 = choice2;
		this.choiceDescription2 = choiceDescription2;
		this.choiceId3 = choice3;
		this.choiceDescription3 = choiceDescription3;
		this.choiceId4 = choice4;
		this.choiceDescription4 = choiceDescription4;
		this.rowNo=rowNo;
	}
	
	public Long getCountQuestionTypeId() {
		return countQuestionTypeId;
	}
	
	public void setCountQuestionTypeId(Long countQuestionTypeId) {
		this.countQuestionTypeId = countQuestionTypeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Long getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getParentTopicName() {
		return parentTopicName;
	}

	public void setParentTopicName(String parentTopicName) {
		this.parentTopicName = parentTopicName;
	}

	public Short getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(Short publishYear) {
		this.publishYear = publishYear;
	}

	public Long getOrigin() {
		return origin;
	}

	public void setOrigin(Long origin) {
		this.origin = origin;
	}

	public Long getDifficultyLevelId() {
		return difficultyLevelId;
	}

	public void setDifficultyLevelId(Long difficultyLevelId) {
		this.difficultyLevelId = difficultyLevelId;
	}

	public Long getReadingQuestionId() {
		return readingQuestionId;
	}

	public void setReadingQuestionId(Long readingQuestionId) {
		this.readingQuestionId = readingQuestionId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Long getChoiceId1() {
		return choiceId1;
	}

	public void setChoiceId1(Long choice1) {
		this.choiceId1 = choice1;
	}

	public String getChoiceDescription1() {
		return choiceDescription1;
	}

	public void setChoiceDescription1(String choiceDescription1) {
		this.choiceDescription1 = choiceDescription1;
	}

	public Long getChoiceId2() {
		return choiceId2;
	}

	public void setChoiceId2(Long choice2) {
		this.choiceId2 = choice2;
	}

	public String getChoiceDescription2() {
		return choiceDescription2;
	}

	public void setChoiceDescription2(String choiceDescription2) {
		this.choiceDescription2 = choiceDescription2;
	}

	public Long getChoiceId3() {
		return choiceId3;
	}

	public void setChoiceId3(Long choice3) {
		this.choiceId3 = choice3;
	}

	public String getChoiceDescription3() {
		return choiceDescription3;
	}

	public void setChoiceDescription3(String choiceDescription3) {
		this.choiceDescription3 = choiceDescription3;
	}

	public Long getChoiceId4() {
		return choiceId4;
	}

	public void setChoiceId4(Long choice4) {
		this.choiceId4 = choice4;
	}

	public String getChoiceDescription4() {
		return choiceDescription4;
	}

	public void setChoiceDescription4(String choiceDescription4) {
		this.choiceDescription4 = choiceDescription4;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	public String getPracticeStat() {
		return practiceStat;
	}

	public void setPracticeStat(String practiceStat) {
		this.practiceStat = practiceStat;
	}
	
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	
	public Long getSelectedChoiceId() {
		return selectedChoiceId;
	}
	
	public void setSelectedChoiceId(Long selectedChoiceId) {
		this.selectedChoiceId = selectedChoiceId;
	}

}
