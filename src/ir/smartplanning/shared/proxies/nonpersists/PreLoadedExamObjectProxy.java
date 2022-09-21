package ir.smartplanning.shared.proxies.nonpersists;


import ir.smartplanning.server.domain.nonpersist.PreLoadedExamObject;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
 

@ProxyFor(value=PreLoadedExamObject.class)
public interface PreLoadedExamObjectProxy extends ValueProxy {
//	public List<QuestionProxy> getQuestions();
//	public void setQuestions(List<QuestionProxy> questions);
//	public ExamBookletTotalProxy getExamBookletTotals();
//	public void setExamBookletTotals(ExamBookletTotalProxy examBookletTotals) ;
}
