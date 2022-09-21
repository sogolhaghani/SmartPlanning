package ir.smartplanning.shared.proxies.nonpersists;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

import ir.smartplanning.server.domain.nonpersist.Question;

@ProxyFor(value = Question.class)
public interface QuestionProxy extends ValueProxy {

	public Long getId();

	public String getQuestion();

	public String getAnswer();

	public Long getAnswerId();

	public Long getQuestionTypeId();

	public String getMajorName();

	public String getModuleName();

	public String getTopicName();

	public String getParentTopicName();

	public Short getPublishYear();

	public Long getOrigin();

	public Long getDifficultyLevelId();

	public Long getReadingQuestionId();

	public String getCreatedByName();

	public Long getChoiceId1();

	public String getChoiceDescription1();

	public Long getChoiceId2();

	public String getChoiceDescription2();

	public Long getChoiceId3();

	public String getChoiceDescription3();

	public Long getChoiceId4();

	public String getChoiceDescription4();

	public void setId(Long id);

	public void setQuestion(String question);

	public void setAnswer(String answer);

	public void setAnswerId(Long answerId);

	public void setQuestionTypeId(Long questionTypeId);

	public void setMajorName(String majorName);

	public void setModuleName(String moduleName);

	public void setTopicName(String topicName);

	public void setParentTopicName(String parentTopicName);

	public void setPublishYear(Short publishYear);

	public void setOrigin(Long origin);

	public void setDifficultyLevelId(Long difficultyLevelId);

	public void setReadingQuestionId(Long readingQuestionId);

	public void setCreatedByName(String createdByName);

	public void setChoiceId1(Long choice1);

	public void setChoiceDescription1(String choiceDescription1);

	public void setChoiceId2(Long choice2);

	public void setChoiceDescription2(String choiceDescription2);

	public void setChoiceId3(Long choice3);

	public void setChoiceDescription3(String choiceDescription3);

	public void setChoiceId4(Long choice4);

	public void setChoiceDescription4(String choiceDescription4);

	public Long getCnt();

	public void setCnt(Long cnt);

	public String getPracticeStat();

	public void setPracticeStat(String practiceStat);

	public int getRowNo();

	public void setRowNo(int rowNo);

	public Long getCountQuestionTypeId();

	public void setCountQuestionTypeId(Long countQuestionTypeId);
	
	public Long getSelectedChoiceId() ;
	
	public void setSelectedChoiceId(Long selectedChoiceId) ;
}