package ir.smartplanning.shared.proxies.nonpersists;

import ir.smartplanning.server.domain.nonpersist.QuestionAnswer;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(QuestionAnswer.class)
public interface QuestionAnswerProxy extends ValueProxy{

	public Long getQuestionID();
	public void setQuestionID(Long questionID);


	public Long getSelectedChoiceID();
	public void setSelectedChoiceID(Long selectedChoiceID);
	
	public Long getAnswerId();
	public void setAnswerId(Long answerId);
	
	public Integer getState() ;
	public void setState(Integer state) ;
}
