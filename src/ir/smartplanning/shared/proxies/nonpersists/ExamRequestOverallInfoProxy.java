package ir.smartplanning.shared.proxies.nonpersists;


import java.util.Date;

import ir.smartplanning.server.domain.nonpersist.ExamRequestOverallInfo;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value = ExamRequestOverallInfo.class)
public interface ExamRequestOverallInfoProxy extends ValueProxy {
	public Long getExamRequestTypeID();

	public Long getExamSubjectTypeID();

	public String getExamName();

	public int getQuestionCount();

	public Date getStartDateTime();

	public Date getEndDateTime1();

	public Date getEndDateTime2();

	public Date getCurrentDate();

	public byte getCurrentBookletNo();

	public boolean isWasAtExam();

	public Boolean getHasMultipleParticipants();

	public void setHasMultipleParticipants(Boolean hasMultipleParticipants);

	public int getQuestionCount1();

	public int getQuestionCount2();

}